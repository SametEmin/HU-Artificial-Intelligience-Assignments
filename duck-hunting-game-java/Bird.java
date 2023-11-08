

import java.awt.font.NumericShaper.Range;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.omg.CORBA.PUBLIC_MEMBER;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;
/**
 * The Bird class represents a bird character in the game.
 * It contains properties and behaviors related to the bird, such as position, movement, animation, and color.
 */
public class Bird {

	ImageView bird = new ImageView();
	public float charPosX = 0;
	public float charPosY = 100;
	public static float directionXmultiplier =1;
	public float direction = 1*MyCanvas.scale/2*directionXmultiplier;
	public float directionY = 1*directionXmultiplier;
	public Rectangle hitbox = new Rectangle(32*MyCanvas.scale, 20*MyCanvas.scale, Color.ALICEBLUE);
	Images images = new Images();
	public boolean isDead = false;
	String color;
	List<String> colors=new ArrayList<>();
	
	/**
     * Constructs a Bird object with the specified initial position.
     *
     * @param charPosX the initial X position of the bird
     * @param charPosY the initial Y position of the bird
     */
	public Bird(float charPosX, float charPosY) {
		this.charPosX = charPosX;
		this.charPosY = charPosY;
		
		Random rand=new Random();
		colors.add("red");
		colors.add("blue");
		colors.add("black");
		
		color=colors.get(rand.nextInt(colors.size()));
		
		bird.setLayoutX(charPosX - 10);
		bird.setLayoutY(charPosY+ 10);

		MyCanvas.levelBirds.add(this);

		hitbox.setY(charPosY );
		hitbox.setOpacity(0);

		if (Math.random() < 0.50) {
			direction = -1*MyCanvas.scale/2;
		} else {
			direction = 1*MyCanvas.scale/2;
		}

	}
	 
	AnimationTimer flyAnim = new AnimationTimer() {
		
		MediaPlayer player3;
		int brake_y = 1;
		int counter = 0;
		
		 
		@Override
		
		public void handle(long arg0) {
			if (direction != 0) {
				bird.setLayoutX(charPosX);
				hitbox.setX(charPosX - 15);

				if (charPosX == MyCanvas.resolutionX - hitbox.getWidth()) {
					direction = -1*MyCanvas.scale/2;

				} else if (charPosX == 0) {
					direction = 1*MyCanvas.scale/2;

				}
				charPosX += 1 * direction;

			} else {
				counter++;
				bird.setLayoutY(charPosY);
				 if (counter==1) {
					 player3=new MediaPlayer(new Media(new File("assets\\effects\\DuckFalls.mp3").toURI().toString()));
					 player3.play();
				}
				
				if (counter > 30) {
					
					charPosY += 1 * brake_y;
					bird.setImage(whichColor(color).get(7));

				}
				if (charPosY == MyCanvas.resolutionY - 50) {
					brake_y = 0;
					player3.stop();
					MyCanvas.root3.getChildren().remove(this);
				}

			}

		}

	};
	AnimationTimer spriteAnim = new AnimationTimer() {
		int counter = 160;
		
		@Override
		public void handle(long now) {
			bird.setImage(whichColor(color).get((int) counter / 40));
			bird.setScaleX(1.2*MyCanvas.scale * direction);
			bird.setScaleY(1.2*MyCanvas.scale );
			bird.toBack();
			counter++;
			if (counter == 239) {
				counter = 120;
			}

		}
	};
	
	AnimationTimer spriteAnim2 = new AnimationTimer() {
		int counter = 0;

		@Override
		public void handle(long now) {
			bird.setImage(whichColor(color).get((int) counter / 40));
			bird.setScaleX(1.2*MyCanvas.scale* direction);
			bird.setScaleY(1.2*MyCanvas.scale* -directionY);
			
			bird.toBack();
			counter++;
			if (counter == 119) {
				counter = 0;
			}

		}
	};

	AnimationTimer crossFlyAnim = new AnimationTimer() {
		int brake_y = 1;
		int counter = 0;

		@Override
		public void handle(long now) {
			if (direction != 0) {
				bird.setLayoutX(charPosX);
				hitbox.setX(charPosX - 15);
				bird.setLayoutY(charPosY);
				hitbox.setY(charPosY );
				

				if (charPosX == MyCanvas.resolutionX - hitbox.getWidth()) {
					direction = -1;

				} else if (charPosX == 0) {
					direction = 1;

				}
				if (charPosY == 0) {
					directionY = 1;
				} else if (charPosY ==MyCanvas.resolutionY - hitbox.getHeight() ) {
					directionY= -1;

				}
				charPosY+=1*directionY;
				charPosX += 1 * direction;
				

			} else {
				counter++;
				bird.setLayoutY(charPosY);

				if (counter > 30) {
					charPosY += 1 * brake_y;
					bird.setImage(whichColor(color).get(7));

				}
				if (charPosY == MyCanvas.resolutionY - 50) {
					brake_y = 0;
				}

			}
		}
	};
	  /**
     * Returns the list of images based on the specified color.
     *
     * @param color the color of the bird
     * @return the list of images corresponding to the color
     */
	public List<Image> whichColor(String color) {
		switch (color) {
		case "red":
			return images.redBirdImages;
		case "blue":
			return images.blueBirdImages;
		case "black":
			return images.blackBirdImages;
		default:
			break;
		}
		return null;
	}
}
