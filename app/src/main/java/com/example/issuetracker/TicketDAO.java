package com.example.issuetracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface TicketDAO {

    @Insert
    void insert(Ticket ticket);

    @Update
    void update(Ticket ticket);

    @Delete
    void delete(Ticket ticket);

    @Query("Select * FROM ticket_table")
    Ticket[] loadAll();

    @Query("SELECT * FROM ticket_table WHERE ticket LIKE :ticketName")
    public Ticket findByTicketName(String ticketName);

}
