package test;

import org.apache.ibatis.ognl.MemberAccess;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;
import zzk.entity.Blog;
import zzk.entity.Common;

import java.util.Collections;
import java.util.HashMap;

public class MetaObjectTest {

    @Test
    public void testMeta() {
        Configuration configuration = new Configuration();
        Blog blog = new Blog();
        MetaObject metaObject = configuration.newMetaObject(blog);
        System.out.println("------------------------------------------------");
        // 操作属性
        metaObject.setValue("title", "我的博客");
        System.out.println(blog.getTitle());
        // 操作子属性/创建对象
        System.out.println("------------------------------------------------");
        metaObject.setValue("author.name", "张三");
        System.out.println(blog.getAuthor().getName());
        System.out.println("------------------------------------------------");
        // 查找属性名 支持下划线转驼峰
        System.out.println(metaObject.findProperty("author.out_look", true));
        System.out.println("------------------------------------------------");
        // 访问数组
        Common common = new Common();
        common.setContentStr("hello");
        metaObject.setValue("commons", Collections.singletonList(common));
        System.out.println(blog.getCommons().get(0).getContentStr());
        System.out.println(metaObject.getValue("commons[0].contentStr"));
        System.out.println("------------------------------------------------");
        // 访问Map
        metaObject.setValue("labels", new HashMap<>());
        metaObject.setValue("labels[red]", "开心");
        System.out.println(metaObject.getValue("labels[red]"));
        System.out.println("------------------------------------------------");
    }

}
