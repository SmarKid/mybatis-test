package test;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import zzk.entity.User;
import zzk.mapper.UserMapper;

public class SecondCacheTest {

    SqlSessionFactory factory;
    Configuration configuration;

    @Before
    public void init() {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        factory = factoryBuilder.build(this.getClass().getResourceAsStream("/mybatis-config.xml"));
        configuration = factory.getConfiguration();
    }
    @Test
    public void cacheTest01() {
        Cache cache = configuration.getCache("zzk.mapper.UserMapper");
        User user = new User("zzk", 18);
        cache.putObject("zzk", user);
        System.out.println(cache.getObject("zzk"));
    }

    @Test
    public void cacheTest02() {
        SqlSession session01 = factory.openSession();
        UserMapper mapper01 = session01.getMapper(UserMapper.class);
        User user01 = mapper01.selectUserById(2);
        session01.commit();
        SqlSession sqlSession02 = factory.openSession();
        UserMapper mapper02 = sqlSession02.getMapper(UserMapper.class);
        User user02 = mapper02.selectUserById(2);
        System.out.println(user01 == user02);
        SqlSession sqlSession03 = factory.openSession();
        UserMapper mapper03 = sqlSession03.getMapper(UserMapper.class);
        User user03 = mapper03.selectUserById(2);
        System.out.println(user03 == user02);
    }

    @Test
    public void cacheTest03() {
        SqlSession session = factory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.selectUserByPrimaryKey(2);
        System.out.println(user);
    }
}
