// אלגוריתמים מבוזרים - עבודה מסכמת - מגיש: פלדמן אברהם 307408914.

package com.company;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Processor extends Thread {

    ServerSocket providerSocket;
    Socket connection = null;

    int pID;
    int parentID = -1;

    ArrayList<Integer> neighbors = new ArrayList<Integer>();
    ArrayList<Integer> children  = new ArrayList<Integer>();
    ArrayList<Integer> others    = new ArrayList<Integer>();

    String message;

    Processor(int pID, ArrayList<Integer> neighbors) throws IOException {

        this.pID = pID;

        for (int i = 0; i < neighbors.size(); i++)
            this.neighbors.add(neighbors.get(i));

    }

    public void run()
    {
        try {
            providerSocket = new ServerSocket(this.pID + Driver.minPortNumber, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                connection = providerSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }

            // new thread for a client
            try {
                Receiver receiv = new Receiver(connection,
                                               pID,
                                               parentID,
                                               neighbors,
                                               message);
                receiv.run();

                this.message  = receiv.message;
                this.parentID = receiv.parentID;
                if (receiv.child != -1)
                    this.children.add(receiv.child);
                if (receiv.other != -1)
                    this.others.add(receiv.other);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (this.neighbors.size() == this.children.size() + this.others.size()){
                System.out.println("\n================================================\n" +
                                   "I  Processor Number [" + this.pID      + "]\n" +
                                   "My Parent Number is [" + this.parentID + "]\n" +
                                   "My Neighbors     is "  + this.neighbors.toString() + "\n" +
                                   "My Children      is "  + this.children.toString() + "\n" +
                                   "My Others        is "  + this.others.toString() +
                                   "\n================================================\n");
                break;
            }
        }
    }

    void startMessaging() throws IOException {
        new Sender(this.pID, this.pID, "root").run();
    }
}