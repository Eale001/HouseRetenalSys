package com.eale.hrsm.controller.hrms;

import com.eale.hrsm.bean.Type;
import com.eale.hrsm.bean.User;
import com.eale.hrsm.service.TypeService;
import com.eale.hrsm.service.UserService;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @description 房屋类型的controller
 * @author: Eale
 * @date:2019/4/29/029-2:11
 */
@Controller
@RequestMapping("/")
public class TypeController {


    @Autowired
    private TypeService typeService;

    @Autowired
    private UserService userService;

    /**
     * @description   进入类型列表
     * @date: 2019/4/29/029 3:25
     * @author: Eale
     * @prams [request, session]
     * @return java.lang.String
     */
    @RequestMapping("typeManage")
    public String typeManage(Model model){
        List<Type> typeAll = typeService.findAll();
        model.addAttribute("typeAll",typeAll);
        return "hrsm/typeManage";
    }


    @RequestMapping("typeDetail")
    public String typeDetail(Model model, HttpSession session,@RequestParam(value = "typeId",required = false) String typeId){

        User user = userService.findById((Long)session.getAttribute("userId"));

        model.addAttribute("user",user);

        if (!StringUtil.isEmpty(typeId)){
            Type type = typeService.findById(Long.parseLong(typeId));
            model.addAttribute("type",type);
            return "hrsm/typeDetail";
        }

        return "hrsm/typeDetail";
    }
    
    /**
     * @description   进入类型新增、修改 页面
     * @date: 2019/4/29/029 3:36
     * @author: Eale
     * @prams [model, session]
     * @return java.lang.String
     */
    @RequestMapping("typeEdit")
    public String typeEdit(Model model, HttpSession session,@RequestParam(value = "typeId",required = false) String typeId){

        User user = userService.findById((Long)session.getAttribute("userId"));

        model.addAttribute("user",user);

        if (!StringUtil.isEmpty(typeId)){
            Type type = typeService.findById(Long.parseLong(typeId));
            model.addAttribute("type",type);
            return "hrsm/typeEdit";
        }

        return "hrsm/typeEdit";
    }

    /**
     * @description   保存 修改 类型
     * @date: 2019/5/4/004 18:23
     * @author: Eale
     * @prams [type, model]
     * @return java.lang.String
     */
    @RequestMapping(value = "typeSave",method = RequestMethod.POST)
    public String typeSave(Type type,Model model,HttpSession session){

        User user = userService.findById((Long)session.getAttribute("userId"));

        type.setCreateDate(new Date());
        type.setCreateUser(user);
        typeService.save(type);
        return "/typeManage";

    }

    /**
     * @description   删除类型
     * @date: 2019/5/4/004 18:38
     * @author: Eale
     * @prams [typeId, model]
     * @return java.lang.String
     */
    @RequestMapping("typeDelete")
    public String typeDelete(@RequestParam(value = "typeId")String typeId,Model model){
        typeService.deleteById(Long.parseLong(typeId));
        return "/typeManage";

    }


}
