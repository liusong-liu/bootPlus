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
@ApiModel(value="TClass对象", description="")
public class TClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "班号")
    private String number;

    @ApiModelProperty(value = "班级人数")
    private Integer countNum;

    private Date createTime;

    private Date updateTime;


}
