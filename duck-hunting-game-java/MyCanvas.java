

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;

import javafx.application.Application;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MyCanvas extends Application {

	public static int scale = 3;

	static int resolutionX = 270 * scale;
	static int resolutionY = 270 * scale;

	public BackgroundImage bimage = new BackgroundImage(new Image("assets\\background\\1.png"),
			BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			new BackgroundSize(resolutionX, resolutionX, false, false, false, false));
	MediaPlayer mediaPlayer;
	MediaPlayer mediaPlayer2;
	MediaPlayer mediaPlayer3;
	public ImageCursor cimage = new ImageCursor(new Image("assets\\crosshair\\1.png"));
	int imageCounter = 1;
	int cursorCounter = 1;
	int ammo = 0;
	int currentLevel = 1;
	Pane root2;
	Scene scene2;
	Pane root;
	Scene scene;
	public static Pane root3;
	Scene scene3;

	public static List<Bird> levelBirds = new ArrayList<>();
	public boolean levelFinished = false;
	ImageView foreground = new ImageView(new Image("assets\\foreground\\1.png"));
	boolean isContinue = false;
	boolean isLevelFinished = false;
	boolean isLevelTried = false;
	boolean isLevelFinished2 = false;

	boolean isGameFinished = false;

	Text text1;
	Text text2;
	Text text3;
	Text text4;

	String tryAgainLevel_;
	String nextLevel_;

	AnimationTimer gameLoop;
	AnimationTimer fallingAnim;
	AnimationTimer anim;
	AnimationTimer player;
	Rectangle rect=new Rectangle(10,10);
	

	/**
	 * The entry point for the JavaFX application.
	 *
	 * @param primaryStage the primary stage for this application, onto which the
	 *                     application scene can be set.
	 */
	@Override
	public void start(Stage primaryStage) {
		
		
		musicPlayer(615,false);
		root2 = new Pane();
		scene2 = new Scene(root2, resolutionX, resolutionX);
		root = new Pane();

		scene = new Scene(root, resolutionX, resolutionX);
		root3 = new Pane();
		scene3 = new Scene(root3, resolutionX, resolutionX);

		primaryStage.setScene(scene2);

		root2.setBackground(new Background(new BackgroundImage(new Image("assets\\welcome\\1.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(resolutionX, resolutionX, false, false, false, false))));
		Text text2_1 = new Text();
		text2_1.setTextAlignment(TextAlignment.CENTER);
		text2_1.setText("PRESS ENTER TO START\nPRESS ESC TO EXIT");
		text2_1.setFill(javafx.scene.paint.Color.AQUAMARINE);
		text2_1.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 20 * scale));

		text2_1.layoutXProperty().bind(scene.widthProperty().subtract(text2_1.prefWidth(-1)).divide(2));
		text2_1.layoutYProperty().bind(scene.heightProperty().subtract(text2_1.prefHeight(-1)).divide(1.2));

		root2.getChildren().add(text2_1);
		
		Text sceneText = new Text();
		sceneText.setTextAlignment(TextAlignment.CENTER);
		sceneText.setText("USE ARROW KEYS TO CHANGE\nBACKGROUND AND CROSSHAIR");
		sceneText.setFill(javafx.scene.paint.Color.DARKGOLDENROD);
		sceneText.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 13 * scale));

		sceneText.layoutXProperty().bind(scene.widthProperty().subtract(sceneText.prefWidth(-1)).divide(2));
		sceneText.layoutYProperty().bind(scene.heightProperty().subtract(sceneText.prefHeight(-1)).divide(5));

		root.getChildren().add(sceneText);

		AnimationTimer textAnimation = new AnimationTimer() {
			int counter=0;
			int mult=1;
			@Override
			public void handle(long now) {
				text2_1.setOpacity((float)counter/1000);
				counter+=4*mult;
				if(counter==1000) {
						
					mult=-1;
				}
				else if (counter==0) {
					mult=1;
				}
			}
			
		};
		textAnimation.start();
		scene2.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case ENTER:
				primaryStage.setScene(scene);
				mediaPlayer2=new MediaPlayer(new Media(new File("assets\\effects\\Intro.mp3").toURI().toString()));
				mediaPlayer2.play();
				
				player.stop();
				mediaPlayer.stop();
				
				textAnimation.stop();
				break;
			case ESCAPE:
				primaryStage.close();

				break;
			default:
				break;
			}
		});

		
		
		primaryStage.getIcons().add(new Image("assets/favicon/1.png"));
		primaryStage.show();

		root.setBackground(new Background(bimage));
		root.setCursor(cimage);

		scene.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case UP:
				changeCursor(root, true);
				break;
			case DOWN:
				changeCursor(root, false);

				break;
			case LEFT:
				changeBackGround(root, false);

				break;
			case RIGHT:
				changeBackGround(root, true);

				break;
			case ENTER:
				primaryStage.setScene(scene3);
				mediaPlayer2.stop();
				break;

			case ESCAPE:
				primaryStage.setScene(scene2);
				break;

			default:
				break;
			}
		});

		scene3.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case ESCAPE:
				primaryStage.setScene(scene2);
				break;
			case ENTER:
				if (isLevelFinished) {

					nextlevelOpener(nextLevel_);

					showText(text3, true);
					isLevelFinished = false;
					text1.setText(String.format("Level %d/6", currentLevel));

				} else if (isLevelTried) {
					tryAgainLevelOpener(tryAgainLevel_);
					showText(text4, true);

				}

				if (isGameFinished) {
					primaryStage.setScene(scene2);
					level1(false);
					Bird.directionXmultiplier*=1.4;
					isGameFinished = false;
				}
				break;

			default:
				break;
			}
		});
		level0(root3, primaryStage);
		level1(false);

	}

	/**
	 * Changes the background of the specified Pane based on the given flag. If the
	 * flag is true, the background is incremented to the next image in the
	 * sequence. If the flag is false, the background is decremented to the previous
	 * image in the sequence.
	 *
	 * @param pane           The Pane object whose background is to be changed.
	 * @param isItIncreasing A boolean flag indicating whether to increment or
	 *                       decrement the background.
	 */
	public void changeBackGround(Pane a, boolean isItIncreasing) {
		if (isItIncreasing) {
			imageCounter++;
			if (imageCounter == 7) {
				imageCounter = 1;
			}
		} else {
			imageCounter--;
			if (imageCounter == 0 || imageCounter == -1) {
				imageCounter = 6;
			}
		}
		bimage = new BackgroundImage(new Image(String.format("assets\\background\\%d.png", imageCounter)),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(resolutionX, resolutionX, false, false, false, false));

		try {
			root3.getChildren().remove(foreground);
		} catch (Exception e) {
		}
		foreground = new ImageView(new Image(String.format("assets\\foreground\\%d.png", imageCounter)));
		foreground.setFitWidth(resolutionX);
		foreground.setFitHeight(resolutionX);
		root3.getChildren().add(foreground);

		a.setBackground(new Background(bimage));
		root3.setBackground(new Background(bimage));

	}

	/**
	 * Changes the cursor of the specified Pane based on the given flag. If the flag
	 * is true, the cursor is incremented to the next image in the sequence. If the
	 * flag is false, the cursor is decremented to the previous image in the
	 * sequence.
	 *
	 * @param pane           The Pane object whose cursor is to be changed.
	 * @param isItIncreasing A boolean flag indicating whether to increment or
	 *                       decrement the cursor.
	 */

	public void changeCursor(Pane a, boolean isItIncreasing) {
		if (isItIncreasing) {
			cursorCounter++;
			if (cursorCounter == 8) {
				cursorCounter = 1;
			}
		} else {
			cursorCounter--;
			if (cursorCounter == 0 || cursorCounter == -1) {
				cursorCounter = 7;
			}
		}
		cimage = new ImageCursor(new Image(String.format("assets\\crosshair\\%d.png", cursorCounter)), 20, 20);

		a.setCursor(cimage);
		root3.setCursor(cimage);

	}

	/**
	 * Sets up the initial configuration for level 0 of the game.
	 *
	 * @param root  The root Pane object.
	 * @param stage The Stage object.
	 */
	public void level0(Pane root, Stage stage) {
		
		
		root3.setCursor(cimage);
		root3.setBackground(new Background(bimage));

		foreground.setFitWidth(resolutionX);
		foreground.setFitHeight(resolutionX);
		foreground.toFront();

		root3.getChildren().add(foreground);

		text1 = new Text();
		text1.setText(String.format("Level %d/6", currentLevel));
		text1.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 11 * scale));
		text1.setTextAlignment(TextAlignment.CENTER);
		text1.setFill(javafx.scene.paint.Color.AZURE);

		text1.layoutXProperty().bind(scene.widthProperty().subtract(text1.prefWidth(-1)).divide(2));
		text1.setY(resolutionX / 30);
		root.getChildren().add(text1);

		ammo = 10;

		text2 = new Text();
		text2.setText(String.format("Ammo Left: %d", ammo));
		text2.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 11 * scale));
		text2.setTextAlignment(TextAlignment.RIGHT);
		text2.setFill(javafx.scene.paint.Color.AZURE);
		// text1.layoutXProperty().bind(scene.widthProperty().subtract(text1.prefWidth(-1)).divide(2));
		text2.setX((resolutionX - 80 * scale));
		text2.setY(resolutionX / 30);
		root.getChildren().add(text2);

		text3 = new Text();
		text3.setText(String.format("LEVEL %s IS FINISHED\nPRESS ENTER TO CONTINUE", currentLevel));
		text3.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 20 * scale));
		text3.setTextAlignment(TextAlignment.RIGHT);
		text3.setFill(javafx.scene.paint.Color.BLACK);

		text3.setX(resolutionX / 5);
		text3.setY(resolutionY / 2);
		text3.setTranslateZ(100);
		text3.setOpacity(0);

		root.getChildren().add(text3);

		text4 = new Text();
		text4.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 20 * scale));
		text4.setTextAlignment(TextAlignment.RIGHT);
		text4.setFill(javafx.scene.paint.Color.BLACK);
		text4.setText("TRY AGAIN!!");

		text4.setX(resolutionX / 3);
		text4.setY(resolutionY / 2);
		text4.setTranslateZ(100);
		text4.setOpacity(0);

		root.getChildren().add(text4);

	}

	/**
	 * Sets up the configuration for level 1 of the game.
	 *
	 * @param isTryAgain A boolean flag indicating whether it's a retry of the
	 *                   level.
	 */
	public void level1(boolean isTryAgain) {

		ammo = 10;
		text2.setText(String.format("Ammo Left: %d", ammo));

		Bird bird1 = new Bird(150 * scale, 100 * scale / 2);
		bird1.spriteAnim.start();
		bird1.flyAnim.start();

		Bird bird2 = new Bird(150 * scale, 250 * scale / 2);
		bird2.spriteAnim.start();
		bird2.flyAnim.start();

		root3.getChildren().add(bird1.bird);
		root3.getChildren().add(bird1.hitbox);

		root3.getChildren().add(bird2.bird);
		root3.getChildren().add(bird2.hitbox);

		clickFunc("level1", "level2");

	}

	/**
	 * Sets up the configuration for level 2 of the game.
	 */
	public void level2(boolean isTryAgain) {

		ammo = 11;
		text2.setText(String.format("Ammo Left: %d", ammo));

		Bird bird1 = new Bird(300 * scale / 2, 300 * scale / 2);
		bird1.spriteAnim.start();
		bird1.flyAnim.start();

		Bird bird2 = new Bird(300 * scale / 2, 200 * scale / 2);
		bird2.spriteAnim.start();
		bird2.crossFlyAnim.start();

		Bird bird3 = new Bird(300 * scale / 2, 250 * scale / 2);
		bird3.spriteAnim.start();
		bird3.flyAnim.start();

		root3.getChildren().add(bird1.bird);
		root3.getChildren().add(bird1.hitbox);

		root3.getChildren().add(bird2.bird);
		root3.getChildren().add(bird2.hitbox);
		root3.getChildren().add(bird3.bird);
		root3.getChildren().add(bird3.hitbox);

		clickFunc("level2", "level3");

	}

	/**
	 * Sets up the configuration for level 3 of the game.
	 */
	public void level3() {

		ammo = 11;
		text2.setText(String.format("Ammo Left: %d", ammo));

		Bird bird1 = new Bird(300 * scale / 2, 50 * scale / 2);
		bird1.spriteAnim.start();
		bird1.flyAnim.start();

		Bird bird2 = new Bird(300 * scale / 2, 200 * scale / 2);
		bird2.spriteAnim.start();
		bird2.crossFlyAnim.start();

		Bird bird3 = new Bird(300 * scale / 2, 150 * scale / 2);
		bird3.spriteAnim.start();
		bird3.flyAnim.start();

		Bird bird4 = new Bird(300 * scale / 2, 250 * scale / 2);
		bird4.spriteAnim.start();
		bird4.crossFlyAnim.start();

		root3.getChildren().add(bird1.bird);
		root3.getChildren().add(bird1.hitbox);

		root3.getChildren().add(bird2.bird);
		root3.getChildren().add(bird2.hitbox);
		root3.getChildren().add(bird3.bird);
		root3.getChildren().add(bird3.hitbox);
		root3.getChildren().add(bird4.bird);
		root3.getChildren().add(bird4.hitbox);

		clickFunc("level3", "level4");
	}

	/**
	 * Sets up the configuration for level 4 of the game.
	 */
	public void level4() {
		ammo = 11;
		text2.setText(String.format("Ammo Left: %d", ammo));

		Bird bird1 = new Bird(300 * scale / 2, 300 * scale / 2);
		bird1.spriteAnim.start();
		bird1.flyAnim.start();

		Bird bird2 = new Bird(50 * scale / 2, 200 * scale / 2);
		bird2.spriteAnim.start();
		bird2.crossFlyAnim.start();

		Bird bird3 = new Bird(300 * scale / 2, 250 * scale / 2);
		bird3.spriteAnim.start();
		bird3.crossFlyAnim.start();

		Bird bird4 = new Bird(100 * scale / 2, 150 * scale / 2);
		bird4.spriteAnim.start();
		bird4.crossFlyAnim.start();

		root3.getChildren().add(bird1.bird);
		root3.getChildren().add(bird1.hitbox);

		root3.getChildren().add(bird2.bird);
		root3.getChildren().add(bird2.hitbox);
		root3.getChildren().add(bird3.bird);
		root3.getChildren().add(bird3.hitbox);
		root3.getChildren().add(bird4.bird);
		root3.getChildren().add(bird4.hitbox);

		clickFunc("level4", "level5");

	}

	/**
	 * Sets up the configuration for level 5 of the game.
	 */
	public void level5() {
		ammo = 8;
		text2.setText(String.format("Ammo Left: %d", ammo));

		Bird bird1 = new Bird(300 * scale / 2, 300 * scale / 2);
		bird1.spriteAnim.start();
		bird1.crossFlyAnim.start();

		Bird bird2 = new Bird(50 * scale / 2, 200 * scale / 2);
		bird2.spriteAnim.start();
		bird2.crossFlyAnim.start();

		Bird bird3 = new Bird(300 * scale / 2, 250 * scale / 2);
		bird3.spriteAnim.start();
		bird3.crossFlyAnim.start();

		Bird bird4 = new Bird(100 * scale / 2, 150 * scale / 2);
		bird4.spriteAnim.start();
		bird4.crossFlyAnim.start();

		root3.getChildren().add(bird1.bird);
		root3.getChildren().add(bird1.hitbox);

		root3.getChildren().add(bird2.bird);
		root3.getChildren().add(bird2.hitbox);
		root3.getChildren().add(bird3.bird);
		root3.getChildren().add(bird3.hitbox);
		root3.getChildren().add(bird4.bird);
		root3.getChildren().add(bird4.hitbox);

		clickFunc("level5", "level6");
	}

	/**
	 * Sets up the configuration for level 6 of the game.
	 */
	public void level6() {
		ammo = 11;
		text2.setText(String.format("Ammo Left: %d", ammo));

		Bird bird1 = new Bird(300 * scale / 2, 300 * scale / 2);
		bird1.spriteAnim.start();
		bird1.flyAnim.start();

		Bird bird2 = new Bird(300 * scale / 2, 200 * scale / 2);
		bird2.spriteAnim.start();
		bird2.crossFlyAnim.start();

		Bird bird3 = new Bird(300 * scale / 2, 100 * scale / 2);
		bird3.spriteAnim.start();
		bird3.crossFlyAnim.start();

		Bird bird4 = new Bird(300 * scale / 2, 250 * scale / 2);
		bird4.spriteAnim.start();
		bird4.crossFlyAnim.start();

		Bird bird5 = new Bird(300 * scale / 2, 170 * scale / 2);
		bird5.spriteAnim.start();
		bird5.flyAnim.start();
		Bird bird6 = new Bird(300 * scale / 2, 210 * scale / 2);
		bird6.spriteAnim.start();
		bird6.flyAnim.start();
		Bird bird7 = new Bird(300 * scale / 2, 220 * scale / 2);
		bird7.spriteAnim.start();
		bird7.flyAnim.start();

		root3.getChildren().add(bird1.bird);
		root3.getChildren().add(bird1.hitbox);

		root3.getChildren().add(bird2.bird);
		root3.getChildren().add(bird2.hitbox);
		root3.getChildren().add(bird3.bird);
		root3.getChildren().add(bird3.hitbox);
		root3.getChildren().add(bird4.bird);
		root3.getChildren().add(bird4.hitbox);
		root3.getChildren().add(bird5.bird);
		root3.getChildren().add(bird5.hitbox);

		root3.getChildren().add(bird6.bird);
		root3.getChildren().add(bird6.hitbox);

		root3.getChildren().add(bird7.bird);
		root3.getChildren().add(bird7.hitbox);

		clickFunc("level6", "level1");

	}

	/**
	 * Removes all the birds from the level by removing their hitboxes and bird
	 * objects from the root pane.
	 */
	public void removeBirds() {
		for (Bird bird : levelBirds) {
			root3.getChildren().remove(bird.hitbox);
			root3.getChildren().remove(bird.bird);
		}
		levelBirds.removeAll(levelBirds);
	}

	/**
	 * Sets up the functionality for mouse click events in the game. It updates the
	 * current level's "try again" level and "next level" variables. It handles the
	 * logic for shooting birds and updating the ammo count. It checks if the level
	 * is finished or if the player ran out of ammo.
	 *
	 * @param tryAgainLevel The level to retry if the player runs out of ammo.
	 * @param nextLevel     The level to progress to if all birds are eliminated.
	 */
	MediaPlayer player2 = null;
	public void clickFunc(String tryAgainLevel, String nextLevel) {
		tryAgainLevel_ = tryAgainLevel;
		nextLevel_ = nextLevel;
		
		root3.setOnMouseClicked(event -> {
			
			try {
				 player2=new MediaPlayer(new Media(new File("assets\\effects\\Gunshot.mp3").toURI().toString()));
				player2.play();
			} catch (Exception e) {
			}
			
			
			if (ammo == 0) {
				
			} else if (ammo > 0) {
				
				for (int i = 0; i < levelBirds.size(); i++) {
					
					
					if (event.getX() +15> levelBirds.get(i).charPosX 
							&& levelBirds.get(i).charPosX +  levelBirds.get(i).hitbox.getWidth() > event.getX()+15 && levelBirds.get(i).charPosY
									 < event.getY() +15
							&& levelBirds.get(i).charPosY +  levelBirds.get(i).hitbox.getHeight() > event.getY()+15) {
						levelBirds.get(i).direction = 0;
						levelBirds.get(i).isDead = true;
						levelBirds.get(i).bird.setImage(levelBirds.get(i).whichColor(levelBirds.get(i).color).get(6));

						levelBirds.get(i).spriteAnim.stop();
						levelBirds.get(i).spriteAnim2.stop();
						root3.getChildren().remove(levelBirds.get(i).hitbox);
						levelBirds.remove(i);

					}
				}
				text2.setText(String.format("Ammo Left: %d", ammo - 1));

			}

			if (levelBirds.size() == 0 && !isLevelTried) {
				
				
				text3.setText(String.format("LEVEL %s IS FINISHED", currentLevel));
				showText(text3, false);

				text1.setText(String.format("Level %d/6", currentLevel));
				isLevelFinished = true;
				if (currentLevel == 6) {
					isGameFinished = true;
					currentLevel = 0;
				}
				if (isGameFinished) {
					mediaPlayer2=new MediaPlayer(new Media(new File("assets\\effects\\GameCompleted.mp3").toURI().toString()));
					mediaPlayer2.play();
				}
				else {
					mediaPlayer2=new MediaPlayer(new Media(new File("assets\\effects\\LevelCompleted.mp3").toURI().toString()));
					mediaPlayer2.play();
				}

			}

			ammo -= 1;

			if (ammo == 0) {
				if (levelBirds.size() > 0 && !isLevelFinished) {
					
					mediaPlayer2=new MediaPlayer(new Media(new File("assets\\effects\\GameOver.mp3").toURI().toString()));
					mediaPlayer2.play();
					text2.setText(String.format("Ammo Left: %d", ammo));
					removeBirds();
					isLevelTried = true;
					isLevelFinished = false;
					showText(text4, false);

				}
			}
			
			
		});
	}

	/**
	 * Shows or hides a text element with animation.
	 *
	 * @param text       The Text object to show or hide.
	 * @param isStarting Determines if the text is being hidden (true) or shown
	 *                   (false).
	 */
	public void showText(Text text, boolean isStarting) {
		anim = new AnimationTimer() {

			@Override
			public void handle(long now) {

				if (isLevelFinished) {
					text3.setOpacity(1);

				} else if (isLevelTried) {

					text4.setOpacity(1);

				}
			}
		};
		if (!isStarting) {
			anim.start();

		} else {
			anim.stop();
			text.setOpacity(0);
			isLevelFinished = false;
			isLevelTried = false;

		}

	}

	/**
	 * Opens the next level based on the given level name.
	 *
	 * @param nextLevel The name of the next level to open.
	 */
	public void nextlevelOpener(String nextLevel) {
		currentLevel++;
		switch (nextLevel) {
		case "level2":
			level2(false);

			break;
		case "level3":
			level3();
			break;
		case "level4":
			level4();
			break;
		case "level5":
			level5();
			break;
		case "level6":
			level6();
			break;
		default:
			break;
		}
	}

	/**
	 * Opens the specified level as a try again attempt.
	 *
	 * @param tryAgainLevel The level to open as a try again attempt.
	 */
	public void tryAgainLevelOpener(String tryAgainLevel) {
		switch (tryAgainLevel) {
		case "level1":
			level1(true);
			break;
		case "level2":
			level2(true);
			break;
		case "level3":
			level3();
			break;
		case "level4":
			level4();
			break;
		case "level5":
			level5();
			break;
		case "level6":
			level6();
			break;

		default:
			break;
		}
	}
	void musicPlayer(int time,boolean exit){
		String musicFile = "assets\\effects\\Title.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
		
		 player=new AnimationTimer() {
			int counter=0;
			@Override
			public void handle(long now) {
				mediaPlayer.play();
				counter++;
				
				if(counter==time) {
					counter=0;
					mediaPlayer.stop();
					mediaPlayer.play();
					
				}
			}
		};
		if (exit) {
			
			player.stop();
			mediaPlayer.stop();
		}
		else {
			
			player.start();
		}
		
	}
}
