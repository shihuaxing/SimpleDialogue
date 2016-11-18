package communication;

import graphic_interface.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Rikkardo on 09/11/2016.
 */
public class ServerListener implements Runnable{

    private boolean isRunning;
    private ServerSocket server;
    private Server s;

    public ServerListener(ServerSocket server, Server s){
        isRunning = true;
        this.server = server;
        this.s = s;
    }

    @Override
    public void run() {
        try {
            while(isRunning){
                Socket connection = server.accept();
                s.addConnection(connection);
            }
        } catch(IOException e){
            s.showInfo("Connections listener closed.");
        }
    }

    public void close(){
        isRunning = false;
        try {
            server.close();
        }catch(IOException e){
            s.showInfo("Can not close connection listener properly.");
        }
    }

}
