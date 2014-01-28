package com.ubante.intervalometer;

/**
 * Created by Con_0 on 1/27/14.
 */
public class InputParameters {

    private int intervalBetweenFrames, numberOfFrames;
    private float shutterSpeed, fps;
    private String title;

    public int getIntervalBetweenFrames() {
        return intervalBetweenFrames;
    }

    public int getNumberOfFrames() {
        return numberOfFrames;
    }

    public float getShutterSpeed() {
        return shutterSpeed;
    }

    public float getFps() {
        return fps;
    }

    public String getTitle() {
        return title;
    }

    /** Constructors */
    InputParameters(int intervalBetweenFrames,
                    int numberOfFrames,
                    float shutterSpeed,
                    String title,
                    float fps) {
        this.intervalBetweenFrames = intervalBetweenFrames;
        this.numberOfFrames = numberOfFrames;
        this.shutterSpeed = shutterSpeed;
        this.title = title;
        this.fps = fps;
    }

    InputParameters(int intervalBetweenFrames,
                    int numberOfFrames,
                    float shutterSpeed,
                    String title) {
        this(intervalBetweenFrames, numberOfFrames, shutterSpeed, title, Movie.DEFAULTFPS);
    }

    InputParameters(int intervalBetweenFrames,
                    int numberOfFrames,
                    float shutterSpeed) {
        this(intervalBetweenFrames, numberOfFrames, shutterSpeed,
                Movie.DEFAULTTITLE, Movie.DEFAULTFPS);
    }

}
