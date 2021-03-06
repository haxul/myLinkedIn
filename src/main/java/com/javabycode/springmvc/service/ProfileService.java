package com.javabycode.springmvc.service;

import com.javabycode.springmvc.dao.ProfileDao;
import com.javabycode.springmvc.model.Account;
import com.javabycode.springmvc.model.Profile;
import com.javabycode.springmvc.model.Skills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("profileService")
@Transactional
public class ProfileService {

    @Autowired
    private ProfileDao dao;

    public void saveOrUpdateProfile(Profile profile) {
        dao.saveOrUpdateProfile(profile);
    }

    public void updateProfile(Profile profile) {
        dao.updateProfile(profile);
    }

    public Profile getProfileByAccountId(Account account) {
        Profile profile = dao.findProfileByAccountId(account);
        return profile;
    }
}
