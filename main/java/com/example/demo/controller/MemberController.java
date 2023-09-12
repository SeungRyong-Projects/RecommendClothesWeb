package com.example.demo.controller;

import com.example.demo.model.MemberVO;
import com.example.demo.model.Message;
import com.example.demo.service.MemberService;
import com.example.demo.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/join")
    public String MemberJoin() {
        return "/member/join";
    }

    @PostMapping("/join")
    public String memberJoin(
            @Valid MemberVO memberVO,
            Model model,
            Errors errors
    ) throws Exception {
        if (errors.hasErrors()) {
            Map<String, String> validate = memberService.formValidation(errors);

            for (String key : validate.keySet()) {
                model.addAttribute(key, validate.get(key));
            }
        }

        boolean idCheck = memberService.duplicateId(memberVO.getUserId());
        boolean emailCheck = memberService.duplicateEmail(memberVO.getEmail());

        if (!idCheck && !emailCheck)
            memberService.insertMember(memberVO);

        return "redirect:/member/login";
    }

    @PostMapping("/loginProcess")
    public String loginProcess(
//            @RequestParam(value = "id", defaultValue = "") int id,
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "password", defaultValue = "") String password,
            HttpServletRequest request
    ) throws Exception {
        if (!userId.equals("") && !password.equals("")) {
            MemberVO memberVO = new MemberVO();
//            memberVO.setId(id);
            memberVO.setUserId(userId);
            memberVO.setPassword(password);

            Boolean result = memberService.loginProcess(memberVO, request);

            log.info("로그인 -{}", result);
            if (result == false) {
                return "redirect:/member/login";
            }
            return "redirect:/main/mainPage";
        }
        return "redirect:/member/login";
    }
    @GetMapping("/login")
    public String memberLogin() {
        return "/member/login";
    }
    /*
     * 아이디 찾기 페이지
     * */
    @GetMapping("/find_id")
    public String findId() {
        return "member/find_id";
    }

    @PostMapping("/find_id")
    public ModelAndView findIdPost(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "email", defaultValue = "") String email,
            ModelAndView mav
    ) throws Exception {

        if (!name.equals("") && !email.equals("")) {
            MemberVO memberVO = new MemberVO();
            memberVO.setName(name);
            memberVO.setEmail(email);

            String id = memberService.findUserId(memberVO);

            log.info("찾은 id -{}", id);

            if (id == null) {
                // 찾는 id가 없습니다.
                mav.addObject("data", new Message("찾으시는 계정이 없습니다.", "/member/find_id"));
                mav.setViewName("message/message");
                return mav;
            } else {
                // 찾는 id가 있습니다.
                int idLength = id.length();
                // 4
                id = id.substring(0, idLength - 2);
                // ab
                id += "**";
                log.info("id 마스킹 -{}", id);
                mav.addObject("data", new Message(name + "님이 찾으시는 ID는 " + id + "입니다.", "/member/login"));
                mav.setViewName("message/message");
                return mav;
            }

        }

        mav.addObject("data", new Message("이름과 이메일을 확인하세요.", "/member/login"));
        mav.setViewName("message/message");
        return mav;
    }

    /*
     * 비밀번호 찾기 페이지
     * */
    @GetMapping("/find_pw")
    public String findPw() {
        return "member/find_pw";
    }

    @PostMapping("/find_pw")
    public ModelAndView findPwPost(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "userId", defaultValue = "") String userId,
            ModelAndView mav
    ) throws Exception {
        if (!name.equals("") && !email.equals("") && !userId.equals("")) {
            MemberVO memberVO = new MemberVO();
            memberVO.setName(name);
            memberVO.setEmail(email);
            memberVO.setUserId(userId);

            String id = memberService.findPassword(memberVO);

            if(id == null) {
                // 계정 없음
                mav.addObject("data", new Message("찾으시는 계정이 없습니다.", "/member/find_pw"));
                mav.setViewName("message/message");
                return mav;
            } else {
                String pw = Util.generateRandomString(10);
                log.info("pw -{}", pw);
                memberVO.setPassword(pw);
                memberService.updatePassword(memberVO);
                mav.addObject("data", new Message("변경된 비밀번호는 " + pw + "입니다", "/member/login"));
                mav.setViewName("message/message");
                return mav;
            }

        }

        mav.addObject("data", new Message("입력 정보를 확인하세요.", "/member/find_pw"));
        mav.setViewName("message/message");
        return mav;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        return "redirect:/member/login";
    }
}
