package test;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import zzk.entity.Blog;
import zzk.mapper.BlogMapper;

import javax.imageio.stream.FileCacheImageInputStream;

public class ComplexTest {

    SqlSessionFactory factory;
    Configuration     configuration;

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

}
