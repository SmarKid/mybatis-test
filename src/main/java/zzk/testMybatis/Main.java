package zzk.testMybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        String resource = "mybatis-config.xml";
        String absoluteResource = "E:\\workspace\\Java\\开源项目\\mybatis-test\\src\\main\\resources\\mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
//        InputStream inputStream = Files.newInputStream(Paths.get(absoluteResource));
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try(SqlSession session = sqlSessionFactory.openSession()) {
            Object o = session.selectOne("zzk.mapper.UserMapper.selectUserById", 1);
            System.out.println("我是第一次查询的"+o);
            System.out.println("-------------------------------我是分割线---------------------");
            Object z = session.selectOne("zzk.mapper.UserMapper.selectUserById", 1);
            System.out.println("我是第二次查询的"+z);
        }
    }
}
