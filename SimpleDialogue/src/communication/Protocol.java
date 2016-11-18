package communication;

import dialogue.DialogueManager;
import other.Displayable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Rikkardo on 26/10/2016.
 */
public class Protocol implements Runnable{

    private Socket connection;
    private Displayable display;
    private boolean isRunning;
    private DialogueManager dm;
    private PrintWriter out;
    private BufferedReader in;

    public Protocol(Socket connection, Displayable errors_display){
        this.connection = connection;
        this.display = errors_display;
        isRunning = true;
        dm = new DialogueManager();
        try {
            out = new PrintWriter(connection.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch(IOException e){
            errors_display.display("Could not establish a new connection.");
        }
    }

    @Override
    public void run() {
        String message = "";
        out.println("READY");
        try {
            while(isRunning) {
                message = in.readLine();
                if (message.startsWith("MESSAGE")) {
                    out.println(dm.answers(message.substring(8)));
                } else if (message.startsWith("CLOSE")) {
                    close(false, "User disconnected.");
                }
            }
        } catch(Exception e){
            display.display("User has been disconnected.");
        }
    }

    public void close(boolean requested, String info){
        dm.saveDialogue();
        if(requested){
            out.println("CLOSE");
        }
        try {
            isRunning = false;
            in.close();
            out.close();
            connection.close();
            display.display(info);
        } catch(IOException e){
            display.display("User disconnected not properly");
        }
    }

    public boolean isRunning(){
        return isRunning;
    }

}
