package com.yupi.springbootinit.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 图片实体
 */
@Data
public class Picture implements Serializable {

    String title;

    String url;

    private final static long serialVersionUID=1L;
}
