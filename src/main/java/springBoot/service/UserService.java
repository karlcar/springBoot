package springBoot.service;

import java.util.List;
import java.util.Map;

import springBoot.po.User;

public interface UserService {
	//分页查询
	List<User> getUserByPage(Map<String,Object> paramMap);
	//分页需要总数查询
	int getUserByTotal();
	//添加用户
	int addUser(User user);
	//修改用户
	int updateUser(User user);
	//删除用户
	int deleteUser(Integer id);
	//根据用户id查询用户
	User getUserById(Integer id);
	
}
