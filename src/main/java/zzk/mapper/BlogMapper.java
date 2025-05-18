package zzk.mapper;

import org.apache.ibatis.annotations.Param;
import zzk.entity.Blog;

public interface BlogMapper {

    Blog selectBlogById(@Param("id") Integer id);
}
