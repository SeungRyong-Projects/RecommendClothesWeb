package com.example.demo.service;

import com.example.demo.model.MemberVO;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface MemberService {
    Boolean duplicateId(String id) throws Exception;

    Boolean duplicateEmail(String email) throws Exception;

    void insertMember(MemberVO memberVO) throws Exception;
    Boolean loginProcess(MemberVO memberVO, HttpServletRequest request) throws Exception;

    Map<String, String> formValidation(Errors errors);
    String findUserId(MemberVO memberVO) throws Exception;
    String findPassword(MemberVO memberVO) throws Exception;
    void updatePassword(MemberVO memberVO) throws Exception;
    MemberVO selectMemberById(String userId) throws Exception;

    List<String> selectMember(int id) throws Exception;
}
