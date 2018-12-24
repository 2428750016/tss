package com.yuhang.dao;

import com.yuhang.entiy.Poetry;

import java.util.List;

/**
 * 唐诗的dao
 */
public interface PoetryDao {

    public List<Poetry> findAll();  //查所有

}
