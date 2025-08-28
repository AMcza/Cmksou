package com.yupi.springbootinit.datasource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.service.PostService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
public class PostDataSouce implements DataSource<PostVO>{

    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> doSearch(String searchText, int pageNum, int pageSize) {
        PostQueryRequest queryRequest=new PostQueryRequest();
        queryRequest.setSearchText(searchText);
        queryRequest.setCurrent(pageNum);
        queryRequest.setPageSize(pageSize);
        ServletRequestAttributes servletRequestAttributes =(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request= servletRequestAttributes.getRequest();
        Page<Post> postPage = postService.searchFromEs(queryRequest);
        return postService.getPostVOPage(postPage,request);
    }

}
