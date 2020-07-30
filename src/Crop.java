/**
 * Crop class to store plant data
 * @author Nathan Stefanik
 * @version 1.1
 */
public class Crop {
    private Controller controller;
    private String type;
    private String imageLink;
    private int age;
    private int cropIndex;
    private int waterLevel; // ranges from 0 - 3, 0 is bone dry
    private boolean isDead = false;
    private int sellPrice;
    private boolean deadBoi = false;

    /**
     * Primary constructor
     * @param controller the main controller object
     * @param type the type of crop
     * @param cropIndex the index of the crop in the farm crop list
     */
    public Crop(Controller controller, String type, int cropIndex) {
        this.controller = controller;
        this.type = type;
        this.age = getAge();
        this.imageLink = updateImageLink();
        this.cropIndex = cropIndex;
        if (type.equals("")) {
            this.waterLevel = -1;
        } else {
            this.waterLevel = 1;
        }
        this.sellPrice = MarketItem.createPrice(controller, type);
    }

    /**
     * Grow method to be called by day functionality.
     * Needs to be implemented elsewhere.
     */
    public void grow() {
        this.age = Math.min(age + 1, 3);
        if (this.age == 3) { // just became fully grown
            sellPrice = MarketItem.getItemSellingPrice(controller, type);
        }
        this.imageLink = updateImageLink();
    }

    /**
     * Increases water level by 1, has a max of 10.
     */
    public void water() {
        this.waterLevel = Math.min(waterLevel + 1, 3);
        updateImageLink();
    }

    /**
     * Checks if able to harvest.
     * @return true if mature, false otherwise.
     */
    public boolean isHarvestable() {
        return getAge() == 3;
    }

    /**
     * Getter for age of crop.
     * @return int for age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Setter for age of crop.
     * @param a The new age.
     */
    public void setAge(int a) {
        age = a;
    }

    /**
     * Getter for type of crop
     * @return String
     */
    public String getType() {
        return this.type;
    }

    /**
     * Setter for type of crop
     * @param type the new crop type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the link for the correct crop image
     * @return the image URL
     */
    public String updateImageLink() {
        if (type.equals("")) {
            imageLink = "images/brown_background.png";
        } else if (isDead) {
            imageLink = "images/dead_all.png";
        } else {
            if (getAge() == 0) {
                imageLink = "images/age_0_all.png";
            } else if (type.toLowerCase().equals("wheat")) {
                if (getAge() == 1) {
                    imageLink = "images/age_1_wheat.png";
                } else if (getAge() == 2) {
                    imageLink = "images/age_2_wheat.png";
                } else {
                    imageLink = "images/age_3_wheat.png";
                }
            } else if (type.toLowerCase().equals("potatoes")) {
                if (getAge() == 1) {
                    imageLink = "images/age_1_potatoes.png";
                } else if (getAge() == 2) {
                    imageLink = "images/age_2_potatoes.png";
                } else {
                    imageLink = "images/age_3_potatoes.png";
                }
            } else if (type.toLowerCase().equals("carrots")) {
                if (getAge() == 1) {
                    imageLink = "images/age_1_carrots.png";
                } else if (getAge() == 2) {
                    imageLink = "images/age_2_carrots.png";
                } else {
                    imageLink = "images/age_3_carrots.png";
                }
            }
        }
        return imageLink;
    }

    /**
     * Getter for crop index
     * @return int
     */
    public int getCropIndex() {
        return cropIndex;
    }

    /**
     * Setter for crop index
     * @param cropIndex int
     */
    public void setCropIndex(int cropIndex) {
        this.cropIndex = cropIndex;
    }

    /**
     * Getter for current water level
     * @return the water level
     */
    public int getWaterLevel() {
        return this.waterLevel;
    }

    /**
     * Getter for if crop is dead.
     * @return boolean if it is dead.
     */
    public boolean getIsDead() {
        return this.isDead;
    }

    /**
     * Setter for if crop is dead.
     * @param isDead boolean if it is dead.
     */
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
        updateImageLink();
    }

    /**
     * Getter for the sell price.
     * @return int with the sell price.
     */
    public int getSellPrice() {
        return sellPrice;
    }

    /**
     * Getter for if crop is dead.
     * @return boolean if it is dead.
     */
    public boolean isDeadBoi() {
        return deadBoi;
    }

    /**
     * Setter for if crop is dead.
     * @param deadBoi boolean if it is dead.
     */
    public void setDeadBoi(boolean deadBoi) {
        this.deadBoi = deadBoi;
        updateImageLink();
    }

    /**
     * Setter for setting the water level.
     * @param waterLevel The new water level.
     */
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
        updateImageLink();
    }

    /**
     * Getter for an image.
     * @return String of the image url.
     */
    public String getImageLink() {
        return imageLink;
    }
}