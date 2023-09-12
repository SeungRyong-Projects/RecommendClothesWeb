package com.example.demo.mapper;

import com.example.demo.model.BoardVO;
import com.example.demo.model.SearchHelper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<BoardVO> selectBoardVO(SearchHelper searchHelper) throws Exception;
    int countBoardVO(SearchHelper searchHelper) throws Exception;
    BoardVO selectBoardVOById(int id) throws Exception;
    void updateBoardVO(BoardVO boardVO) throws Exception;
    void updateCount(int id) throws Exception;
    void deleteById(int id) throws Exception;
    void insertBoardVO(BoardVO boardVO) throws Exception;
}
