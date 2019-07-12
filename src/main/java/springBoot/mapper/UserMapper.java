package springBoot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import springBoot.po.User;
import springBoot.po.UserExample;

public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);
    
    int deleteByPrimaryKey(Integer id);
    
    User selectByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);
    
    int updateByExampleSelective(@Param("record") User record);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

	List<User> selectUserByPage(Map<String, Object> paramMap);

	int selectUserByTotal();
}