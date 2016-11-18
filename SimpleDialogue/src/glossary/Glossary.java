package glossary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Rikkardo on 01/11/2016.
 */
public class Glossary {

    private ArrayList<String> glossary; //list of sentences
    private ArrayList<String> singleWords;  //list of words composing sentences, no duplicates

    public Glossary(String fileName){
        try{

            //set ready data structures in function of building the glossary
            glossary = new ArrayList<>();
            singleWords = new ArrayList<>();
            ArrayList<String> sentences = new ArrayList<>();
            ArrayList<String> words = new ArrayList<>();
            Scanner s = new Scanner(new File(fileName));
            String line;
            int level = 0;

            //read line per line the data file, that must follow the required format
            while(s.hasNext()){
                line = s.nextLine();
                line = clean(line); //"clean" the line from undesirable characters and ensure it is saved in lower case

                //analyze the line and make the direct operations
                if(!line.startsWith("<")){  //check if normal word to compose or store
                    words.add(line);
                    if (singleWords.indexOf(line) < 0) {
                        singleWords.add(line);
                    }
                } else if(line.equals("</w"+(level)+">")){  //check if a sequence of words or sentences is finished
                    if(level==0){
                        sentences = words;
                    } else {
                        ArrayList<String> temp = new ArrayList<>();

                        //elaborate data to compose sentences
                        for (int i = 0; i < sentences.size(); i++) {
                            for (int j = 0; j < words.size(); j++) {
                                if(!words.get(j).equals("") && !sentences.get(i).equals("")) {
                                    temp.add(sentences.get(i) + " " + words.get(j));
                                } else{
                                    temp.add(sentences.get(i));
                                }
                            }
                        }
                        sentences = temp;
                    }
                    words = new ArrayList<>();
                    level++;
                } else if(line.equals("</sentence>")){  //check if a sequence of sentences is finished

                    //add composed sentences to the glossary
                    for(int i = 0; i<sentences.size(); i++){
                        glossary.add(sentences.get(i));
                    }
                    sentences = new ArrayList<>();
                    level = 0;
                }
            }
        } catch(FileNotFoundException e){
            System.out.println("File " + fileName + "not found.");
        }
    }


    public String clean(String s){
        s = s.replaceAll("  ", "");
        s = s.replaceAll(" ", "");
        s = s.toLowerCase();
        return s;
    }

    public boolean isPresent(String message){
        boolean isPresent = false;
        message = message.toLowerCase();
        int words = message.split(" ").length;
        for(int i = 0; i<words && !isPresent; i++) {
            Scanner s = new Scanner(message);
            String messageTemp = "";
            for(int j = 0; j<i; j++){
                s.next();
            }
            while (s.hasNext() && !isPresent) {
                if (!messageTemp.equals("")) {
                    messageTemp += " ";
                }
                messageTemp += s.next();
                isPresent = glossary.indexOf(messageTemp) >= 0;
            }
        }
        return isPresent;
    }

    public String getMatchingSentence(String message){
        boolean isPresent = false;
        message = message.toLowerCase();
        int words = message.split(" ").length;
        int n = -1;
        for(int i = 0; i<words; i++) {
            Scanner s = new Scanner(message);
            String messageTemp = "";
            for(int j = 0; j<i; j++){
                s.next();
            }
            while (s.hasNext()) {
                String wordTemp = s.next();
                if (!messageTemp.equals("") && !wordTemp.equals(" ")) {
                    messageTemp += " ";
                }
                messageTemp += wordTemp;
                if(glossary.indexOf(messageTemp) >= 0){
                    if(!isPresent){
                        isPresent = true;
                        n = glossary.indexOf(messageTemp);
                    }else if(glossary.get(n).length()<messageTemp.length()) {
                        n = glossary.indexOf(messageTemp);
                    }
                }
            }
        }
        return glossary.get(n);
    }

    public boolean languageKnown(String message){
        boolean unknown = true;
        Scanner s = new Scanner(message);
        String word = "";
        while(s.hasNext() && unknown){
            word = s.next();
            unknown = singleWords.indexOf(word)<0;
        }
        return unknown;
    }

    public String getNextWord(String message){
        Scanner s = new Scanner(message);
        boolean matching = false;
        String temp = "";
        while(s.hasNext() && !matching){
            if(!temp.equals("")){
                temp += " ";
            }
            temp += s.next();
            matching = glossary.indexOf(temp)>=0;
        }
        return s.next();
    }

    public boolean someWords(String message){
        boolean result = false;
        Scanner s = new Scanner(message);
        while(s.hasNext() && !result){
            result = singleWords.indexOf(s.nextLine())>0;
        }
        return result;
    }

    public void show(){
        for(int i = 0; i<glossary.size(); i++){
            System.out.println(glossary.get(i));
        }
    }

}
