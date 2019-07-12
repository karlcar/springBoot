package springBoot.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import springBoot.mapper.UserMapper;
import springBoot.po.User;
import springBoot.service.UserService;

//@Component //spring注解
@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Override
	public List<User> getUserByPage(Map<String, Object> paramMap) {
		
		return userMapper.selectUserByPage(paramMap);
	}

	@Override
	public int getUserByTotal() {
		//设置Key序列化方式，采用字符串方式，可读性更好
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		//取得redis总数
		Integer totalRows = (Integer)redisTemplate.opsForValue().get("totalRows");
		if(totalRows == null) {
			synchronized (this) {
				totalRows = (Integer)redisTemplate.opsForValue().get("totalRows");
				if(totalRows == null) {
					totalRows = userMapper.selectUserByTotal();
					redisTemplate.opsForValue().set("totalRows", totalRows);
				}
			}
		}
		return totalRows;
	}

	@Override
	public int addUser(User user) {
		//设置Key序列化方式，采用字符串方式，可读性更好
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		int add = userMapper.insertSelective(user);
		if(add > 0) {
			//更新下缓存的总数
			int totalRows = userMapper.selectUserByTotal();
			redisTemplate.opsForValue().set("totalRows", totalRows);
		}
		return add;
	}

	@Override
	public int updateUser(User user) {
		return userMapper.updateByExampleSelective(user);
	}

	@Override
	public int deleteUser(Integer id) {
		//设置Key序列化方式，采用字符串方式，可读性更好
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		int delete = userMapper.deleteByPrimaryKey(id);
		if(delete > 0) {
			//更新下缓存的总数
			int totalRows = userMapper.selectUserByTotal();
			redisTemplate.opsForValue().set("totalRows", totalRows);
		}
		return delete;
	}

	@Override
	public User getUserById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

}
