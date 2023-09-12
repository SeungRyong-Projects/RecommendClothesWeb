package com.example.demo.ServiceImpl;

import com.example.demo.mapper.MyPageMapper;
import com.example.demo.model.MemberVO;
import com.example.demo.model.MyPageVO;
import com.example.demo.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyPageServiceImpl implements MyPageService {

    private MyPageMapper myPageMapper;

    public MyPageServiceImpl(MyPageMapper myPageMapper) {
        this.myPageMapper = myPageMapper;
    }

    @Override
    public void updateMyPage(MemberVO memberVO) throws Exception {
        myPageMapper.updateMyPage(memberVO);
    }

    @Override
    public void insertMyPage(MemberVO memberVO) throws Exception {
        myPageMapper.insertMyPage(memberVO);
    }

    @Override
    public MyPageVO selectMyPageVOById(int id) throws Exception {
        return myPageMapper.selectMyPageVOById(id);
    }
}
