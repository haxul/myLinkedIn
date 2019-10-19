package com.javabycode.springmvc.service;

import com.javabycode.springmvc.dao.AccessTokenDao;
import com.javabycode.springmvc.model.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("accessTokenService")
@Transactional
public class AccessTokenService {

    @Autowired
    private AccessTokenDao dao;

    public final static int TTL = 3600000;
    public void save(AccessToken accessToken) {
        dao.save(accessToken);
    }
}
