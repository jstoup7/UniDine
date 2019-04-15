package com.example.unidine;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;

public class Meeting {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    String date;
    String time;
    String location;
    ArrayList<String> attendees;

    public Meeting(String date, String time, String location, ArrayList<String> attendees) {
        this.date = date;
        this.time = time;
        this.location = location;
        this.attendees = attendees;
    }

    public static String makeMeeting(String date, String time, String location, ArrayList<String> attendees, String myID) {
        attendees.add(myID);
        DatabaseReference meetingsRef = database.getReference("Meetings");
        DatabaseReference meetingRef = meetingsRef.push();
        meetingRef.setValue(new Meeting(date, time, location, attendees));
        String id = meetingRef.getKey();
        DatabaseReference tempRef;
        for (int i = 0; i < attendees.size(); i++) {
            tempRef = database.getReference("Users/" + attendees.get(i) + "/Meetings");
            tempRef.push().setValue(id);
        }
        return id;
    }
}
