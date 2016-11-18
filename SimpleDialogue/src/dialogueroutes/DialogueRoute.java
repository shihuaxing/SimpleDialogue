package dialogueroutes;

import other.utility;

import java.util.ArrayList;

/**
 * Created by Rikkardo on 30/10/2016.
 */
public class DialogueRoute {

    private ArrayList<String> messages;
    private ArrayList<String> entrance;
    private ArrayList<String> finals;

    int messageIndex;
    int entranceIndex;
    int finalIndex;

    int type;


    public DialogueRoute(String fileName, int type) {
        messages = utility.createMessages(fileName, "message");
        entrance = utility.createMessages(fileName, "entrance");
        finals = utility.createMessages(fileName, "final");
        messageIndex = 0;
        entranceIndex = 0;
        finalIndex = 0;
        this.type = type;
    }

    public String getNext(boolean last){
        String response = "";
        if(last || entranceIndex==0){
            if(messageIndex<messages.size()){
                response = messages.get(messageIndex);
                messageIndex++;
            } else if(type==0){
                messageIndex = 0;
                response = getNext(true);
            } else if(type==1){
                response = createCasualMessage();
            } else if(type==2){
                response = "BROWSE " + finals.get(finalIndex);
                finalIndex++;
            }
        } else{
            if(entranceIndex<entrance.size()){
                response = entrance.get(entranceIndex);
                entranceIndex++;
            } else {
                response = getNext(true);
            }
        }
        return response;
    }

    private String createCasualMessage(){
        String response = "";
        int length = (int)(Math.random()*20) + 1;
        for(int i = 0; i<length; i++){
            response += (char)((Math.random()*26)+97);
        }
        response = (char)((int)response.charAt(0)-32) + response.substring(1);
        return response;
    }

}
