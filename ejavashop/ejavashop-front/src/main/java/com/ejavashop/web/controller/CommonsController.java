package com.ejavashop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 公共跳转
 *                       
 * @Filename: CommonsController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
public class CommonsController extends BaseController {

    @RequestMapping(value = "/error404.html", method = RequestMethod.GET)
    public String error404() {
        return "front/error/error404";
    }

    @RequestMapping(value = "/error500.html", method = RequestMethod.GET)
    public String error500() {
        return "front/error/error500";
    }

    @RequestMapping(value = "/error.html", method = RequestMethod.GET)
    public String error() {
        return "front/error/error500";
    }
}
