package com.ubante.intervalometer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Con_0 on 1/27/14.
 */
public class Output {

    private Settings s;
    private Movie m;
    private Reality r;

    /** This will consider possible problems. */
    void displayNotes() {
        List<String> notes = new ArrayList<String>();

        // check for over exposure
        float exposure = s.getExposure();
        double stops = Settings.exposureToStops(exposure);
        notes.add(String.format("The exposure is %1.1f times (%1.1f stops) that of the Sunny 16 rule.",
                exposure, stops));

        // check for space usage on memory card
        float totalCardSpaceUsed = s.getNumberOfFrames()*r.getImageSize();
        int cardCapacity = r.getCardCapacity();
        float percent = totalCardSpaceUsed / cardCapacity * 100 / 1024;
        notes.add(String.format("This sequence will take %.1f MB on your %d GB card (%.1f%%).",
                totalCardSpaceUsed,
                cardCapacity,
                percent));
        if (percent > 50) {
            notes.add("Warning: make sure you have space on your memory card.");
        }

        // check for blur from long shutter speeds
        if (Settings.RULEOFSIXHUNDRED/s.getFocalLength() < s.getShutterSpeed()) {
            notes.add(String.format(
                    "Warning: shutter speed too slow for focal length (%d mm) and will cause motion blur",
                    s.getFocalLength()));
        }

        // check for 400 frame limit of LRTimelapse
        if (s.getNumberOfFrames() > Movie.MAXFRAMESALLOWEDBYFREETLTIMELAPSE) {
            notes.add(String.format(
                    "Warning: %d is too many frames for one movie made with the free version of TLTimelapse",
                    s.getNumberOfFrames()));
        }

        // check for long sequences
        if (r.getDurationHours() > 2) {
            notes.add(String.format("Warning: your duration (%.2f) is >2hours - consider Aperature priority.",
                    r.getDurationHours()));
        }

        // check for batteries
        float batteriesNeeded = r.getBatteryUsage(s.getNumberOfFrames());
        if (batteriesNeeded > 1) {
            notes.add(String.format("Fatal: insufficient battery power; you need %f batteries.",
                    batteriesNeeded));
        } else if (batteriesNeeded > 0.8) {
            notes.add(String.format("Warning: your battery usage is very high (%.1f%%) - consider fresh batteries.",
                    batteriesNeeded*100));
        }

        // XXX since we're always outputting, maybe we don't need this if.
        if (! notes.isEmpty()) {
            System.out.println("-------------------------------\nNotes:");
            for ( String note : notes ) {
                System.out.println(note);
            }
        }
    }

    void display() {
        System.out.println("\n===============================");
        System.out.printf("%s\n", m.getTitle());
        System.out.println("-------------------------------\nSettings:");
        System.out.printf("\tnumber of frames\t%8d\n", s.getNumberOfFrames());
        System.out.printf("\tshutter speed\t\t\t%10.5f\n", s.getShutterSpeed());
        System.out.printf("\tdelay\t\t\t\t%10.1f\n", s.getIntervalBetweenFrames()); // weird that this wants a float
        System.out.println("-------------------------------\nMovie:");
        System.out.printf("\tduration (seconds)\t%10.1f\n", m.getDurationSeconds());
        System.out.printf("\tduration (minutes)\t%10.1f\n", m.getDurationMinutes());
        System.out.printf("\tFPS\t\t\t\t\t%10.1f\n", m.getFPS());
        System.out.printf("\tspeedup\t\t\t\t%10.1f\n", m.getSpeedUp(r.getDurationSeconds()));
        System.out.println("-------------------------------\nReality:");
        System.out.printf("\tduration (seconds)\t%10.1f\n", r.getDurationSeconds());
        System.out.printf("\tduration (minutes)\t%10.1f\n", r.getDurationMinutes());
        System.out.printf("\tduration (hours)\t%10.1f\n", r.getDurationHours());
        System.out.printf("\ttime now\t\t\t\t%s\n",r.getNow().getTime());
        System.out.printf("\tsequence start\t\t\t%s\n",r.getSequenceStart().getTime());
        System.out.printf("\tsequence finish\t\t\t%s\n",r.getSequenceFinish().getTime());
        System.out.printf("\ttotal elapsed time\t%10.1f\n",r.getTotalElapsedTimeSeconds());

        displayNotes();
    }
    /** Constructor */
    Output (Settings s, Movie m, Reality r) {
        this.s = s;
        this.m = m;
        this.r = r;
    }

}
