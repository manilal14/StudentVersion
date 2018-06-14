package com.example.mani.studentversion.AttendanceRelated;

public class Subject {

    private int class_id;
    private String class_name;
    private String semester;


    public Subject(int class_id, String class_name, String semester) {

        this.class_id = class_id;
        this.class_name = class_name;
        this.semester = semester;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
