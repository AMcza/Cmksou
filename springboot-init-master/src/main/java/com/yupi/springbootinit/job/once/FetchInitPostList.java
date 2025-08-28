package com.yupi.springbootinit.job.once;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.model.dto.post.PostEsDTO;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Component
@Slf4j
public class FetchInitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Resource
    private PostEsDTO postEsDTO;

    @Override
    public void run(String... args) throws Exception {
        //1.获取数据
        String json = "{\"current\":1,\"pageSize\":8,\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"category\":\"文章\",\"reviewStatus\":1}";
        String url="https://api.codefather.cn/api/post/list/page/vo";
        String result= HttpRequest.post(url)
                .body(json)
                .execute().body();
        System.out.println(result);
        //2.json转对象
        Map<String,Object> map= JSONUtil.toBean(result,Map.class);
        JSONObject data=(JSONObject) map.get("data");
        JSONArray records=(JSONArray) data.get("records");
        System.out.println(records);

        List<Post> postList=new ArrayList<>();
        for (Object record : records) {
            JSONObject tempRecord=(JSONObject) record;
            Post post=new Post();
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            JSONArray tags=(JSONArray) tempRecord.get("tags");
            List<String> tagList=tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));
            post.setUserId(1L);

            postList.add(post);
        }
        //3.保存数据
        boolean b = postService.saveBatch(postList);
        if(b){
            log.info("初始化帖子数据数:{}",postList.size());
        }
    }

}
