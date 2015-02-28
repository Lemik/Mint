package com.example.leoniddushin.mint2.Objects;

public class Coin {

    private int id;
    private String title;//
    private String year;//year
    private String mint;//where it was produced
    private int count;//how many coins you have
    private String nominal;// 1 cent,25 cents 1 dollar
    private String grade;//
    private String description;//
    private String note;//
    private String imgA;//
    private String imgB;

    public int getId() {
        return id;
    }

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