package com.example.issuetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddTicketActivity extends AppCompatActivity {

    EditText ticketDescription;
    private TicketDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);
        ticketDescription = findViewById(R.id.createTaskText);
        dao = TicketRoomDatabase.getAppDatabase(this).ticketDAO();
    }

    public void addNewTicket(View view) {
        Intent intent = new Intent(this, IssueTrackingActivity.class);
        Ticket ticket = new Ticket();
        ticket.setTicket(ticketDescription.getText().toString());
        ticket.setState(Ticket.NEW);

        dao.insert(ticket);

        startActivity(intent);
    }

}