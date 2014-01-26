package com.funandplausible.games.ggj2014;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.funandplausible.games.ggj2014.drawables.Drawable;
import com.funandplausible.games.ggj2014.drawables.Hat;
import com.funandplausible.games.ggj2014.drawables.SpriteDrawable;

public class GameRoot implements ApplicationListener {

    private static GameServices sServices;
    private static final String[] ENEMY_TYPES = new String[] { "low_hat",
            "med_hat", "high_hat" };

    private final String mConstantsText;

    public GameRoot(String constants) {
        mConstantsText = constants;
    }

    public static GameServices services() {
        return sServices;
    }

    private List<Drawable> mDrawables;
    private Set<Updateable> mUpdateables;
    private GameState mState;
    private Box2DDebugRenderer mDebugRenderer;
    private PlayerEntity mPlayer;
    private List<EnemyEntity> mEnemyEntities;
    private int mNEnemies;
    private ComboHandler mComboHandler;
    private Sprite mGameOverSprite;
    private BitmapFont mFont;
    private Sprite mMainMenuSprite;
    private Sprite mHatSprite;

    @Override
    public void create() {
        GameRoot.sServices = new GameServices(mConstantsText);
        mDrawables = new ArrayList<Drawable>();
        mUpdateables = new HashSet<Updateable>();
        mEnemyEntities = new ArrayList<EnemyEntity>();
        mNEnemies = constants().getInt("n_enemies");
        mGameOverSprite = services().contentManager().loadPackedSprite("lose");
        mMainMenuSprite = services().contentManager().loadPackedSprite(
                "mainmenu");
        mHatSprite = services().contentManager().loadPackedSprite("hat1");
        mHatSprite.setColor(Color.BLACK);
        mHatSprite.setBounds(0, 0, 50, 50);
        mFont = new BitmapFont(Content.file("calibri.fnt"));

        if (constants().getBoolean("no_start_screen")) {
            mState = GameState.RUN;
        } else {
            mState = GameState.MAIN_MENU;
        }

        createPlayer();
        createBackground();

        if (constants().getBoolean("spawn_hats_on_floor")) {
            createHats();
        }

        HatGenerator hg = services().hatGenerator();
        List<Hat> hats = hg.generateHats(2);

        createEnemies();
        createWorldBounds();
        createListener();

        mComboHandler = new ComboHandler(mPlayer);
        mDebugRenderer = new Box2DDebugRenderer();
        mPlayer.getHats().addAll(hats);
        for (Hat h : hats) {
            mDrawables.add(h);
            mUpdateables.add(h);
        }
    }

    private void createWorldBounds() {
        new BoundaryCreator();
    }

    private void createEnemies() {
        for (int i = 0; i < mNEnemies; i++) {
            generateEnemy();
        }
    }

    private void generateEnemy() {
        float bounds = constants().getFloat("enemy_bounds");
        float initialType = random().nextFloat();
        float initialX;
        float initialY;
        if (initialType < 1.0 / 3.0) {
            initialX = bounds + random().nextFloat() * 200;
            initialY = random().nextFloat() * bounds * 2 - bounds;
        } else if (initialType < 2.0 / 3.0) {
            initialX = -(bounds + random().nextFloat() * 200);
            initialY = random().nextFloat() * bounds * 2 - bounds;
        } else {
            initialX = 0;
            initialY = -bounds + random().nextFloat() * 200;
        }

        List<Hat> hats = generateEnemyHats();
        EnemyEntity ee = new EnemyEntity(initialX, initialY, bounds, hats,
                mPlayer);
        mUpdateables.addAll(hats);
        mDrawables.addAll(hats);
        mEnemyEntities.add(ee);
        mUpdateables.add(ee);
        mDrawables.add(ee);
    }

    private List<Hat> generateEnemyHats() {
        float totalProbability = 0;

        for (String et : ENEMY_TYPES) {
            totalProbability += constants().getFloat(et + "_npc_probability");
        }

        float actualProbability = services().random().nextFloat()
                * totalProbability;
        int i = -1;
        while (actualProbability > 0) {
            actualProbability -= constants().getFloat(
                    ENEMY_TYPES[i + 1] + "_npc_probability");
            i++;
        }

        String enemyType = ENEMY_TYPES[i];
        int minHatCount = constants().getInt(enemyType + "_npc_hat_min");
        int maxHatCount = constants().getInt(enemyType + "_npc_hat_max");
        int nHats = services().random().nextInt(maxHatCount - minHatCount)
                + minHatCount;
        HatGenerator hg = services().hatGenerator();
        List<Hat> hats = hg.generateHats(nHats);
        return hats;
    }

    private Random random() {
        return services().random();
    }

    private void createPlayer() {
        mPlayer = new PlayerEntity();
        mDrawables.add(mPlayer);
        mUpdateables.add(mPlayer);
    }

    @Override
    public void dispose() {
        mainSpriteBatch().dispose();
    }

    @Override
    public void render() {
        handlePauseInput();
        switch (mState) {
        case MAIN_MENU:
            if (Gdx.input.isKeyPressed(Keys.SPACE)) {
                mState = GameState.RUN;
            } else {
                clear();
                drawMainMenu();
                break;
            }
        case RUN:
            updateMain();
        case PAUSED:
            clear();
            drawMain();
            break;
        case GAME_OVER:
            if (Gdx.input.isKeyPressed(Keys.SPACE)) {
                create();
            }
            clear();
            drawGameOver();
            break;
        }
    }

