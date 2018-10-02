package com.revature.bankApp;

import java.util.HashMap;
import java.util.Map;
//import java.util.Map.Entry;

public class Accounts {

    static Map<Integer, String> newAccounts = new HashMap<>();

    public Accounts(Integer accountNum, String userName) {
        newAccounts.put(accountNum, userName);

    }

    public void viewAccounts() {
        System.out.println(newAccounts);
    }

}