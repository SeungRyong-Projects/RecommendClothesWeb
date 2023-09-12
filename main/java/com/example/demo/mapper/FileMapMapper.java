package com.example.demo.mapper;

import com.example.demo.model.FileMapVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapMapper {

    void insertFileMap(FileMapVO fileMapVO);
}
