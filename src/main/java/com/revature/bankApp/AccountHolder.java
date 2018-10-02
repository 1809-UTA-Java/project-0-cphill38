package com.revature.bankApp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class AccountHolder implements Serializable {

    private static final long serialVersionUID = 1L;
    private String fname, lname;
    private int accountNum;

    public AccountHolder(String fname, String lname, int accountNum) {
        this.fname = fname;
        this.lname = lname;
        this.accountNum = accountNum;

        //System.out.println(this.fname);
    }

    public void serialize(Object obj) {
        String filename = this.fname + this.accountNum;
        try(ObjectOutputStream oos = 
            new ObjectOutputStream(
                new FileOutputStream(filename))) {
                    oos.writeObject(obj);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
    }

    public static void login(String filename) {
        try(ObjectInputStream ois =
            new ObjectInputStream(
                new FileInputStream(filename))) {
                    Object obj = ois.readObject();
                    System.out.println(obj);
                }catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }catch (IOException ex) {
                    ex.printStackTrace();
                }catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
    }


}