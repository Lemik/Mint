package com.example.leoniddushin.mint2.Objects;

public class Coin {

    private int id;
    private int FK_collection; // Collection  ID
    private String title;//
    private String year;//year
    private String mint;//where it was produced
    private int count;//how many coins you have
    private String nominal;// 1 cent,25 cents 1 dollar
    private String grade;//
    private String description;//
    private String note;//
    private String imgA;// front side image
    private String imgB;// back

    public Coin(){}

    public Coin(int id, int FK_collection, String title, String year, String mint, int count, String nominal, String grade, String description, String note, String imgA, String imgB) {
        this.id = id;
        this.FK_collection = FK_collection;
        this.title = title;
        this.year = year;
        this.mint = mint;
        this.count = count;
        this.nominal = nominal;
        this.grade = grade;
        this.description = description;
        this.note = note;
        this.imgA = imgA;
        this.imgB = imgB;
    }

    public int getId() {
        return id;
    }

    public int getFK_collection(){return FK_collection;}

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getMint() {
        return mint;
    }

    public String getNominal() {
        return nominal;
    }

    public String getGrade() {
        return grade;
    }

    public String getDescription() {
        return description;
    }

    public String getNote() {
        return note;
    }

    public int getCount() {
        return count;
    }

    public String getImgA() {
        return imgA;
    }

    public String getImgB() {
        return imgB;
    }


//***************************************************************


    public void setId(int id) {
        this.id = id;
    }

    public void setFK_collection(int FK_collection) {
        this.FK_collection = FK_collection;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMint(String mint) {
        this.mint = mint;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setImgA(String imgA) {
        this.imgA = imgA;
    }

    public void setImgB(String imgB) {
        this.imgB = imgB;
    }
}