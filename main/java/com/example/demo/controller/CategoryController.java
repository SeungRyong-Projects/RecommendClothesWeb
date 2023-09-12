package com.example.demo.controller;

import com.example.demo.model.MemberVO;
import com.example.demo.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private MemberService memberService;

    public CategoryController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping("/street")
    public String categoryStreet(
            Model model,
            HttpServletRequest request
    ) throws Exception {
        HttpSession session = request.getSession();
        MemberVO sessionResult = (MemberVO) session.getAttribute("memberVO");

        List<String> result = memberService.selectMember(sessionResult.getId());
        model.addAttribute("result", result);
        return "/myPage/myPage";
    }

    @RequestMapping("/cutout")
    public String categoryCutout(
            Model model,
            HttpServletRequest request
    ) throws Exception {
        HttpSession session = request.getSession();
        MemberVO sessionResult = (MemberVO) session.getAttribute("memberVO");

        List<String> result = memberService.selectMember(sessionResult.getId());
        model.addAttribute("result", result);
        return "/myPage/myPage";
    }

    @RequestMapping("/gorpCore")
    public String categoryGorpCore(
            Model model,
            HttpServletRequest request
    ) throws Exception {
        HttpSession session = request.getSession();
        MemberVO sessionResult = (MemberVO) session.getAttribute("memberVO");

        List<String> result = memberService.selectMember(sessionResult.getId());
        model.addAttribute("result", result);
        return "/myPage/myPage";
    }

    @RequestMapping("/mixMatch")
    public String categoryMixMatch(
            Model model,
            HttpServletRequest request
    ) throws Exception {
        HttpSession session = request.getSession();
        MemberVO sessionResult = (MemberVO) session.getAttribute("memberVO");

        List<String> result = memberService.selectMember(sessionResult.getId());
        model.addAttribute("result", result);
        return "/myPage/myPage";
    }

    @RequestMapping("/feminine")
    public String categoryFeminine(
            Model model,
            HttpServletRequest request
    ) throws Exception {
        HttpSession session = request.getSession();
        MemberVO sessionResult = (MemberVO) session.getAttribute("memberVO");

        List<String> result = memberService.selectMember(sessionResult.getId());
        model.addAttribute("result", result);
        return "/myPage/myPage";
    }

    @RequestMapping("/school")
    public String categorySchool(
            Model model,
            HttpServletRequest request
    ) throws Exception {
        HttpSession session = request.getSession();
        MemberVO sessionResult = (MemberVO) session.getAttribute("memberVO");

        List<String> result = memberService.selectMember(sessionResult.getId());
        model.addAttribute("result", result);
        return "/myPage/myPage";
    }

    @RequestMapping("/work")
    public String categoryWork(
            Model model,
            HttpServletRequest request
    ) throws Exception {
        HttpSession session = request.getSession();
        MemberVO sessionResult = (MemberVO) session.getAttribute("memberVO");

        List<String> result = memberService.selectMember(sessionResult.getId());
        model.addAttribute("result", result);
        return "/myPage/myPage";
    }

    @RequestMapping("/y2k")
    public String categoryY2k(
            Model model,
            HttpServletRequest request
    ) throws Exception {
        HttpSession session = request.getSession();
        MemberVO sessionResult = (MemberVO) session.getAttribute("memberVO");

        List<String> result = memberService.selectMember(sessionResult.getId());
        model.addAttribute("result", result);
        return "/myPage/myPage";
    }
}

