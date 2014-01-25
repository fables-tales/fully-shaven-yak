<!doctype html>
<html>
	<head>
		<title>ggj2014</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<style>
			canvas {
				cursor: default;
				outline: none;
			}
		</style>
	</head>
	
	<body>
		<script type="text/javascript">
			<?php
				echo "constants = " . json_encode(file_get_contents('https://raw2.github.com/samphippen/fully-shaven-yak/master/ggj2014-desktop/assets/constants.txt')) . ';';
			?>
		</script>
		<div align="center" id="embed-com.funandplausible.games.ggj2014.GwtDefinition"></div>
		<script type="text/javascript" src="com.funandplausible.games.ggj2014.GwtDefinition/com.funandplausible.games.ggj2014.GwtDefinition.nocache.js"></script>
	</body>
	
	<script>
		function handleMouseDown(evt) {
		  evt.preventDefault();
		  evt.stopPropagation();
		  evt.target.style.cursor = 'default';
		}
		
		function handleMouseUp(evt) {
		  evt.preventDefault();
		  evt.stopPropagation();
		  evt.target.style.cursor = '';
		}
		
		document.getElementById('embed-com.funandplausible.games.ggj2014.GwtDefinition').addEventListener('mousedown', handleMouseDown, false);
		document.getElementById('embed-com.funandplausible.games.ggj2014.GwtDefinition').addEventListener('mouseup', handleMouseUp, false);
	</script>
</html>
