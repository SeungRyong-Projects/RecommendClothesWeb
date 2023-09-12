package com.example.demo.mapper;

import com.example.demo.model.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {
    Boolean duplicateId(String id) throws Exception;

    Boolean duplicateEmail(String email) throws Exception;

    void insertMember(MemberVO memberVO) throws Exception;

    MemberVO loginProcess(MemberVO memberVO) throws Exception;

    String findUserId(MemberVO memberVO) throws Exception;

    String findPassword(MemberVO memberVO) throws Exception;

    void updatePassword(MemberVO memberVO) throws Exception;
    MemberVO selectMemberById(String userId) throws Exception;
    List<String> selectMember(int id) throws Exception;


}
