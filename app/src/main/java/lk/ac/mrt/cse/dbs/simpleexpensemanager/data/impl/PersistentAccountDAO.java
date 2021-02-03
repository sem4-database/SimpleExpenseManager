package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.DBHelper;

public class PersistentAccountDAO implements AccountDAO {
    private DBHelper dbHelper;

    public  PersistentAccountDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    @Override
    public List<String> getAccountNumbersList() {
        ArrayList<String> accountNumbers = new ArrayList<>();
        ArrayList<Account> accounts = dbHelper.getAllAccounts();
        if(accounts.size()==0){
            return accountNumbers;
        }else {
            for(Account a:accounts){
                accountNumbers.add(a.getAccountNo());
            }
        }
        return accountNumbers;
    }

    @Override
    public List<Account> getAccountsList() {
        return dbHelper.getAllAccounts();
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        return null;
    }

    @Override
    public void addAccount(Account account) {
        dbHelper.AddAccount(account);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        dbHelper.deleteAccount(accountNo);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        if(accountNo ==null){
            throw new InvalidAccountException("Invalid Account Number");

        }
        Account account = dbHelper.getAccount(accountNo);
        double balance = account.getBalance();
        if(expenseType == ExpenseType.INCOME){
            account.setBalance(balance+amount);
        }else if (expenseType == ExpenseType.EXPENSE){
            account.setBalance(balance-amount);

        }
        if(account.getBalance()<0 ){
            throw new InvalidAccountException("Insufficient credit");
        }

        else{
            dbHelper.UpdateAccount(account);
        }
    }
}