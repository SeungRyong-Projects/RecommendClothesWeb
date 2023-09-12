package com.example.demo.file;

import com.example.demo.mapper.FileMapMapper;
import com.example.demo.model.FileMapVO;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FileMapService {

    private FileMapMapper fileMapMapper;

    public FileMapService(FileMapMapper fileMapMapper) {
        this.fileMapMapper = fileMapMapper;
    }

    public void insertFileMap(FileMapVO fileMapVO) {
        fileMapMapper.insertFileMap(fileMapVO);
    }
}
