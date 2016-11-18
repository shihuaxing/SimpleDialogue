package dialogue;

import com.sun.deploy.util.StringUtils;
import dialogueinfo.DialogueInfo;
import dialogueroutes.DialogueRoute;
import glossary.Glossary;
import other.Displayable;
import other.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Rikkardo on 27/10/2016.
 */
public class DialogueManager{

    private ArrayList<String> dialogue; //Keep a copy of the dialogue, to be saved
    private Glossary[] glossaries;  //Glossaries to identify messages type
    private DialogueRoute[] routes;
    private int idMessageType;
    /**
     * int idMessageType define how to reply to a type of message
     * this ID's are used in Glossary[]: they define the position for glossaries, if needed to enter into a idMessageType
     * in order to improve a cleaner and shorter code, states will be divided in function of the way of the response
     * they need
     * a more clean explanation of these indexes use can be seen in the second part of the method "answers"
     * N.B. actions are to be intended as main concept of received messages
     *
     * types that goes simply in route by glossary:
     * greeting = 0
     * asked for bot name = 1
     * asked if bot thinks = 2
     * asked for bot thoughts = 3
     * asked for bot activities = 4
     * asked how does the bot feel = 5
     * asked for a song = 6
     * asked for a story = 7
     *
     * types that goes in info request:
     * asked for time = 20
     * asked for date = 21
     * asked for today weather = 22
     * asked to search something = 23
     *
     * types that ask about the dialogue messages:
     * asked for message n = 40
     * asked for his message n = 41
     * asked for bot message n = 42
     * asked for his first question = 43
     * asked for bot first question = 44
     *
     * types that takes user info:
     * asked for its name = 60
     * asked for its nickname = 61
     * asked for its gender = 62
     * asked for its age = 63
     * asked for its feeling = 64
     * asked for its job = 65
     * asked for its hobby = 66
     * asked all about itself = 67
     *
     * types that goes to register user info:
     * gave name = 80
     * gave nickname = 81
     * gave age = 82
     * gave gender = 83
     * gave feeling = 84
     * gave job = 85
     * gave general info = 86
     *
     * types to check at the begin and at the end:
     * repeating = 100
     * nothing said = 101
     * nothing found = 102
     * not this language = 103
     * bad words = 104
     *
     */
    private DialogueInfo info;
    private Interlocutor interlocutor;

