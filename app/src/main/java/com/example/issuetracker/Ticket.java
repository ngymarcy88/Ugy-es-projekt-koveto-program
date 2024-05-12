package com.example.issuetracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ticket_table")
public class Ticket {
    public static final String NEW = "NEW";
    public static final String IN_PROGRESS = "IN_PROGRESS";
    public static final String DONE = "DONE";
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String ticket;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
