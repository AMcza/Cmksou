package com.yupi.springbootinit.registry;

import com.yupi.springbootinit.datasource.DataSource;
import com.yupi.springbootinit.datasource.PictureDataSource;
import com.yupi.springbootinit.datasource.PostDataSouce;
import com.yupi.springbootinit.datasource.UserDataSource;
import com.yupi.springbootinit.model.enums.SearchTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceRegistry {
    //私有化构造器方法
    private DataSourceRegistry(){
    }

    private static class Holder{
        private static final DataSourceRegistry INSTANCE = new DataSourceRegistry();
    }
    public static DataSourceRegistry getInstance(){
        return Holder.INSTANCE;
    }
    @Resource
    private PostDataSouce postDataSouce;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    private final Map<String ,DataSource> dataSourceMap=new HashMap<>();

    @PostConstruct
    public void init(){
        dataSourceMap.put(SearchTypeEnum.POST.getValue(),postDataSouce);
        dataSourceMap.put(SearchTypeEnum.USER.getValue(),userDataSource);
        dataSourceMap.put(SearchTypeEnum.PICTURE.getValue(),pictureDataSource);
    }


    public DataSource getDataSource(String type) {
        return dataSourceMap.get(type);
    }
}
