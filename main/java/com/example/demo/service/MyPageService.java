package com.example.demo.service;

import com.example.demo.model.MemberVO;
import com.example.demo.model.MyPageVO;

public interface MyPageService {

    void updateMyPage(MemberVO memberVO) throws Exception;
    void insertMyPage(MemberVO memberVO) throws  Exception;
    MyPageVO selectMyPageVOById(int id) throws Exception;


}
