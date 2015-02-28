package com.example.leoniddushin.mint2.Objects;

import java.util.ArrayList;

/**
 * Created by leoniddushin on 14-12-20.
 */
public class Collection {

    private int id;
    private String name;
    private int count;
    private String country;
    private int belongings;
    private String img;

    public Collection(){    }
    public Collection(int id,String name, int count,String country,int belongings, String img) {
        super();
        this.id = id;
        this.name = name;
        this.count = count;
        this.country = country;
        this.belongings = belongings;
        this.img= img;
    }

    @Override
    public String toString() {
        return "Collection [id=" + id + ", name=" + name + ", count=" + count + "]";
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getCount() {
        return count;
    }
    public String getCountry() {
        return country;
    }
    public int getBelongings() {
        return belongings;
    }
    public String getImg() {
        return img;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setBelongings(int belongings) {
        this.belongings = belongings;
    }
    public void setImg(String img) {
        this.img = img;
    }

//#########################################
    public static ArrayList<Collection> collectionList = new ArrayList<Collection>();
    public static void setCollections(ArrayList<Collection> collectionList){
        Collection.collectionList = collectionList;
    }

//###############################################################
    public static ArrayList<Coin> coinList = new ArrayList<Coin>();
    public static void loadCollectionFromFile(ArrayList<Coin> coinListNew){
        Collection.coinList = coinListNew;
    }
}
