package com.allinone.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginService {
@RequestMapping(value="/login",method=RequestMethod.POST)
@ResponseBody
public String loginrequest( @RequestParam String name, @RequestParam String password) {
	return "Welcome";
}
}
