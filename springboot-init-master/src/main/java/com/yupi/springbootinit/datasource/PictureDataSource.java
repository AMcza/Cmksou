package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.service.PictureService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Component
public class PictureDataSource implements DataSource<Picture> {

    @Resource
    private PictureService pictureService;

    @Override
    public Page<Picture> doSearch(String searchText, int pageNum, int pageSize) {
        Page<Picture> picturePage = pictureService.searchPicture(searchText, pageNum, pageSize);
        return picturePage;
    }
}
