package com.example.issuetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class IssueTrackingActivity extends AppCompatActivity {
    private static final String LOG_TAG = IssueTrackingActivity.class.getName();
    List<Section> sectionList = new ArrayList<>();
    RecyclerView mainRecyclerView;
    private TicketDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_tracking);

        initFrame();

        dao = TicketRoomDatabase.getAppDatabase(this).ticketDAO();

        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(sectionList);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initFrame() {

        String sectionOneName = "New Item";
        List<String> sectionOneItems = new ArrayList<>();

        String sectionTwoName = "In progress";
        List<String> sectionTwoItems = new ArrayList<>();


        String sectionThreeName = "Done";
        List<String> sectionThreeItems = new ArrayList<>();

        Ticket[] tickets = dao.loadAll();
        for (int i = 0; i < tickets.length; i++) {
            switch (tickets[i].getState()) {
                case Ticket.IN_PROGRESS:
                    sectionTwoItems.add(tickets[i].getTicket());
                    break;
                case Ticket.DONE:
                    sectionThreeItems.add(tickets[i].getTicket());
                    break;
                default:
                    sectionOneItems.add(tickets[i].getTicket());
            }
        }


        sectionList.add(new Section(sectionOneName, sectionOneItems));
        sectionList.add(new Section(sectionTwoName, sectionTwoItems));
        sectionList.add(new Section(sectionThreeName, sectionThreeItems));

        Log.d(LOG_TAG, "initData: " + sectionList);
    }

    public void createTask(View view) {
        Intent intent = new Intent(this, AddTicketActivity.class);
        startActivity(intent);
    }

    public void deleteTicket(View view) {

    }

    public void deleteTaskButAction(View view) {
        Intent intent = new Intent(this, DeleteTicketActivity.class);
        startActivity(intent);
    }

    public void modifyTaskAction(View view) {
        Intent intent = new Intent(this, ModifyTicketActivity.class);
        startActivity(intent);
    }
}