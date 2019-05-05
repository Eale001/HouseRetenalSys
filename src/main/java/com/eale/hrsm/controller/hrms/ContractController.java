package com.eale.hrsm.controller.hrms;

import com.eale.hrsm.bean.Contract;
import com.eale.hrsm.bean.House;
import com.eale.hrsm.bean.Contract;
import com.eale.hrsm.bean.User;
import com.eale.hrsm.service.*;
import com.eale.hrsm.service.ContractService;
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
 * @description 合同controller
 * @author: Eale
 * @date:2019/5/4/004-18:35
 */
@Controller
@RequestMapping("/")
public class ContractController {

    @Autowired
    private ContractService contractService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private HouseService houseService;


    /**
     * @description   进入合同列表
     * @date: 2019/4/29/029 3:25
     * @author: Eale
     * @prams [request, session]
     * @return java.lang.String
     */
    @RequestMapping("contractManage")
    public String contractManage(Model model){
        List<Contract> contractAll = contractService.findAll();
        model.addAttribute("contractAll",contractAll);
        return "hrsm/contractManage";
    }

    /**
     * @description   合同详情
     * @date: 2019/5/4/004 21:20
     * @author: Eale
     * @prams [model, session, contractId]
     * @return java.lang.String
     */
    @RequestMapping("contractDetail")
    public String contractDetail(Model model, HttpSession session, @RequestParam(value = "contractId",required = false) String contractId){

        User user = userService.findById((Long)session.getAttribute("userId"));

        model.addAttribute("user",user);

        if (!StringUtil.isEmpty(contractId)){
            Contract contract = contractService.findById(Long.parseLong(contractId));
            model.addAttribute("contract",contract);
            return "hrsm/contractDetail";
        }

        return "hrsm/contractDetail";
    }

    /**
     * @description   进入合同新增、修改 页面
     * @date: 2019/4/29/029 3:36
     * @author: Eale
     * @prams [model, session]
     * @return java.lang.String
     */
    @RequestMapping("contractEdit")
    public String contractEdit(Model model, HttpSession session, @RequestParam(value = "contractId",required = false) String contractId){

        User user = userService.findById((Long)session.getAttribute("userId"));

        model.addAttribute("user",user);

        if (!StringUtil.isEmpty(contractId)){
            Contract contract = contractService.findById(Long.parseLong(contractId));
            model.addAttribute("contract",contract);
            return "hrsm/contractEdit";
        }

        return "hrsm/contractEdit";
    }

    /**
     * @description   保存 修改 合同
     * @date: 2019/5/4/004 18:23
     * @author: Eale
     * @prams [contract, model]
     * @return java.lang.String
     */
    @RequestMapping(value = "contractSave",method = RequestMethod.POST)
    public String contractSave(Contract contract,String houseId,Model model,HttpSession session){

        User user = userService.findById((Long)session.getAttribute("userId"));
        House house = houseService.findById(Long.parseLong(houseId));

        contract.setHouse(house);
        contract.setCreateDate(new Date());
        contract.setCreateUser(user);
        contractService.save(contract);
        return "/contractManage";

    }


    /**
     * @description   删除合同
     * @date: 2019/5/4/004 18:38
     * @author: Eale
     * @prams [contractId, model]
     * @return java.lang.String
     */
    @RequestMapping("contractDelete")
    public String contractDelete(@RequestParam(value = "contractId")String contractId,Model model){
        contractService.deleteById(Long.parseLong(contractId));
        return "/contractManage";

    }
    
    
}
