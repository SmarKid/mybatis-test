package zzk.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import zzk.entity.User;

@CacheNamespace
public interface UserMapper {
    User selectUserById(@Param("id") Integer id);
    @Select({"select * from user_info where id = #{1}"})
    User selectUserByPrimaryKey(Integer id);
    int insertUser(@Param("user") User user);

}
