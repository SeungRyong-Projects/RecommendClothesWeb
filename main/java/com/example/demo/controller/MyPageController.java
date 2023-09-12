package com.example.demo.controller;

import com.example.demo.model.BoardVO;
import com.example.demo.model.MemberVO;
import com.example.demo.model.Message;
import com.example.demo.model.MyPageVO;
import com.example.demo.service.MemberService;
import com.example.demo.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/myPage")
public class MyPageController {
    private MemberService memberService;
    private MyPageService myPageService;

    public MyPageController(MemberService memberService, MyPageService myPageService) {
        this.memberService = memberService;
        this.myPageService = myPageService;
    }

    @RequestMapping("/myPage")
    public String myPageMyPage(
            @ModelAttribute MemberVO memberVO,
            HttpServletRequest request,
            Model model
    ) throws Exception {

        HttpSession session = request.getSession();
        MemberVO sessionResult = (MemberVO) session.getAttribute("memberVO");

        List<String> result = memberService.selectMember(sessionResult.getId());
//        memberVO.setUserId(memberVO.getUserId());
//        memberVO.setId(memberVO.getId());
//        memberVO.setBlog(memberVO.getBlog());
//        memberVO.setInstagram(memberVO.getInstagram());
//        memberVO.setLocation(memberVO.getLocation());
//        memberVO.setStyle(memberVO.getStyle());

        model.addAttribute("result", result);
        log.info("result -{}", result);

        return "/myPage/myPage";
    }

    @RequestMapping("/fix")
    public String myPageFix(
            @RequestParam(value = "userId", defaultValue = "") String userId,
            Model model
    ) {
        return "/myPage/fix";
    }

    @RequestMapping("/save")
    public String modifyMyPage(
            @ModelAttribute MemberVO memberVO,
            HttpServletRequest request,
            Model model
    ) throws Exception {

        HttpSession session = request.getSession();
        MemberVO sessionResult = (MemberVO) session.getAttribute("memberVO");
        log.info("MyPage -{}", memberVO);

        if(sessionResult != null) {
            String userId = sessionResult.getUserId();
            memberVO.setUserId(userId);

            myPageService.updateMyPage(memberVO);
        } else {
            model.addAttribute("data", new Message("로그인 후 이용하세요.", "/member/login"));
            return "message/message";
        }
        return "redirect:/myPage/myPage";
    }
}