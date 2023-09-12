package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FileMapVO {

    private int id;
    //board 테이블 id
    private int boardId;
    //upload_file 테이블 id
    private int fileId;
}
