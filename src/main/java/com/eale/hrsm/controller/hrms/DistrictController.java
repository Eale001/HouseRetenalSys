package com.eale.hrsm.controller.hrms;

import com.eale.hrsm.bean.District;
import com.eale.hrsm.bean.Region;
import com.eale.hrsm.bean.User;
import com.eale.hrsm.service.DistrictService;
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
 * @description 街道的controller
 * @author: Eale
 * @date:2019/5/4/004-18:37
 */
@Controller
@RequestMapping("/")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @Autowired
    private UserService userService;

    @Autowired
    private RegionService regionService;

    /**
     * @description   进入街道列表
     * @date: 2019/4/29/029 3:25
     * @author: Eale
     * @prams [request, session]
     * @return java.lang.String
     */
    @RequestMapping("districtManage")
    public String districtManage(Model model){
        List<District> districtAll = districtService.findAll();
        model.addAttribute("districtAll",districtAll);
        return "hrsm/districtManage";
    }


    @RequestMapping("districtDetail")
    public String districtDetail(Model model, HttpSession session, @RequestParam(value = "districtId",required = false) String districtId){

        User user = userService.findById((Long)session.getAttribute("userId"));

        model.addAttribute("user",user);

        if (!StringUtil.isEmpty(districtId)){
            District district = districtService.findById(Long.parseLong(districtId));
            model.addAttribute("district",district);
            return "hrsm/districtDetail";
        }
        List<Region> regionList = regionService.findAll();
        model.addAttribute("regionList",regionList);
        return "hrsm/districtDetail";
    }

    /**
     * @description   进入街道新增、修改 页面
     * @date: 2019/4/29/029 3:36
     * @author: Eale
     * @prams [model, session]
     * @return java.lang.String
     */
    @RequestMapping("districtEdit")
    public String districtEdit(Model model, HttpSession session, @RequestParam(value = "districtId",required = false) String districtId){

        User user = userService.findById((Long)session.getAttribute("userId"));

        model.addAttribute("user",user);

        if (!StringUtil.isEmpty(districtId)){
            District district = districtService.findById(Long.parseLong(districtId));
            model.addAttribute("district",district);
            return "hrsm/districtEdit";
        }
        List<Region> regionList = regionService.findAll();
        model.addAttribute("regionList",regionList);
        return "hrsm/districtEdit";
    }

    /**
     * @description   保存 修改 街道
     * @date: 2019/5/4/004 18:23
     * @author: Eale
     * @prams [district, model]
     * @return java.lang.String
     */
    @RequestMapping(value = "districtSave",method = RequestMethod.POST)
    public String districtSave(District district,@RequestParam(value = "regionId")String regionId, Model model,HttpSession session){

        User user = userService.findById((Long)session.getAttribute("userId"));

        Region region = regionService.findById(Long.parseLong(regionId));

        district.setRegion(region);
        district.setCreateDate(new Date());
        district.setCreateUser(user);

        districtService.save(district);
        return "/districtManage";

    }

    /**
     * @description   删除街道
     * @date: 2019/5/4/004 18:38
     * @author: Eale
     * @prams [districtId, model]
     * @return java.lang.String
     */
    @RequestMapping("districtDelete")
    public String districtDelete(@RequestParam(value = "districtId")String districtId,Model model){
        districtService.deleteById(Long.parseLong(districtId));
        return "/districtManage";

    }

}
