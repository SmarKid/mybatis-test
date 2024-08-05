package test;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import zzk.mapper.UserMapper;

public class ParamTest {
    private SqlSession sqlSession;
    private UserMapper mapper;

    @Before
    public void init() {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = factoryBuilder.build(this.getClass().getResourceAsStream("/mybatis-config.xml"));
        sqlSession = factory.openSession();
        mapper = sqlSession.getMapper(UserMapper.class);
    }

    @After
    public void over() {
        sqlSession.close();
    }

    @Test
    public void singleTest() {
        mapper.selectUserById(3);
    }
}
