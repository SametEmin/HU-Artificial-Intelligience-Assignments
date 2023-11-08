

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.image.Image;
/**
 * The Images class represents a collection of image resources used in the application.
 * It provides access to various lists of images, such as red bird images, black bird images, blue bird images,
 * background images, crosshair images, and foreground images.
 */
public class Images {
	public List<Image> redBirdImages = new ArrayList<>();
	public List<Image> blackBirdImages = new ArrayList<>();
	public List<Image> blueBirdImages = new ArrayList<>();
	public List<Image> backgroundImages = new ArrayList<>();
	public List<Image> crosshairImages = new ArrayList<>();
	public List<Image> foregroundImages = new ArrayList<>();
	public Image welcome=new Image("assets\\welcome\\1.png");
	


	/**
     * Constructs an instance of the Images class and initializes the image lists.
     * It loads the images from the corresponding asset paths.
     */
	public Images() {
		for (int i = 1; i < 9; i++) {
			redBirdImages.add(new Image(String.format("assets\\duck_red\\%d.png", i)));
			blueBirdImages.add(new Image(String.format("assets\\duck_blue\\%d.png", i)));
			blackBirdImages.add(new Image(String.format("assets\\duck_black\\%d.png", i)));
		}
		for (int i = 1; i < 8; i++) {
			crosshairImages.add(new Image(String.format("assets\\crosshair\\%d.png", i)));
		}
		for (int i = 1; i < 7; i++) {
			foregroundImages.add(new Image(String.format("assets\\foreground\\%d.png", i)));
			backgroundImages.add(new Image(String.format("assets\\background\\%d.png", i)));
		}
	}

	public List<Image> getRedBirdImages() {

		return redBirdImages; 
	}

	public List<Image> getBlueBirdImages() {

		return blueBirdImages;
	}

	public List<Image> getBlackBirdImages() {

		return blackBirdImages;
	}

	
}
