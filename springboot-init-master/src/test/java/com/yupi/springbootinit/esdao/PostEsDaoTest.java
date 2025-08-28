package com.yupi.springbootinit.esdao;

import com.yupi.springbootinit.model.dto.post.PostEsDTO;
import com.yupi.springbootinit.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PostEsDaoTest {

    @Resource
    private PostEsDao postEsDao;

    @Resource
    private PostService postService;

    @Test
    void testSelect() {
        System.out.println(postEsDao.count());
        Page<PostEsDTO> postEsDTOPage = postEsDao.findAll(
                PageRequest.of(0, 5, Sort.by("createTime")));
        List<PostEsDTO> postList = postEsDTOPage.getContent();
        System.out.println(postList);
    }

    @Test
    void testAdd() {
        PostEsDTO postEsDTO = new PostEsDTO();
        postEsDTO.setId(1L);
        postEsDTO.setTitle("test");
        postEsDTO.setContent("test内容");
        postEsDTO.setUserId(1L);
        postEsDTO.setCreateTime(new Date());
        postEsDTO.setUpdateTime(new Date());
        postEsDTO.setIsDelete(0);
        postEsDao.save(postEsDTO);
        System.out.println(postEsDTO.getId());
    }

    @Test
    void testFindById() {
        Optional<PostEsDTO> postEsDTO = postEsDao.findById(1L);
        System.out.println(postEsDTO.get());
    }

    @Test
    void testSelectByTitle() {

        List<PostEsDTO> postEsDTOList = postEsDao.findByTitle("并发");
        System.out.println(postEsDTOList);
    }

    @Test
    void testDelete(){
       postEsDao.deleteById(1L);
    }
}
