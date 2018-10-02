package com.revature.bankApp;

import java.util.Scanner;

public class Menu {
        public static void main(String[] args) {

            Accounts a;
            Scanner sc = new Scanner(System.in);

            System.out.println("\nWelcome to your BankApp!");
            System.out.println("Please select an input option below.\n");
            System.out.println("Login (1)\nApply for an account (2)\nEmployee Login (3)");
            System.out.println("\nExit App (0)");

            int option = sc.nextInt();

            if(option==1)
                login();
            else if(option==2)
                apply();
            else if(option==3)
                employeeLogin();
            else
                System.out.println("Exiting...");

            a = new Accounts(3847, "CodyP");
            a = new Accounts(1024, "JohnD");
            a = new Accounts(1234, "DeShaunW");
            a.viewAccounts();

            

        
    }

    static void login() {
        System.out.println("User Login.");
        AccountHolder.login("John1024");
    }

    static void apply() {
        System.out.println("Applying for account.");
        AccountHolder a = new AccountHolder("John", "Doe",1024);
        a.serialize(a);

    }

    static void employeeLogin() {
        System.out.println("Employee Login.");
    }
}
