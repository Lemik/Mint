package com.example.leoniddushin.mint2.File;

import com.example.leoniddushin.mint2.Objects.Coin;
import com.example.leoniddushin.mint2.Objects.Collection;
import com.example.leoniddushin.mint2.Objects.Country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by leoniddushin on 14-12-22.
 */
public class CSVFile {
    InputStream inputStream;

    public CSVFile(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ArrayList<Coin> getCoinsFromFile() {
        ArrayList<Coin> coinList = new ArrayList<Coin>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split("\\|");
                Coin cl = new Coin();
                cl.setTitle(row[0].trim());
                cl.setYear(row[1].trim());
                cl.setMint(row[2].trim());
                cl.setCount(0);//it's new collection there is no count
                cl.setNominal(row[3].trim());
                cl.setGrade("");//it's new collection there is no Grade information available
                cl.setDescription(row[4].trim());
                cl.setNote("");//it's new collection there is no Notes about this coin
                cl.setImgA(row[5].trim());
                cl.setImgB(row[6].trim());
                coinList.add(cl);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
        return coinList;
    }

    public ArrayList<Collection> getCollectionsFromFile() {
        ArrayList<Collection> collectionList = new ArrayList<Collection>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split("\\|");
                Collection cl = new Collection();
                cl.setId(Integer.parseInt(row[0].trim()));
                cl.setName(row[1].trim());
                cl.setCount(Integer.parseInt(row[2].trim()));
                cl.setCountry(row[3].trim());
                cl.setBelongings(Integer.parseInt(row[4].trim()));
                cl.setLock(false);
                cl.setImg(row[5].trim());
                collectionList.add(cl);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
        return collectionList;
    }

    public ArrayList<Country> getCountryFromFile() {
        ArrayList<Country> clolectionList = new ArrayList<Country>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split("\\|");
                Country cl = new Country();

                cl.setId(Integer.parseInt(row[0].trim()));
                cl.setName(row[1].trim());
                cl.setCount(Integer.parseInt(row[2].trim()));
                cl.setImageName(row[3].trim());
                clolectionList.add(cl);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
        return clolectionList;
    }
}