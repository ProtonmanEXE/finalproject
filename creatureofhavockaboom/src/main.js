import kaboom from "kaboom";

// initialize context (kaboom)
kaboom({
    global: true,
    fullscreen: true,
    scale: 1,
    debug: true,
    background: [0, 0, 0, 1], 
    // background: [0, 0, 0, 0], // gives white background
    // background: [0, 0, 0, 1], // gives black background

})

// Speed identifiers
const SPEED = 480

loadSprite("bean", "assets/creatureofhavoc/bean.png")
loadSprite("caveman", "assets/creatureofhavoc/bluecaveman.png")
loadSprite("torch", "assets/creatureofhavoc/torch.png")
loadSprite("scorpion", "assets/creatureofhavoc/scorpion.png")
loadSprite("woodblock", "assets/creatureofhavoc/woodblock.png")
loadSprite("meat", "assets/creatureofhavoc/meat.png")

// Design 2 levels
const LEVELS = [
	[
	    "=                    =",
		"=                    =",
		"=   ^  ^  ^  ^  ^    =",
		"=                 >  =",
		"=                    =",
		"=            W       =",
		"=@                   =",
	],
	[
		"@   M  M  M  M              >",
		"                             ",
	],
]

// Define a scene called "game". The callback will be run when we go() to the scene
// Scenes can accept argument from go()
scene("game", ({ levelIdx, score }) => {

	gravity(2400)

    add([
		rect(1700, 28),
		outline(4),
		pos(0, height()),
		origin("botleft"),
		area(),
		solid(),
		color(127, 200, 255),
	])

    add([
		rect(1700, 145),
		outline(4),
		pos(0, 0),
		origin("topleft"),
		area(),
		solid(),
		color(127, 200, 255),
	])

	// Use the level passed, or first level
	const level = addLevel(LEVELS[levelIdx || 0], {
		width: 64,
		height: 64,
		pos: vec2(100, 200),
		"@": () => [
			sprite("caveman"),
			area(),
			body(),
			origin("bot"),
            scale(2.5),
			"player",
		],
		"=": () => [
			sprite("woodblock"),
			area(),
			solid(),
            scale(3),
			origin("bot"),
		],
		"W": () => [
			sprite("scorpion"),
			area(),
			origin("bot"),
			scale(0.1),
			"danger",
		],
		"^": () => [
			sprite("torch"),
			area(),
			origin("bot"),
			scale(0.3),
		],
		">": () => [
			rect(60, 135),
            color(165,42,42),
            area(),
            solid(),
			"portal",
		],
		"M": () => [
			sprite("meat"),
			area(),
			origin("bot"),
			scale(0.1),
			"meat",
		],
	})

	// Get the player object from tag
	const player = get("player")[0]

    player.onUpdate(() => {
        // center camera to player
        var currCam = camPos();
        if (currCam.x < player.pos.x) {
          camPos(player.pos.x, currCam.y);
        }
      });

	// Movements
	onKeyPress("space", () => {
		if (player.isGrounded()) {
			player.jump(1000, 0)
		}
	})

	onKeyDown("left", () => {
		player.move(-SPEED, 0)
	})

	onKeyDown("right", () => {
		player.move(SPEED, 0)
	}) 

	// Monster death
	player.onCollide("danger", () => {
		player.pos = level.getPos(0, 0)
		// Go to "lose" scene when we hit a "danger"
		go("lose")
	})

	// Fall death
	player.onUpdate(() => {
		if (player.pos.y >= 1000) {
			go("lose")
		}
	})

	// Enter the next level on portal
	player.onCollide("portal", () => {
		if (levelIdx < LEVELS.length - 1) {
			// If there's a next level, go() to the same scene but load the next level
			go("game", {
				levelIdx: levelIdx + 1,
				score: score
			})
		} else {
			// Otherwise we have reached the end of game, go to "win" scene!
			go("win", { score: score, })
		}
	})

	// meat collection score
	player.onCollide("meat", (c) => {
		destroy(c),
		score += 1,
		scoreLabel.text = score
	})

	// Score counter text
	const scoreLabel = add([
		text(score),
		pos(12)
	])

})

scene("lose", () => {

	add([
		text("You Lose"),
		pos(12)
	])

	// Press any key to go back
	onKeyPress(start)

})

scene("win", ({ score }) => {

	add([
		text(`You grabbed ${score} coins!!!`, {
			width: width(),
		}),
		pos(12)
	])

	onKeyPress(start)

})

function start() {
	// Start with the "game" scene, with initial parameters
	go("game", {
		levelIdx: 0,
		score: 0,
	})
}

start()
