package com.example.geonotes;

public class TextNote {
    public String location;
    public String message;
    public String date;
    public String time;


    public TextNote(){

    }

    public TextNote(String message, String date, String time, String location){
        this.message = message;
        this.date = date;
        this.time = time;
        this.location = location;

    }

}
