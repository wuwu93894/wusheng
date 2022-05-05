package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bean.Gender;
import com.example.demo.mapper.GenderMapper;
@Service
public class GenderServiceImpl implements  GenderService{
	//调用GenderMapper 
    @Autowired
    public GenderMapper genderMapper;
	@Override
	//通过GenderMapper 的listGender方法 获取返回值
	public ArrayList<Gender> listGender() {
		return genderMapper.listGender();
	}

}
