package com.example.mani.studentversion.AttendanceRelated;

public class AttendanceReport {

    private  String subName;
    private int totalHrs;
    private int presentHrs;
    private int absentHrs;

    public AttendanceReport(String subName, int totalHrs, int present_hrs) {
        this.subName = subName;
        this.totalHrs = totalHrs;
        this.presentHrs = present_hrs;
        this.absentHrs = totalHrs - present_hrs;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getTotalHrs() {
        return totalHrs;
    }

    public void setTotalHrs(int totalHrs) {
        this.totalHrs = totalHrs;
    }

    public int getPresentHrs() {
        return presentHrs;
    }

    public void setPresentHrs(int presentHrs) {
        this.presentHrs = presentHrs;
    }

    public int getAbsentHrs() {
        return absentHrs;
    }

    public void setAbsentHrs(int absentHrs) {
        this.absentHrs = absentHrs;
    }
}
