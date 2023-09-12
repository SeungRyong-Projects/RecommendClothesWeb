package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberVO {
    private int id;
    private String userId;
    @NotBlank(message = "이메일을 입력하세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "이메일 형식이 맞지 않습니다.")
    private String email;
    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 영문, 숫자, 특수기호가 최소 1개이상 포함되어야합니다.")
    private String password;
    @NotBlank(message = "이름을 입력하세요.")
    @Size(min = 2, max = 10, message = "이름은 최소 2자 이상 10자 이내로 입력하세요.")
    private String name;
    private String phone;
    private int height;
    private int weight;
    private String instagram;
    private String blog;
    private String location;
    private String style;
    private int level;
    private String status;
    private int cert;
    private LocalDateTime regDate;
    private LocalDateTime dropDate;

}
