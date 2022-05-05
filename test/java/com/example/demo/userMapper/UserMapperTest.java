package com.example.demo.userMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.LoginSpringBootApplicationTests;
import com.example.demo.bean.LoginUser;
import com.example.demo.mapper.UserMapper;

class UserMapperTest extends LoginSpringBootApplicationTests {
    @Autowired
	private UserMapper userMapper;
    //ユーザーＩＤ存在する場合
	@Test
	void test() {
		LoginUser user=userMapper.find("111@elife.com");
		Assertions.assertEquals("111@elife.com", user.getAccountId());
		Assertions.assertEquals("000001", user.getPassword());
	}
	//ユーザーＩＤ存在しない場合
    @Test
    void test1() {
    	LoginUser user=userMapper.find("12314");
    	Assertions.assertEquals(null,user);
    }
}
