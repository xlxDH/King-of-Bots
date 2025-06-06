package com.kob.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("record")
public class Record {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer aId;
    private Integer aSx;
    private Integer aSy;
    private String aSteps;
    private Integer bId;
    private Integer bSx;
    private Integer bSy;
    private String bSteps;
    private String map;
    private String loser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createtime;
}
