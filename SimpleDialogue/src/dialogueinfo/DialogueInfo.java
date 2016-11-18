package dialogueinfo;

import dialogue.Interlocutor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Rikkardo on 01/11/2016.
 */
public class DialogueInfo {

    Calendar c;

    public DialogueInfo(){
        c = Calendar.getInstance();
    }

    public String getInfoByCode(String info, int code){
        String response = "";
        if(code==0){
            response = time();
        } else if(code==1){
            response = date();
        } else if(code==2){
            response = weather();
        } else if(code==3){
            response = createURL(info);
        }
        return response;
    }

    private String time(){
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
        return f.format(c.getTime());
    }

    private String date(){
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        return f.format(c.getTime());
    }

    private String weather(){
        return "It will be very sunny in the afternoon, with possibility of rains in the evening.";
    }

    private String createURL(String info){
        return "BROWSE https://www.google.it/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=" + info.replaceAll(" ", "+");
    }

}
