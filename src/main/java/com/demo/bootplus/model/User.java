package com.demo.bootplus.model;

import com.baomidou.mybatisplus.annotation.*;
import com.sun.javafx.beans.IDProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor //有参构造器
@NoArgsConstructor  //无参构造器
public class User {
    //常见的数据库中主键自动设置方法有（uuid、自增id、雪花算法、redis生成、zookeeper生成）
//    @TableId(type = IdType.ASSIGN_ID, value = "id")
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    @Version //乐观锁注解
    private int version;

    //字段添加填充内容
    @TableField(fill = FieldFill.INSERT ,value = "create_time")
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE ,value = "update_time")
    private LocalDateTime updateTime;
    @TableLogic//逻辑删除
    private Integer deleted;


}

