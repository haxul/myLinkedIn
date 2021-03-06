package com.javabycode.springmvc.service;

import com.javabycode.springmvc.dao.AccountDao;
import com.javabycode.springmvc.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("accountService")
@Transactional
public class AccountService {

    @Autowired
    private AccountDao dao;

    public Account findById(int id) {
        return dao.findById(id);
    }

    public void saveAccount(Account account) {
        dao.saveAccount(account);
    }

    public Account findByEmail(String email) {
        return dao.findByEmail(email);
    }

    public void updateEmailAccount(Account account, String email) {
        dao.updateEmailAccount(account, email);
    }
    public void updatePasswordAccount(Account account, String password) {
        dao.updatePasswordAccount(account, password);
    }

    public void updateAccount(Account account) {
        dao.updateAccount(account);
    }
    public Boolean checkInputFields(Account account) {
        String email = account.getEmail();
        String name = account.getName();
        String lastname = account.getLastname();
        String phone = account.getPhone();
        String password = account.getPassword();
        List<String> characteristics = new ArrayList<String>();
        characteristics.add(email);
        characteristics.add(name);
        characteristics.add(lastname);
        characteristics.add(phone);
        characteristics.add(password);
        for (String characteristic : characteristics) {
            if (characteristic == null) return false;
        }
        return true;
    }

    public Boolean checkCorrectnessOfEmail(String email) {
        Pattern pattern = Pattern.compile("^.+@.+\\.\\w{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public Boolean checkNumberPhone(String phone) {
        Pattern pattern = Pattern.compile("^\\+7\\d{10}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
