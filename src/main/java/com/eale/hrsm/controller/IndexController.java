package com.eale.hrsm.controller;

import com.eale.hrsm.bean.House;
import com.eale.hrsm.bean.Rolemenu;
import com.eale.hrsm.bean.User;
import com.eale.hrsm.service.HouseService;
import com.eale.hrsm.service.RolepowerlistService;
import com.eale.hrsm.service.SystemMenuService;
import com.eale.hrsm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @description 首页的controller
 * @author: Eale
 * @date:2019/4/28/028-12:18
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private SystemMenuService systemMenuService;

    @Autowired
    private UserService userService;


    @Autowired
    private RolepowerlistService rolepowerlistService;

    @Autowired
    private HouseService houseService;


    @RequestMapping("index")
    public String index(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if(StringUtils.isEmpty(session.getAttribute("userId"))){
            return "login/login";
        }
        Long userId = Long.parseLong(session.getAttribute("userId") + "");
        User user=userService.findById(userId);

        systemMenuService.findMenuSys(req,user);

        model.addAttribute("user", user);
        //展示用户操作记录 由于现在没有登陆 不能获取用户id
        return "index/index";
    }
    /**
     * 菜单查找
     * @param model
     * @param req
     * @return
     */
    @RequestMapping("menucha")
    public String menucha(HttpSession session, Model model, HttpServletRequest req){
        Long userId = Long.parseLong(session.getAttribute("userId") + "");
        User user=userService.findById(userId);

        String val=null;
        if(!StringUtils.isEmpty(req.getParameter("val"))){
            val=req.getParameter("val");
        }
        if(!StringUtils.isEmpty(val)){
//			List<Rolemenu> oneMenuAll=rdao.findname(0L, user.getRole().getRoleId(), true, true, val);//找父菜单
            List<Rolemenu> oneMenuAll=rolepowerlistService.findname(0L, user.getRole().getRoleId(), true, true, val);//找父菜单


            List<Rolemenu> twoMenuAll=null;
            for (int i = 0; i < oneMenuAll.size(); i++) {
                twoMenuAll=rolepowerlistService.findbyparentsxian(oneMenuAll.get(i).getMenuId(), user.getRole().getRoleId(), true, true);//找子菜单
            }
            req.setAttribute("oneMenuAll", oneMenuAll);
            req.setAttribute("twoMenuAll", twoMenuAll);
        }else{
            systemMenuService.findMenuSys(req,user);
        }

        return "common/leftlists";

    }

    /**
     * 控制面板主页
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("test2")
    public String test2(HttpSession session, Model model, HttpServletRequest request) {
        Long userId = Long.parseLong(session.getAttribute("userId") + "");
        User user=userService.findById(userId);
        request.setAttribute("user", user);
        //计算三个模块的记录条数

        model.addAttribute("user", user);

        List<House> houseList = houseService.findAll();
        model.addAttribute("houseAll",houseList);

        return "systemcontrol/control";
    }

    @RequestMapping("test3")
    public String test3() {
        return "note/noteview";
    }

    @RequestMapping("test4")
    public String test4() {
        return "mail/editaccount";
    }

    @RequestMapping("notlimit")
    public String notLimit() {
        return "common/notlimit";
    }
    // 测试系统管理

    @RequestMapping("one")
    public String witeMail() {
        return "mail/wirtemail";
    }

    @RequestMapping("two")
    public String witeMail2() {
        return "mail/seemail";
    }

    @RequestMapping("three")
    public String witeMail3() {
        return "mail/allmail";
    }

    @RequestMapping("mmm")
    public String witeMail4() {
        return "mail/mail";
    }

}