    public DialogueManager() {
        dialogue = new ArrayList<>();
        glossaries = new Glossary[120];
        glossaries[0] = new Glossary("src/glossary/files/glossary_greeting.xml");
        glossaries[1] = new Glossary("src/glossary/files/glossary_whats_your_name.xml");
        glossaries[2] = new Glossary("src/glossary/files/glossary_do_you_think.xml");
        glossaries[3] = new Glossary("src/glossary/files/glossary_what_are_you_thinking.xml");
        glossaries[4] = new Glossary("src/glossary/files/glossary_your_activities.xml");
        glossaries[5] = new Glossary("src/glossary/files/glossary_how_are_you.xml");
        glossaries[6] = new Glossary("src/glossary/files/glossary_sing_for_me.xml");
        glossaries[7] = new Glossary("src/glossary/files/glossary_tell_me_a_story.xml");
        glossaries[20] = new Glossary("src/glossary/files/glossary_what_time.xml");
        glossaries[21] = new Glossary("src/glossary/files/glossary_what_date.xml");
        glossaries[22] = new Glossary("src/glossary/files/glossary_weather.xml");
        glossaries[23] = new Glossary("src/glossary/files/glossary_search_that.xml");
        glossaries[40] = new Glossary("src/glossary/files/glossary_message_n.xml");
        glossaries[41] = new Glossary("src/glossary/files/glossary_my_message_n.xml");
        glossaries[42] = new Glossary("src/glossary/files/glossary_your_message_n.xml");
        glossaries[43] = new Glossary("src/glossary/files/glossary_my_question_n.xml");
        glossaries[44] = new Glossary("src/glossary/files/glossary_your_question_n.xml");
        glossaries[60] = new Glossary("src/glossary/files/glossary_whats_my_name.xml");
        glossaries[61] = new Glossary("src/glossary/files/glossary_whats_my_nickname.xml");
        glossaries[62] = new Glossary("src/glossary/files/glossary_whats_my_gender.xml");
        glossaries[63] = new Glossary("src/glossary/files/glossary_how_old_am_i.xml");
        glossaries[64] = new Glossary("src/glossary/files/glossary_how_am_i_feeling.xml");
        glossaries[65] = new Glossary("src/glossary/files/glossary_whats_my_job.xml");
        glossaries[66] = new Glossary("src/glossary/files/glossary_whats_my_hobby.xml");
        glossaries[67] = new Glossary("src/glossary/files/glossary_all_about_me.xml");
        glossaries[80] = new Glossary("src/glossary/files/glossary_my_name_is.xml");
        glossaries[81] = new Glossary("src/glossary/files/glossary_my_nickname_is.xml");
        glossaries[82] = new Glossary("src/glossary/files/glossary_my_age_is.xml");
        glossaries[83] = new Glossary("src/glossary/files/glossary_my_gender_is.xml");
        glossaries[84] = new Glossary("src/glossary/files/glossary_i_am_feeling.xml");
        glossaries[85] = new Glossary("src/glossary/files/glossary_my_job_is.xml");
        glossaries[86] = new Glossary("src/glossary/files/glossary_my_hobby_is.xml");
        glossaries[104] = new Glossary("src/glossary/files/glossary_bad_words.xml");
        routes = new DialogueRoute[200];
        routes[0] = new DialogueRoute("src/dialogueroutes/files/route_greet.xml", 0);
        routes[1] = new DialogueRoute("src/dialogueroutes/files/route_my_name_is.xml", 0);
        routes[2] = new DialogueRoute("src/dialogueroutes/files/route_are_you_intelligent.xml", 2);
        routes[3] = new DialogueRoute("src/dialogueroutes/files/route_i_am_thinking_about.xml", 0);
        routes[4] = new DialogueRoute("src/dialogueroutes/files/route_activities.xml", 2);
        routes[5] = new DialogueRoute("src/dialogueroutes/files/route_how_do_you_feel.xml", 0);
        routes[6] = new DialogueRoute("src/dialogueroutes/files/route_sing.xml", 0);
        routes[7] = new DialogueRoute("src/dialogueroutes/files/route_stories.xml", 0);
        routes[100] = new DialogueRoute("src/dialogueroutes/files/route_repeat.xml", 0);
        routes[101] = new DialogueRoute("src/dialogueroutes/files/route_nothing_said.xml", 0);
        routes[102] = new DialogueRoute("src/dialogueroutes/files/route_cant_understand.xml", 0);
        routes[103] = new DialogueRoute("src/dialogueroutes/files/route_unknown_language.xml", 1);
        routes[104] = new DialogueRoute("src/dialogueroutes/files/route_bad_words.xml", 0);
        info = new DialogueInfo();
        interlocutor = new Interlocutor();
    }

    /**
     * Simply return the response for a message
     */
    public String answers(String message){
        dialogue.add("HUMAN: " + message);
        String response = "";
        int lastId = idMessageType;
        idMessageType = assignId(prepare(message));
        if (idMessageType >= 0 && idMessageType < 20 || idMessageType>=100) {
            response = routes[idMessageType].getNext(idMessageType==lastId);
        } else if (idMessageType >= 20 && idMessageType < 40) {
            response = info.getInfoByCode(getInfoFromMessage(message, idMessageType), idMessageType - 20);
        } else if (idMessageType >= 40 && idMessageType < 60) {
            response = getMessageByPosition(message, idMessageType - 40);
        } else if (idMessageType >= 60 && idMessageType < 80) {
            response = interlocutor.getInfo(idMessageType - 60);
        } else if (idMessageType >= 80 && idMessageType < 100) {
            response = interlocutor.setInfo(getInfoFromMessage(message, idMessageType), idMessageType-80);
        }
        dialogue.add("BOT: " + response);
        if(!response.startsWith("BROWSE")){
            response = "MESSAGE " + response;
        }
        return response;
    }

    /**
     * Used to identify the type of message
     */
    private int assignId(String message){
        idMessageType = -1;
        boolean analyzed = false;
        if(repeated()){
            idMessageType = 100;
        } else if(message.equals("")) {
            idMessageType = 101;
        } else if(glossaries[104].someWords(message)) {
            idMessageType = 104;
        } else{
            for (int i = 0; i < glossaries.length; i++) {
                if (glossaries[i] != null) {
                    if (glossaries[i].isPresent(message)) {
                        if (!analyzed) {
                            idMessageType = i;
                            analyzed = true;
                        } else {
                            String s1 = glossaries[idMessageType].getMatchingSentence(message);
                            String s2 = glossaries[i].getMatchingSentence(message);
                            if (s2.length() > s1.length()) {
                                idMessageType = i;
                            }
                        }
                    }
                }
            }
            if (idMessageType == -1) {
                if (!unknownLanguage(message)) {
                    idMessageType = 102;
                } else {
                    idMessageType = 103;
                }
            }
        }
        return idMessageType;
    }

