package test;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import zzk.entity.User;
import zzk.mapper.UserMapper;

public class CacheTest {


    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;

    @Before
    public void init() {
//        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
//        sqlSessionFactory = factoryBuilder.build(CacheTest.class.getClassLoader().getResourceAsStream("mybatis-config.xml"));
//        sqlSession = sqlSessionFactory.openSession();
    }


    /**
     * 一级缓存有效必要条件
     * 运行时参数相关
     * 1.sql和参数必须相同
     * 2.statementID必须相同
     * 3.sqlSession必须相同
     */
    @Test
    public void testLocalCache() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user1 = mapper.selectUserById(1);
        User user2 = mapper.selectUserById(1);
        System.out.println("相同statementId，相同语句：" + (user1 == user2));
        User user3 = mapper.selectUserByPrimaryKey(1);
        System.out.println("不同statementId，相同语句：" + (user1 == user3));
        User user4 = sqlSessionFactory.openSession().getMapper(UserMapper.class).selectUserById(1);
        System.out.println("相同statementId，不同session：" + (user1 == user4));
    }
    /**
     * 一级缓存有效必要条件
     * 操作与配置相关
     * 1.未手动清空
     * 2.未调用 flushCache=true的查询
     * 3.未执行Update
     * 4.缓存的作用域不是STATEMENT -> 子查询同样有效
     */
    public void testLocalCache2() {

    }

    @Test
    public void testBySpring() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        // 动                                    动                       MyBatis
        // userMapper -> SqlSessionTemplate -> SqlSessionInterceptor -> SqlSessionFactory
        UserMapper userMapper = context.getBean(UserMapper.class);
        DataSourceTransactionManager txManager = (DataSourceTransactionManager) context.getBean("txManager");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());
        User user = userMapper.selectUserById(6);
        User user2 = userMapper.selectUserById(6);
        System.out.println(user == user2);

    }
}
