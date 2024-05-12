package com.example.issuetracker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Ticket.class}, version = 1, exportSchema = false)
public abstract class TicketRoomDatabase extends RoomDatabase  {

    public abstract TicketDAO ticketDAO();

    public static TicketRoomDatabase instance;

    public static TicketRoomDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TicketRoomDatabase.class,
                    "tickets_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
