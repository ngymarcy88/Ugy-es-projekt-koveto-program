package com.example.issuetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ModifyTicketActivity extends AppCompatActivity {

    private Spinner toModifySpinner;
    private Spinner stateSpinner;
    private TicketDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_ticket);
        dao = TicketRoomDatabase.getAppDatabase(this).ticketDAO();

        Ticket[] tickets = dao.loadAll();
        toModifySpinner = findViewById(R.id.modifyTaskSpinner);
        stateSpinner = findViewById(R.id.stateSpinner);

        String[] ticketNames = new String[tickets.length];
        for (int i = 0; i < tickets.length; i++) {
            ticketNames[i] = tickets[i].getTicket();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, ticketNames);
        toModifySpinner.setAdapter(arrayAdapter);
    }

    public void modifyTicket(View view) {
        Intent intent = new Intent(this, IssueTrackingActivity.class);
        Ticket ticket = dao.findByTicketName(toModifySpinner.getSelectedItem().toString());
        String state = stateSpinner.getSelectedItem().toString();
        if (state.equals("NEW")) {
            ticket.setState(Ticket.NEW);
        }
        if (state.equals("IN_PROGRESS")) {
            ticket.setState(Ticket.IN_PROGRESS);
        }
        if (state.equals("DONE")) {
            ticket.setState(Ticket.DONE);
        }
        dao.update(ticket);
        startActivity(intent);
    }
}