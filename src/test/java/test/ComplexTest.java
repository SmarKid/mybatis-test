package test;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import zzk.entity.Blog;
import zzk.entity.Comment;
import zzk.mapper.BlogMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ComplexTest {

    SqlSessionFactory factory;
    static Configuration configuration;

    @Before
    public void init() {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        factory = factoryBuilder.build(this.getClass().getResourceAsStream("/mybatis-config.xml"));
        configuration = factory.getConfiguration();
    }

    /**
     * 测试循环嵌套查询
     */
    @Test
    public void testNestQuery() {
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = blogMapper.selectBlogById(1);
        System.out.println(blog);
    }

    /**
     * 测试懒加载。执行后观察日志发现在分割线后又执行了查询。
     */
    @Test
    public void testLazyLoad() {
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = blogMapper.selectBlogById(1);
        System.out.println("--------------------------");
        List<Comment> commons = blog.getCommons();
    }

    /**
     * 测试调用get方法前调用set方法是否影响懒加载 发现调用set方法后不会再进行懒加载
     */
    @Test
    public void testLazyLoad2() {
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = blogMapper.selectBlogById(1);
        System.out.println("--------------------------");
        blog.setCommons(new ArrayList<>());
        List<Comment> commons = blog.getCommons();
        System.out.println(commons.size());
    }

    /**
     * 测试序列化后再反序列化是否影响懒加载 操作后不再进行懒加载
     */
    @Test
    public void testLazyLoad3() throws IOException, ClassNotFoundException {
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = blogMapper.selectBlogById(1);
        Blog o = (Blog) readObject(writeObject(blog));
        System.out.println("--------------------------");
        List<Comment> commons = o.getCommons();
        System.out.println(commons == null ? null : commons.size());
    }

    /**
     * 序列化反序列化 后 要进行懒加载需要设置 configurationFactory Blog 类需要继承 ConfigurationFactory 并实现 getConfiguration() 方法
     */
    @Test
    public void testLazyLoad4() throws IOException, ClassNotFoundException {
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = blogMapper.selectBlogById(1);
        Blog o = (Blog) readObject(writeObject(blog));
        System.out.println("--------------------------");
        List<Comment> commons = o.getCommons();
        System.out.println(commons == null ? null : commons.size());
    }

    public static class MyConfFactory {

        public static Configuration getConfiguration() {
            System.out.println("--------------getConfiguration------------");
            return configuration;
        }
    }

    private static byte[] writeObject(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(out);
        outputStream.writeObject(obj);
        return out.toByteArray();
    }

    private static Object readObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        ObjectInputStream inputStream = new ObjectInputStream(input);
        return inputStream.readObject();
    }

}
