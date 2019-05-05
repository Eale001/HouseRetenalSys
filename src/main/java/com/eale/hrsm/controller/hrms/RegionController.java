package com.eale.hrsm.controller.hrms;

import com.eale.hrsm.bean.Region;
import com.eale.hrsm.bean.User;
import com.eale.hrsm.service.RegionService;
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
 * @description  地区的controller
 * @author: Eale
 * @date:2019/5/4/004-18:49
 */
@Controller
@RequestMapping("/")
public class RegionController {
    
    @Autowired
    private RegionService regionService;
    
    @Autowired
    private UserService userService;


    /**
     * @description   进入地区列表
     * @date: 2019/4/29/029 3:25
     * @author: Eale
     * @prams [request, session]
     * @return java.lang.String
     */
    @RequestMapping("regionManage")
    public String regionManage(Model model){
        List<Region> regionAll = regionService.findAll();
        model.addAttribute("regionAll",regionAll);
        return "hrsm/regionManage";
    }


    /**
     * @description   进入查看详情
     * @date: 2019/5/4/004 20:27
     * @author: Eale
     * @prams [model, session, regionId]
     * @return java.lang.String
     */
    @RequestMapping("regionDetail")
    public String regionDetail(Model model, HttpSession session, @RequestParam(value = "regionId",required = false) String regionId){

        User user = userService.findById((Long)session.getAttribute("userId"));

        model.addAttribute("user",user);

        if (!StringUtil.isEmpty(regionId)){
            Region region = regionService.findById(Long.parseLong(regionId));
            model.addAttribute("region",region);
            return "hrsm/regionDetail";
        }

        return "hrsm/regionDetail";
    }


    /**
     * @description   进入地区新增、修改 页面
     * @date: 2019/4/29/029 3:36
     * @author: Eale
     * @prams [model, session]
     * @return java.lang.String
     */
    @RequestMapping("regionEdit")
    public String regionEdit(Model model, HttpSession session, @RequestParam(value = "regionId",required = false) String regionId){

        User user = userService.findById((Long)session.getAttribute("userId"));

        model.addAttribute("user",user);

        if (!StringUtil.isEmpty(regionId)){
            Region region = regionService.findById(Long.parseLong(regionId));
            model.addAttribute("region",region);
            return "hrsm/regionEdit";
        }

        return "hrsm/regionEdit";
    }

    /**
     * @description   保存 修改 地区
     * @date: 2019/5/4/004 18:23
     * @author: Eale
     * @prams [region, model]
     * @return java.lang.String
     */
    @RequestMapping(value = "regionSave",method = RequestMethod.POST)
    public String regionSave(Region region,Model model,HttpSession session){

        User user = userService.findById((Long)session.getAttribute("userId"));

        region.setCreateDate(new Date());
        region.setCreateUser(user);
        regionService.save(region);
        return "/regionManage";

    }

    /**
     * @description   删除地区
     * @date: 2019/5/4/004 18:38
     * @author: Eale
     * @prams [regionId, model]
     * @return java.lang.String
     */
    @RequestMapping("regionDelete")
    public String regionDelete(@RequestParam(value = "regionId")String regionId,Model model){
        regionService.deleteById(Long.parseLong(regionId));
        return "/regionManage";

    }
    
    
}
