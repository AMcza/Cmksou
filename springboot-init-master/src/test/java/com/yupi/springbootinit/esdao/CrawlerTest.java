package com.yupi.springbootinit.esdao;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CrawlerTest {
    @Resource
    private PostService postService;
    @Test
    void testFetchPassage(){
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
        Assertions.assertTrue(b);
    }


    @Test
    void testFetchPicture() throws IOException {
        int current = 1;
        String url = "https://cn.bing.com/images/search?q=小黑子&first="+current;
        Document doc = Jsoup.connect(url).get();
        System.out.println(doc);
        Elements elements=doc.select(".iuscp.isv");


        List<Picture> pictures=new ArrayList<>();
        for (Element element : elements) {
            //获取图片url
            String img=element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(img, Map.class);
            String imgUrl=(String) map.get("murl");
            System.out.println(imgUrl);
            //获取标题
            String title=element.select(".inflnk").get(0).attr("aria-label");
            System.out.println(title);

            Picture picture=new Picture();
            picture.setUrl(imgUrl);
            picture.setTitle(title);

            pictures.add(picture);
        }
    }

}
