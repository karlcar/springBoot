package springBoot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springBoot.po.User;
import springBoot.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping("/index")
	public String index(Model model,
			@RequestParam(value = "curPage", required = false) Integer curPage) {
		// 写死每页10条数据
		int pageSize = 4;

		if (curPage == null || curPage < 1) {
			curPage = 1;
		}

		// 总数
		int totalRows = userService.getUserByTotal();
		// 计算分页
		int totalPages = totalRows / pageSize;
		// 有余数
		int left = totalRows % pageSize;
		if (left > 0) {
			totalPages = totalPages + 1;
		}
		//有可能自己链接中填写curPage大于总页数
		if(curPage > totalPages) {
			curPage = totalPages;
		}
		
		
		int startRow = (curPage - 1) * pageSize;
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("startRow", startRow);
		paramMap.put("pageSize", pageSize);
		
		List<User> userList = userService.getUserByPage(paramMap);
		model.addAttribute("userList", userList);
		model.addAttribute("curPage", curPage);
		model.addAttribute("totalPages", totalPages);

		return "index";
	}
	
	//去到添加用户页面&&渠道修改页面
	@RequestMapping("/user/toAddUser")
	public String toAddUser() {
		return "addUser";
	}
	
	//添加用户，springboot可以直接嵌入user
	@RequestMapping("/user/addUser")
	public String addUser(User user) {
		Integer id = user.getId();
		if(id == null) {
			//添加用户
			userService.addUser(user);
		}else {
			//修改用户
			userService.updateUser(user);
		}
		return "redirect:/index";
	}
	
	
	
	//去到修改用户页面
		@RequestMapping("/user/toUpdateUser")
		public String toUpdateUser(Model model,@RequestParam("id") Integer id) {
			User user = userService.getUserById(id);
			model.addAttribute("user", user);
			return "addUser";
		}
		
		//删除用户
		@RequestMapping("/user/delete")
		public String delete(@RequestParam("id") Integer id) {
			userService.deleteUser(id);
			return "redirect:/index";
		}
}
