package dialogue;

import glossary.Glossary;

import java.util.ArrayList;

/**
 * Created by Rikkardo on 30/10/2016.
 */
public class Interlocutor {

    private String name;
    private String nickname;
    private int gender;
    private int age;

    private String feeling;

    private String job;
    private String hobby;

    public Interlocutor(){
        name = "";
        nickname = "";
        gender = -1;
        age = -1;
        feeling = "";
        job = "";
        hobby = "";
    }

    public void setName(String name){
        this.name = name;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setGender(int gender){
        this.gender = gender;
    }

    public void setFeeling(String feeling){ this.feeling = feeling; }

    public void setJob(String job){
        this.job = job;
    }

    public void setHobby(String hobby){
        this.hobby = hobby;
    }

    public String getName(){
        return name;
    }

    public String getNickname(){
        return nickname;
    }

    public int getAge(){
        return age;
    }

    public int getGender(){
        return gender;
    }

    public String getFeeling() { return feeling; }

    public String getJob(){
        return job;
    }

    public String getHobby(){ return hobby; }

    public String getAll(){
        String info = "I just know what you told me.";
        if(!name.equals("")){
            info += "Your name is " + name + ". ";
        }
        if(!nickname.equals("")){
            info += "They call you " + nickname + ". ";
        }
        if(age>-1){
            info += "You are " + age + " years old. ";
        }
        if(gender>-1){
            String g;
            if(gender==1){
                g = "male";
            } else{
                g = "female";
            }
            info += "You are " + g + ". ";
        }
        if(!job.equals("")){
            info += "Your job is " + job + ". ";
        }
        if(!hobby.equals("")){
            info += "Your hobby is " + hobby + ". ";
        }
        return info;
    }

    public String setInfo(String info, int id){
        String response = "";
        if(id==0){
            setName(info);
            response = "That's a nice name " + info + "!";
        } else if(id==1){
            setNickname(info);
            response = "Who calls you " + info + " is cruel...";
        } else if(id==2){
            setAge(Integer.parseInt(info.replaceAll(" old", "")));
            response = "The age of " + info + " is the best you'll ever have!";
        } else if(id==3){
            if(info.equals("male") || info.equals("man") || info.equals("boy") || info.equals("guy")) {
                setGender(1);
                response = "Who need women?!";
            } else{
                setGender(0);
                response = "Who need men?!";
            }
        } else if(id==4){
            setFeeling(info);
            response = "So you feel " + info + "...";
        } else if(id==5){
            setJob(info);
            response = info + " is a nice job!";
        } else if(id==6){
            setHobby(info);
            response = info + " is perfect for my free time.";
        }
        return response;
    }

    public String getInfo(int id){
        String response = "";
        if(id==0){
            response = "Your name is " + getName();
        } else if(id==1){
            response = "They call you " + getNickname();
        } else if(id==2){
            if(getGender()==0) {
                response = "You're female.";
            } else{
                response = "You're male.";
            }
        } else if(id==2){
            response = "You are " + getAge() + " years old";
        } else if(id==4){
            response = "You're feeling " + getFeeling();
        } else if(id==5){
            response = "Your job is " + getJob();
        } else if(id==6){
            response = "Your hobby is " + getHobby();
        } else if(id==7){
            response = getAll();
        }
        return response;
    }

}
