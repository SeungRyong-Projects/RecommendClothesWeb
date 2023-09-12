package com.example.demo.ServiceImpl;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.MemberVO;
import com.example.demo.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
    HttpServletRequest request;

    private MemberMapper memberMapper;

    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public Boolean duplicateId(String id) throws Exception {
        Boolean res = memberMapper.duplicateId(id);
        return res ? true : false;
    }

    //이메일 중복체크
    @Override
    public Boolean duplicateEmail(String email) throws Exception {
        Boolean res = memberMapper.duplicateEmail(email);
        return res ? true : false;
    }

    /*회원정보 추가*/
    @Override
    public void insertMember(MemberVO memberVO) throws Exception {
        memberMapper.insertMember(memberVO);
    }

    /*로그인 처리*/
    @Override
    public Boolean loginProcess(MemberVO memberVO, HttpServletRequest request) throws Exception {
        MemberVO result = memberMapper.loginProcess(memberVO);

        if (result != null) {
            //세션 정보 생성
            HttpSession session = request.getSession();
            session.setAttribute("memberVO", result);
            session.setMaxInactiveInterval(3600);
            return true;
        }
        return false;
    }

    //회원가입 폼 검증 (비번 영문, 숫자 자리수 등등)
    @Override
    public Map<String, String> formValidation(Errors errors) {
        Map<String, String> result = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            result.put(validKeyName, error.getDefaultMessage());
        }
        return result;
    }

    /*
     * 아이디 찾기
     */
    @Override
    public String findUserId(MemberVO memberVO) throws Exception {
        return memberMapper.findUserId(memberVO);
    }
    /*
     * 비밀번호 찾기
     * */
    @Override
    public String findPassword(MemberVO memberVO) throws Exception {
        return memberMapper.findPassword(memberVO);
    }

    /*
     * 비밀번호 변경
     * */
    @Override
    public void updatePassword(MemberVO memberVO) throws Exception {
        memberMapper.updatePassword(memberVO);
    }

    @Override
    public MemberVO selectMemberById(String userId) throws Exception {
        return memberMapper.selectMemberById(userId);
    }

    @Override
    public List<String> selectMember(int id) throws Exception {
//
//        HttpSession session = request.getSession();
//        MemberVO sessionResult = (MemberVO) session.getAttribute("memberVO");
//
//        int height = memberVO.getHeight();
//        int weight = memberVO.getWeight();
//        String instagram = memberVO.getInstagram();
//        String blog = memberVO.getBlog();
//        String location = sessionResult.getLocation();
//        String style = memberVO.getStyle();
//        String name = sessionResult.getName();
//
//        memberVO.setName(name);
//        memberVO.setLocation(location);
//        memberVO.setStyle(style);
//        memberVO.setBlog(blog);
//        memberVO.setInstagram(instagram);
//        memberVO.setWeight(weight);
//        memberVO.setHeight(height);

        List<String> list = memberMapper.selectMember(id);
        return list;
    }
}
