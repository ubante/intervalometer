package com.ubante.intervalometer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Con_0 on 1/27/14.
 *
 * This will compute some useful stats for timelapse movies.
 * XXX if dark, then change to Darcula mode
 */
public class TimeLapser {

    /** main */
    public static void main(String[] args) {
        Settings s1,s2;
        Movie m1,m2;
        Reality r1,r2;
        Output out1,out2;

        // sunrise
        s1 = new Settings(5,    // seconds between frames
                200,            // number of frames
                1f/10f          // shutter speed
        );
        m1 = new Movie(s1);
        m1.setTitle("Sunrise");
        r1 = new Reality(s1);

        out1 = new Output(s1,m1,r1);
        out1.display();

        // try to streamline
        InputParameters i2 = new InputParameters(5,401,30f,"Milky Way",60f);
        List<InputParameters> inputs = new ArrayList<InputParameters>();
        inputs.add(i2);
        inputs.add(new InputParameters(1,300,1f/60f,"Beach boardwalk2"));
        for ( InputParameters input : inputs ) {
            Settings settings = new Settings (
                    input.getIntervalBetweenFrames(),
                    input.getNumberOfFrames(),
                    input.getShutterSpeed()
            );
            Movie movie = new Movie(settings,input.getTitle());
            movie.setFPS(input.getFps());
            Reality reality = new Reality(settings);
            Output output = new Output(settings,movie,reality);
            output.display();
        }

        // A complicated case
        // Delay the start and catch sunrise
        s2 = new Settings(10,1000,1f);
        s2.setStartDelayHours(6);
        m2 = new Movie(s2,"Shooting in your sleep");
        r2 = new Reality(s2);
        out2 = new Output(s2,m2,r2);
        out2.display();
    }
}
