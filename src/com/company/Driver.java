// אלגוריתמים מבוזרים - עבודה מסכמת - מגיש: פלדמן אברהם 307408914.

package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Driver {

    public static final String host = "localhost";
    public static final int minPortNumber = 1025;

    public static void main(String args[]) throws IOException {

        Processor  p0 = new Processor(0, new ArrayList<Integer>(Arrays.asList( 1          )));
        Processor  p1 = new Processor(1, new ArrayList<Integer>(Arrays.asList( 0, 2, 3, 4 )));
        Processor  p2 = new Processor(2, new ArrayList<Integer>(Arrays.asList( 1, 4       )));
        Processor  p3 = new Processor(3, new ArrayList<Integer>(Arrays.asList( 1, 5, 6, 7 )));
        Processor  p4 = new Processor(4, new ArrayList<Integer>(Arrays.asList( 1, 2, 6    )));
        Processor  p5 = new Processor(5, new ArrayList<Integer>(Arrays.asList( 3, 6, 7    )));
        Processor  p6 = new Processor(6, new ArrayList<Integer>(Arrays.asList( 3, 4, 5, 7 )));
        Processor  p7 = new Processor(7, new ArrayList<Integer>(Arrays.asList( 3, 5, 6    )));
//
        p0.start();
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
        p6.start();
        p7.start();
//
        p0.startMessaging();
    }
}