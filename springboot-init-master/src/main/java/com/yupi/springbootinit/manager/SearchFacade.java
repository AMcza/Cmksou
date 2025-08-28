package com.yupi.springbootinit.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.datasource.DataSource;
import com.yupi.springbootinit.datasource.PictureDataSource;
import com.yupi.springbootinit.datasource.PostDataSouce;
import com.yupi.springbootinit.datasource.UserDataSource;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.dto.search.SearchRequest;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.enums.SearchTypeEnum;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.model.vo.SearchVO;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.registry.DataSourceRegistry;
import com.yupi.springbootinit.service.PictureService;
import com.yupi.springbootinit.service.PostService;
import com.yupi.springbootinit.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
@Component
@Slf4j
public class SearchFacade {
    @Resource
    private PostDataSouce postDataSouce;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private DataSourceRegistry dataSourceRegistry;


    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String searchText=searchRequest.getSearchText();
        String type=searchRequest.getType();
        ThrowUtils.throwIf(StringUtils.isBlank(type),ErrorCode.PARAMS_ERROR,"type不能为空");
        SearchTypeEnum searchTypeEnum=SearchTypeEnum.getEnumByValue(type);
        int current=searchRequest.getCurrent();
        int pageSize=searchRequest.getPageSize();
        if(searchTypeEnum==null){
            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = pictureDataSource.doSearch(searchText, current, pageSize);
                return picturePage;
            });

            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                Page<UserVO> userPage = userDataSource.doSearch(searchText,current,pageSize);
                return userPage;
            });

            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                Page<PostVO> postVOPage = postDataSouce.doSearch(searchText,current,pageSize);
                return postVOPage;
            });
            CompletableFuture.allOf(pictureTask, userTask, postTask).join();
            SearchVO searchVO = new SearchVO();
            try {
                Page<Picture> picturePage = pictureTask.get();
                Page<UserVO> userPage = userTask.get();
                Page<PostVO> postVOPage = postTask.get();
                searchVO.setPictureList(picturePage.getRecords());
                searchVO.setPostList(postVOPage.getRecords());
                searchVO.setUserList(userPage.getRecords());
            } catch (Exception e) {
                log.error("查询错误",e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "未获取数据");
            }
            return searchVO;
        }else{
            SearchVO searchVO=new SearchVO();
//            Map<String, DataSource> typeDataSourceMap=new HashMap() {
//                {
//                    put(SearchTypeEnum.POST.getValue(), postDataSouce);
//                    put(SearchTypeEnum.USER.getValue(), userDataSource);
//                    put(SearchTypeEnum.PICTURE.getValue(), pictureDataSource);
//                }
//            };
            DataSource dataSource = dataSourceRegistry.getDataSource(type);
            Page page = dataSource.doSearch(searchText, current, pageSize);
            searchVO.setDataList(page.getRecords());
            return searchVO;

//            switch (searchTypeEnum){
//                case POST:
//                    PostQueryRequest postQueryRequest = new PostQueryRequest();
//                    postQueryRequest.setSearchText(searchText);
//                    Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
//                    searchVO.setPostList(postVOPage.getRecords());
//                    break;
//                case USER:
//                    UserQueryRequest userQueryRequest = new UserQueryRequest();
//                    userQueryRequest.setUserName(searchText);
//                    Page<UserVO> userPage = userService.listUserVOByPage(userQueryRequest);
//                    searchVO.setUserList(userPage.getRecords());
//                    break;
//                case PICTURE:
//                    Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
//                    searchVO.setPictureList(picturePage.getRecords());
//                    break;
//                default:
//            }
//            return searchVO;
        }
    }
}
