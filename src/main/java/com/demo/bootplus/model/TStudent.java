package com.demo.bootplus.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lius
 * @since 2022-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TStudent对象", description="")
public class TStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String number;

    private String sex;

    private Date birth;

    private Date createTime;

    private Date updateTime;


}
