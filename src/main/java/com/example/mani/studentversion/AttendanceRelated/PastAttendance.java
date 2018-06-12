package com.example.mani.studentversion.AttendanceRelated;

public class PastAttendance {

    private int roll_no;
    private String name;
    private int status;

    PastAttendance(int roll_no, String name, int status) {
        this.roll_no = roll_no;
        this.name = name;
        this.status = status;
    }

    public int getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(int roll_no) {
        this.roll_no = roll_no;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

