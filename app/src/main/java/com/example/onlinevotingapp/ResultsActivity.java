package com.example.onlinevotingapp;

import static com.example.onlinevotingapp.R.id.tableLayoutResults;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private DatabaseReference candidatesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        tableLayout = findViewById(R.id.tableLayoutResults);
        candidatesRef = FirebaseDatabase.getInstance().getReference().child("election-candidates");

        candidatesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tableLayout.removeAllViews(); // Clear existing rows

                TableRow headerRow = new TableRow(ResultsActivity.this);
                headerRow.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                ));

                TextView headerPartyName = createTextView("Party Name");
                TextView headerVotes = createTextView("Votes");

                headerRow.addView(headerPartyName);
                headerRow.addView(headerVotes);
                tableLayout.addView(headerRow);

                for (DataSnapshot candidateSnapshot : snapshot.getChildren()) {
                    String partyName = candidateSnapshot.child("pName").getValue(String.class);
                    Integer votesData = candidateSnapshot.child("votes").getValue(Integer.class);

                    int votes = (votesData != null) ? votesData.intValue() : 0; // Provide a default value if votesData is null

                    TableRow row = new TableRow(ResultsActivity.this);
                    row.setLayoutParams(new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT
                    ));

                    TextView textViewParty = createTextView(partyName);
                    TextView textViewVotes = createTextView(String.valueOf(votes));

                    row.addView(textViewParty);
                    row.addView(textViewVotes);

                    tableLayout.addView(row);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        textView.setPadding(16, 8, 16, 8);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
