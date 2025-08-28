package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.picture.PictureQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PictureService;
import com.yupi.springbootinit.service.PostService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片搜索接口
 */
@RestController
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;


    private final static Gson gson = new Gson();

    @PostMapping("/picture/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest, HttpServletRequest request){
        long current=pictureQueryRequest.getCurrent();
        long size=pictureQueryRequest.getPageSize();
        //限制爬虫
        ThrowUtils.throwIf(size>20, ErrorCode.PARAMS_ERROR);
        String searchText=pictureQueryRequest.getSearchText();
        Page<Picture> picturePage=pictureService.searchPicture(searchText,current,size);
        return ResultUtils.success(picturePage);
    }
}
