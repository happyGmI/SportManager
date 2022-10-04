package com.w.sportmanager.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Student {

    /**
     * 用户学号
     */
    private String id;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户个人昵称
     * 刚刚注册没有给出，使用用户name
     */
    private String nickName;

    /**
     * 用户生日
     */
    private String birthdate;

    /**
     * 用户性别
     */
    private Integer gender;

    /**
     * 用户权限等级
     * 分为老师和学生两种
     */
    private Integer level;

    public Student(String id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = name;
        this.birthdate = "2000-01-01";
        this.avatar = "https://tse2-mm.cn.bing.net/th/id/OIP-C.x09r5tGxTyGiAchyk-KCjQAAAA?pid=ImgDet&rs=1";
        this.gender = 0;
        this.level = 0;
    }
}
