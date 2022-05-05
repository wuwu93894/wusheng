package com.example.demo.service;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bean.Nationality;
import com.example.demo.mapper.NationalityMapper;
@Service
public class NationalityServiceImpl implements NationalityService {
	//注入NationalityMapper
    @Autowired
    public NationalityMapper nationalityMapper;
    //调用NationalityMapper中的listNationality方法获取数据库的返回值
	@Override
	public ArrayList<Nationality> listNationality() {
		return nationalityMapper.listNationality();
	}

}