    private void handlePauseInput() {
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            switch (mState) {
            case GAME_OVER:
                Gdx.app.exit();
                break;
            case MAIN_MENU:
                break;
            case PAUSED:
                mState = GameState.RUN;
                break;
            case RUN:
                mState = GameState.PAUSED;
                break;
            }
        }
    }

    private void drawMainMenu() {
        uiSpriteBatch().begin();
        mMainMenuSprite.draw(uiSpriteBatch());
        uiSpriteBatch().end();
    }

    private void drawGameOver() {
        uiSpriteBatch().begin();
        mGameOverSprite.draw(uiSpriteBatch());
        uiSpriteBatch().end();
    }

    private void updateMain() {
        stepPhysics();
        dropAllHats();

        for (Updateable u : mUpdateables) {
            u.update();
        }

        removeDeadEnemies();
        boolean comboResult = mComboHandler.tick();
        if (comboResult == false) {
            mState = GameState.GAME_OVER;
        }

        Vector2 playerPosition = mPlayer.position();
        float cameraX = playerPosition.x, cameraY = playerPosition.y;
        float cameraBoundX = sServices.constantManager().getFloat(
                "camera_bound_x");
        float cameraBoundY = sServices.constantManager().getFloat(
                "camera_bound_y");
        if (cameraX < -cameraBoundX) {
            cameraX = -cameraBoundX;
        }
        if (cameraY < -cameraBoundY) {
            cameraY = -cameraBoundY;
        }
        if (cameraX > cameraBoundX) {
            cameraX = cameraBoundX;
        }
        if (cameraY > cameraBoundY) {
            cameraY = cameraBoundY;
        }
        camera().position.x = cameraX;
        camera().position.y = cameraY;
        camera().update();

    }

    private void removeDeadEnemies() {
        List<EnemyEntity> deadEntities = new ArrayList<EnemyEntity>();

        for (EnemyEntity ee : mEnemyEntities) {
            if (ee.dead()) {
                deadEntities.add(ee);
            }
        }

        mEnemyEntities.removeAll(deadEntities);

        for (EnemyEntity ee : deadEntities) {
            mDrawables.remove(ee);
            mUpdateables.remove(ee);
            services().world().destroyBody(ee.body());
            generateEnemy();
        }
    }

    private void stepPhysics() {
        services().world().step((float) (60 / 1000.0), 3, 3);
    }

    private boolean mAllowSinglePop = true;
    private float mTimeSinceSpace = 0;

    private void dropAllHats() {
        mTimeSinceSpace++;
        if (mTimeSinceSpace > 30) {
            mAllowSinglePop = true;
        }
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            List<Hat> playersHats;

            if (services().constantManager().getBoolean("drop_single_hat")) {
                playersHats = new ArrayList<Hat>();
                if (mPlayer.hatCount() > 0 && mAllowSinglePop) {
                    services().soundManager().play("hats1");
                    mAllowSinglePop = false;
                    playersHats.add(mPlayer.getHats().pop());
                    mTimeSinceSpace = 0;
                }
            } else {
                playersHats = mPlayer.popAllHats();
            }

            for (Hat h : playersHats) {
                h.setLoose();
            }

            services().hatDistributor().distributeHats(playersHats,
                    mPlayer.position().x, mPlayer.position().y);
        }
    }

    private void clear() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }

    private void drawMain() {
        mainSpriteBatch().setProjectionMatrix(
                cameraMatrix().scale(1.5f, 1.5f, 1));
        mainSpriteBatch().begin();
        Collections.sort(mDrawables);
        for (Drawable d : mDrawables) {
            d.draw(mainSpriteBatch());
        }
        mainSpriteBatch().end();
        drawDebugPhysics();

        uiSpriteBatch().begin();
        mComboHandler.draw(uiSpriteBatch());
        services().scoreBoard().draw(uiSpriteBatch());
        int maxHatCount = services().constantManager().getInt("max_hats");
        String s = mPlayer.getHats().size() + "/" + maxHatCount;
        mFont.draw(uiSpriteBatch(), s, 690, 570);
        mHatSprite.setPosition(620, 570 - mHatSprite.getHeight() / 2);
        mHatSprite.draw(uiSpriteBatch());
        uiSpriteBatch().end();
    }

    private void drawDebugPhysics() {
        if (services().constantManager().getBoolean("debug_physics")) {
            mDebugRenderer.render(
                    services().world(),
                    services().camera().combined.cpy().scale(
                            GameServices.PIXELS_PER_METER,
                            GameServices.PIXELS_PER_METER, 1));
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    private SpriteBatch mainSpriteBatch() {
        return services().mainSpriteBatch();
    }

    private SpriteBatch uiSpriteBatch() {
        return services().uiSpriteBatch();
    }

    private Camera camera() {
        return services().camera();
    }

    private Matrix4 cameraMatrix() {
        return services().camera().combined;
    }

    private void createBackground() {
        SpriteDrawable sd = new SpriteDrawable(services().contentManager()
                .loadPlainSprite("marble.png"), -1000);
        float bound = services().constantManager().getFloat("enemy_bounds");
        sd.setPosition(-bound, -bound);
        mDrawables.add(sd);
    }

    private void createHats() {
        HatDistributor hd = services().hatDistributor();
        HatGenerator hg = services().hatGenerator();

        for (Hat h : hd.distributeHats(
                hg.generateHats(constants().getInt("initial_hats")), 0, 0)) {
            mDrawables.add(h);
            mUpdateables.add(h);
        }
    }

    private ConstantManager constants() {
        return GameRoot.services().constantManager();
    }

    private void createListener() {
        new CollisionHandler();
    }

}
