// אלגוריתמים מבוזרים - עבודה מסכמת - מגיש: פלדמן אברהם 307408914.

package com.company;
import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class Receiver extends Thread {

    Socket connection = null;
    int pID;
    int parentID;

    ArrayList<Integer> neighbors = new ArrayList<Integer>();
    int child = -1;
    int other = -1;


    ObjectInputStream in;
    String message;

    public Receiver(Socket             connection,
                    int                pID,
                    int                parentID,
                    ArrayList<Integer> neighbors,
                    String             message   ) throws IOException {

        this.connection = connection;
        this.pID        = pID;
        this.parentID   = parentID;
        this.neighbors  = neighbors;
        this.message    = message;
    }

    public void run() {
        try {
            in = new ObjectInputStream(connection.getInputStream());
            {
                try {
                    String inString = (String) in.readObject();

                    String[] parts = inString.split(",");
                    int from_pID = Integer.parseInt(parts[0]);
                    String inMessage = parts[2];

                    System.out.println(from_pID + " -> " + this.pID + ": " + inMessage);

                    if (inMessage.equals("root"))
                        if (from_pID == this.pID && this.parentID == -1) {
                            this.parentID = this.pID;
                            this.message = "M";
                            send(this.neighbors, this.message);
                        }

                    if (inMessage.equals("M"))
                        if (this.parentID == -1) {
                            this.parentID = from_pID;
                            this.message = inMessage;
                            for (int i = 0; i < this.neighbors.size(); i++)
                                if (this.neighbors.get(i).equals(from_pID))
                                    this.neighbors.remove(i);
                            send(from_pID, "parent");
                            if (neighbors.size() == 0)
                                return;
                            send(this.neighbors, this.message);
                        }
                        else
                            send(from_pID, "already");

                    else if (inMessage.equals("parent")) {
                        child = from_pID;
                    }

                    else if (inMessage.equals("already")) {
                        other = from_pID;
                    }
                } catch (ClassNotFoundException classNot) {
                    System.err.println("data received in unknown format");
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                in.close();
                connection.close();
                return;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    void send(ArrayList<Integer> to_pIDs, String msg) {

        for (int i = 0; i < to_pIDs.size(); i++) {
            new Sender(this.pID, to_pIDs.get(i), msg).run();
        }
    }

    void send(int to_pID, String msg) {
        new Sender(this.pID, to_pID, msg).run();
    }
}
