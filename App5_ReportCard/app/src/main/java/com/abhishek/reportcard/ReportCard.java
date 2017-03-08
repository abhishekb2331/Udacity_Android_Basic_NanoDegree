package com.abhishek.reportcard;


import java.util.ArrayList;
import java.util.List;

public class ReportCard {

    private static final String UNIVERSITY_NAME="Udacity";
    private static Double TOTAL_SUBJECTS=0.0;

    private String mStudentName;
    private int mRollNumber;
    private List<SubjectMark> mSubjects;

    public ReportCard(String StudentName,int RollNumber)
    {
        mStudentName=StudentName;
        mRollNumber=RollNumber;
        mSubjects=new ArrayList<SubjectMark>();
    }
    public String getStudentName()
    {
        return mStudentName;
    }
    public void setStudentName(String StudentName)
    {
        mStudentName=StudentName;
    }
    public int getRollNumber()
    {
        return mRollNumber;
    }
    public void setRollNumber(int RollNumber)
    {
        mRollNumber=RollNumber;
    }
    public void addSubjectMarks(String Subject,int Marks)
    {
        mSubjects.add(new SubjectMark(Subject,Marks));
    }
    public int getSubjectMarks(String Subject)
    {
        for(SubjectMark i : mSubjects)
        {
            if(i.getSubject().equalsIgnoreCase(Subject))
                return i.getMarks();
        }
        return 0;
    }
    public String getGrade()
    {
        TOTAL_SUBJECTS=0.0;
        TOTAL_SUBJECTS+= mSubjects.size();
        int score=0;
        String mGrade;
        for (SubjectMark i:mSubjects)
        {
            score+=i.getMarks();
        }
        score= (int) Math.floor((Double)(score/TOTAL_SUBJECTS));

        if (score >= 90) {
            mGrade = "A";
        } else if (score < 90.0 && score >= 80.0) {
            mGrade = "B";
        } else if (score < 80.0 && score >= 70.0) {
            mGrade = "C";
        } else if (score < 70.0 && score >= 60.0) {
            mGrade = "D";
        } else if (score < 60.0) {
            mGrade = "Fail";
        } else {
            mGrade = "error";
        }
        return mGrade;
    }

    @Override
    public String toString() {
        StringBuilder mString= new StringBuilder();
        mString.append("ReportCard{" + "studentName='" + mStudentName + '\'' + ", rollNumber=" + mRollNumber);
        for(SubjectMark i: mSubjects)
        {
            mString.append(", "+i.getSubject()+"=" + i.getMarks());
        }
        mString.append(", grade='" + getGrade() + '\'' + '}');
        return mString.toString();
    }


    public String getData() {
        StringBuilder mString= new StringBuilder();
        mString.append( "University: " + ReportCard.UNIVERSITY_NAME + '\n' +"studentName=" + mStudentName + '\n' + "rollNumber=" + mRollNumber+'\n');
        for(SubjectMark i: mSubjects)
        {
            mString.append(""+i.getSubject()+"=" + i.getMarks()+'\n');
        }
        mString.append("grade=" + getGrade() + '\n');
        return mString.toString();
    }
    private class SubjectMark{
        private String mSubject;
        private int mMarks;

        public SubjectMark(String Subject,int Marks)
        {
            mSubject=Subject;
            mMarks=Marks;
        }

        public int getMarks()
        {
            return mMarks;
        }

        public String getSubject()
        {
            return mSubject;
        }
    }
}
