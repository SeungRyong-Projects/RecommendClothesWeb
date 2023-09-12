package com.example.demo.mapper;

import com.example.demo.model.MemberVO;
import com.example.demo.model.MyPageVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageMapper {

    void updateMyPage(MemberVO memberVO) throws Exception;
    void insertMyPage(MemberVO memberVO) throws  Exception;
    MyPageVO selectMyPageVOById(int id) throws Exception;

}
