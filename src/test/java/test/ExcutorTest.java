package test;

import org.apache.ibatis.executor.*;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zzk.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcutorTest {

    private Configuration configuration;

    private Connection connection;

    private JdbcTransaction jdbcTransaction;

    private static final Logger logger = LoggerFactory.getLogger(ExcutorTest.class);
    private SqlSessionFactory sqlSessionFactory
            ;

    @Before
    public void init() throws Exception {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = factoryBuilder.build(getClass().getClassLoader().getResourceAsStream("mybatis-config.xml"));
        configuration = sqlSessionFactory.getConfiguration();
        connection = DriverManager.getConnection(JDBC.URL, JDBC.USER, JDBC.PASSWORD);
        jdbcTransaction  = new JdbcTransaction(connection);
    }

    // 测试简单执行器
    @Test
    public void testSimple() throws SQLException {
        SimpleExecutor executor = new SimpleExecutor(configuration, jdbcTransaction);
        MappedStatement ms = configuration.getMappedStatement("zzk.mapper.UserMapper.selectUserById");
        List<Object> list = executor.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(1));
        executor.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(1));
        System.out.println(list.get(0));
    }

    // 测试可重用执行器
    @Test
    public void testReuse() throws SQLException {
        ReuseExecutor executor = new ReuseExecutor(configuration, jdbcTransaction);
        MappedStatement ms = configuration.getMappedStatement("zzk.mapper.UserMapper.selectUserById");
        List<Object> list = executor.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER,
                ms.getBoundSql(1));
        executor.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(1));
        System.out.println(list.get(0));
    }

    // 测试批量执行器
    @Test
    public void testBatch() throws SQLException {
        BatchExecutor executor = new BatchExecutor(configuration, jdbcTransaction);
        MappedStatement ms = configuration.getMappedStatement("zzk.mapper.UserMapper.insertUser");
        HashMap<Object, Object> map = new HashMap<>();
        map.put("user", new User("zzk1", 4));
        executor.doUpdate(ms, map);
        HashMap<Object, Object> map1 = new HashMap<>();
        map1.put("user", new User("zzk2", 5));
        executor.doUpdate(ms, map1);
        executor.doFlushStatements(false);
    }

    // baseExceutor
    @Test
    public void testBase() throws SQLException {
        Executor executor = new SimpleExecutor(configuration, jdbcTransaction);
        MappedStatement ms = configuration.getMappedStatement("zzk.mapper.UserMapper.selectUserById");
        executor.query(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER);
        executor.query(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER);

    }

    // 测试二级缓存
    @Test
    public void testCache() throws SQLException {
        Executor executor = new SimpleExecutor(configuration, jdbcTransaction);
        executor = new CachingExecutor(executor);
        MappedStatement ms = configuration.getMappedStatement("zzk.mapper.UserMapper.selectUserById");
        executor.query(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER);
        executor.commit(true);
        executor.query(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER);
        executor.query(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER);
    }

    @Test
    public void testSession() throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        List<User> users = sqlSession.selectList("zzk.mapper.UserMapper.selectUserById", 1);
        System.out.println(users);
    }

    @Test
    public void testLocalCache() throws SQLException {
        LocalCacheExcutor localCacheExcutor = new LocalCacheExcutor(new SimpleExecutor(configuration, jdbcTransaction));
        localCacheExcutor.query(configuration.getMappedStatement("zzk.mapper.UserMapper.selectUserById"), 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER);
        localCacheExcutor.query(configuration.getMappedStatement("zzk.mapper.UserMapper.selectUserById"), 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER);
    }

    @Test
    public void testLog() throws SQLException {
        logger.error("test");
    }


}
