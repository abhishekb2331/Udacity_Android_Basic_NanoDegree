package com.abhishek.reportcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReportCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_card);
        ReportCard reportCard = new ReportCard("Abhishek",21212121);
        reportCard.addSubjectMarks("English",80);
        reportCard.addSubjectMarks("Maths",80);
        reportCard.addSubjectMarks("Computer Science",80);
        reportCard.addSubjectMarks("Science",80);
        reportCard.addSubjectMarks("History",80);
        TextView textView = (TextView) findViewById(R.id.displayReportCard);
        textView.setText(reportCard.getData());
    }
}
