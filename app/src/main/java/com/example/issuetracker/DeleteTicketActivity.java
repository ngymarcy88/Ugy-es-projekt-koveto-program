package com.example.issuetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DeleteTicketActivity extends AppCompatActivity {

    private Spinner toDeleteSpinner;
    private TicketDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_ticket);

        dao = TicketRoomDatabase.getAppDatabase(this).ticketDAO();

        Ticket[] tickets = dao.loadAll();
        toDeleteSpinner = findViewById(R.id.deleteTaskSpinner);
        String[] ticketNames = new String[tickets.length];
        for (int i = 0; i < tickets.length; i++) {
            ticketNames[i] = tickets[i].getTicket();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, ticketNames);
        toDeleteSpinner.setAdapter(arrayAdapter);
    }

    public void deleteTicket(View view) {
        Intent intent = new Intent(this, IssueTrackingActivity.class);
        Ticket ticket = dao.findByTicketName(toDeleteSpinner.getSelectedItem().toString());
        dao.delete(ticket);
        startActivity(intent);
    }

}