    /**
     * "Clean" a message from everything that could be dangerous
     */
    private String prepare(String message){
        String chars = ".;,:?!";
        for(int i = 0; i<chars.length(); i++){
            message = message.replace(""+chars.charAt(i), "");
        }
        return message;
    }

    /**
     * Look in the conversation for repeating of message
     */
    private boolean repeated(){
        boolean repeated = true;
        String message = dialogue.get(dialogue.size()-1);
        if(dialogue.size()>10) {
            for (int i = 0; i < 5; i++) {
                if (!dialogue.get(dialogue.size() - (i * 2) -1).equals(message)) {
                    repeated = false;
                }
            }
        } else{
            repeated = false;
        }
        return repeated;
    }

    /**
     * Define if a message has matching with at least one word in a glossary
     */
    private boolean unknownLanguage(String message){
        boolean unknown = true;
        for(int i = 0; i<glossaries.length && unknown; i++){
            if(glossaries[i]!=null){
                unknown = !glossaries[i].languageKnown(message);
            }
        }
        return unknown;
    }

    /**
     * Save dialogue when connection is closed
     */
    public void saveDialogue(){
        String saveTime = utility.getTime();
        File saveDialogue = new File(saveTime + ".dial");
        try {
            PrintWriter p = new PrintWriter(saveDialogue);
            for(int i = 0; i<dialogue.size(); i++){
                p.println(dialogue.get(i));
            }
            p.close();
        } catch(FileNotFoundException e){
            System.out.println("Error: can't create saving file.");
        }
    }

    /**
     * Manages (from numbers 1-10) to find the one you specified by position
     */
    private String getMessageByPosition(String message, int code){
        String response = "";
        int n = -1;
        String numericPosition[] = {"first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth"};
        String last = "last";
        Scanner s = new Scanner(message);
        String temp = "";
        boolean found = false;
        while(s.hasNext() && !found){
            temp = s.next();
            try{
                n = Integer.parseInt(temp);
                found = true;
            } catch(NumberFormatException e) {
                for (int i = 0; i < numericPosition.length && !found; i++) {
                    found = temp.equals(numericPosition[i]);
                    n = i;
                }
            }
        }
        if(found){
            if(code==0){
                response = "It was: " + "\"" + dialogue.get(n).substring(dialogue.get(n).indexOf(":")+1) + "\"";
            } else if(code==1){
                response = countMessagesWithConditions("HUMAN:", "", n);
            } else if(code==2){
                response = countMessagesWithConditions("BOT:", "", n);
            } else if(code==3){
                response = countMessagesWithConditions("HUMAN:", "?", n);
            } else if(code==4){
                response = countMessagesWithConditions("BOT:", "?", n);
            }
        }
        return response;
    }

    /**
     * Necessary to the function getMessageByPosition
     */
    private String countMessagesWithConditions(String mustContainA, String mustContainB, int n){
        String response = "I didn't find anything in our conversation.";
        int counter = 0;
        for(int i = 0; i<dialogue.size() && counter<=n; i++){
            if(dialogue.get(i).contains(mustContainA) && dialogue.get(i).contains(mustContainB) && !dialogue.get(i).contains("BROWSE")){
                if(counter==n){
                    response = "It was: " + "\"" + dialogue.get(i).substring(dialogue.get(i).indexOf(":")+1) + "\"";
                }
                counter++;
            }
        }
        return response;
    }

    /**
     * When is found that a message contains information, this function divide them from the rest of the text
     */
    private String getInfoFromMessage(String message, int id){
        String notInfo = glossaries[id].getMatchingSentence(message);
        String info = "";
        info = message.replace(notInfo, "");
        if(id==82){
            info = findInt(info);
        }
        return info;
    }

    private String findInt(String info){
        Scanner s = new Scanner(info);
        String temp = "0";
        boolean found = false;
        while(s.hasNext() && !found){
            temp = s.next();
            if(temp.matches("[-+]?\\d*\\.?\\d+")){
                found = true;
            }
        }
        return temp;
    }

}
