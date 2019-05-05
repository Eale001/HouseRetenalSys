package com.eale.hrsm.controller.hrms;

import com.eale.hrsm.bean.*;
import com.eale.hrsm.service.*;
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
 * @description  房源信息controller
 * @author: Eale
 * @date:2019/4/28/028-15:00
 */
@Controller
@RequestMapping("/")
public class HouseController {
    
    @Autowired
    private HouseService houseService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private DistrictService districtService;

    /**
     * @description   进入房源信息列表
     * @date: 2019/4/29/029 3:25
     * @author: Eale
     * @prams [request, session]
     * @return java.lang.String
     */
    @RequestMapping("houseManage")
    public String houseManage(Model model){
        List<House> houseAll = houseService.findAll();
        model.addAttribute("houseAll",houseAll);
        return "hrsm/houseManage";
    }

    /**
     * @description   查看详情
     * @date: 2019/5/4/004 20:43
     * @author: Eale
     * @prams [model, session, houseId]
     * @return java.lang.String
     */
    @RequestMapping("houseDetail")
    public String houseDetail(Model model, HttpSession session, @RequestParam(value = "houseId",required = false) String houseId){

        User user = userService.findById((Long)session.getAttribute("userId"));

        model.addAttribute("user",user);

        if (!StringUtil.isEmpty(houseId)){
            House house = houseService.findById(Long.parseLong(houseId));
            model.addAttribute("house",house);
            return "hrsm/houseDetail";
        }
        List<Type> typeList = typeService.findAll();
        List<District> districtList = districtService.findAll();
        model.addAttribute("typeList",typeList);
        model.addAttribute("districtList",districtList);
        return "hrsm/houseDetail";
    }

    /**
     * @description   进入房源信息新增、修改 页面
     * @date: 2019/4/29/029 3:36
     * @author: Eale
     * @prams [model, session]
     * @return java.lang.String
     */
    @RequestMapping("houseEdit")
    public String houseEdit(Model model, HttpSession session, @RequestParam(value = "houseId",required = false) String houseId){

        User user = userService.findById((Long)session.getAttribute("userId"));

        model.addAttribute("user",user);

        if (!StringUtil.isEmpty(houseId)){
            House house = houseService.findById(Long.parseLong(houseId));
            model.addAttribute("house",house);
            return "hrsm/houseEdit";
        }
        List<Type> typeList = typeService.findAll();
        List<District> districtList = districtService.findAll();
        model.addAttribute("typeList",typeList);
        model.addAttribute("districtList",districtList);
        return "hrsm/houseEdit";
    }

    /**
     * @description   保存 修改 房源信息
     * @date: 2019/5/4/004 18:23
     * @author: Eale
     * @prams [house, model]
     * @return java.lang.String
     */
    @RequestMapping(value = "houseSave",method = RequestMethod.POST)
    public String houseSave(House house,String regionId,String typeId,Model model,HttpSession session){

        User user = userService.findById((Long)session.getAttribute("userId"));
        Type type = typeService.findById(Long.parseLong(typeId));
        District district = districtService.findById(Long.parseLong(regionId));

        house.setType(type);
        house.setDistrict(district);
        house.setCreateDate(new Date());
        house.setCreateUser(user);
        houseService.save(house);
        return "/houseManage";

    }

    /**
     * @description   删除房源信息
     * @date: 2019/5/4/004 18:38
     * @author: Eale
     * @prams [houseId, model]
     * @return java.lang.String
     */
    @RequestMapping("houseDelete")
    public String houseDelete(@RequestParam(value = "houseId")String houseId,Model model){
        houseService.deleteById(Long.parseLong(houseId));
        return "/houseManage";

    }
    
}
