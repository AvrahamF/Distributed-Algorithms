// אלגוריתמים מבוזרים - עבודה מסכמת - מגיש: פלדמן אברהם 307408914.

package com.company;

import java.io.*;
import java.net.*;
public class Sender extends Thread{

    int from_pID;
    int to_pID;
    Socket requestSocket;
    ObjectOutputStream out;
    String message;

    Sender(int from_pID, int to_pID, String message){
        this.from_pID = from_pID;
        this.to_pID   = to_pID;
        this.message  = message;
    }
    public void run()
    {
        try{
            requestSocket = new Socket(Driver.host, this.to_pID + Driver.minPortNumber);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            send(message);
        }
        catch(UnknownHostException unknownHost){
            System.err.println("You are trying to connect to an unknown host!");
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
            try{
                out.close();
                requestSocket.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
    }

    void send(String msg)
    {
        try{
            out.writeObject(this.from_pID + "," + this.to_pID + "," + msg);
            out.flush();
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
}