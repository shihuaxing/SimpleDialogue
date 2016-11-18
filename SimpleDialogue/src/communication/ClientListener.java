package communication;

import graphic_interface.Client;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Rikkardo on 10/11/2016.
 */
public class ClientListener implements Runnable {

    private Client client;
    private BufferedReader in;
    public static boolean isRunning;

    public ClientListener(Client client, BufferedReader in){
        this.client = client;
        this.in = in;
        isRunning = false;
    }

    @Override
    public void run() {
        String message = "";
        try {
            while(!isRunning){
                message = in.readLine();
                isRunning = message.equals("READY");
            }
            client.setConnected();
            while (isRunning) {
                message = in.readLine();
                if (message.startsWith("MESSAGE")) {
                    client.showMessage("Operator:" + message.substring(7));
                } else if(message.startsWith("BROWSE")){
                    browse(message.substring(7));
                } else if(message.startsWith("CLOSE")){
                    client.close(false, "Connection closed by server.", "");
                }
            }
        }catch(IOException e){
            client.close(false, "", "Connection closed not properly.");
        }
    }

    public void close(){
        isRunning = false;
    }

    public void browse(String url) throws IOException {
        Desktop.getDesktop().browse(java.net.URI.create(url));
    }

}
