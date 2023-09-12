package com.example.demo.service;

import com.example.demo.model.BoardVO;
import com.example.demo.model.SearchHelper;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.Map;

public interface BoardService {
    HashMap<String, Object> selectBoardVO(SearchHelper searchHelper) throws Exception;
    BoardVO selectBoardVOById(int id) throws Exception;
    void updateBoardVO(BoardVO boardVO) throws Exception;
    void deleteById(int id) throws Exception;
    void insertBoardVO(BoardVO boardVO) throws Exception;
    Map<String, String> formValidation(Errors errors);
}
