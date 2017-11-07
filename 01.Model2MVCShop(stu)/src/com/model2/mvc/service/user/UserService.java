package com.model2.mvc.service.user;

import java.util.HashMap;

import com.model2.mvc.common.Search;
//import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.User;
//import com.model2.mvc.service.user.vo.UserVO;


public interface UserService {
	
	public void addUser(User user) throws Exception;
	
	public User loginUser(User user) throws Exception;
	
	public User getUser(String userId) throws Exception;
	
	public HashMap<String, Object> getUserList(Search search) throws Exception;
	
	public void updateUser(User user) throws Exception;
	
	public boolean checkDuplication(String userId) throws Exception;
	
}