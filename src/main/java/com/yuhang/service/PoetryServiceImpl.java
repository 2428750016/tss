package com.yuhang.service;

import com.yuhang.dao.PoetryDao;
import com.yuhang.entiy.Poetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PoetryServiceImpl implements PoetryService {
    @Autowired
    private PoetryDao poetryDao;


    //查所有
    @Override
    public List<Poetry> queryAll() {
        return poetryDao.findAll();
    }
}
