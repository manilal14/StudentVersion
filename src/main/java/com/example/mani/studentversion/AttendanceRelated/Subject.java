package com.example.mani.studentversion.AttendanceRelated;

public class Subject {

    private int subject_id;
    private int class_id;
    private String class_name;
    private String subject_name;
    private String semester;


    public Subject(int subject_id, int class_id, String class_name, String subject_name, String semester) {
        this.subject_id = subject_id;
        this.class_id = class_id;

        this.class_name = class_name;
        this.subject_name = subject_name;
        this.semester = semester;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }


    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClassName() {
        return class_name;
    }

    public String getSubjectName() {
        return subject_name;
    }

    public String getSemester() {
        return semester;
    }
}
