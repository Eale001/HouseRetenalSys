package com.eale.hrsm.controller.hrms;

import com.eale.hrsm.bean.Contract;
import com.eale.hrsm.bean.House;
import com.eale.hrsm.bean.Order;
import com.eale.hrsm.bean.User;
import com.eale.hrsm.service.ContractService;
import com.eale.hrsm.service.HouseService;
import com.eale.hrsm.service.OrderService;
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
 * @description 订单的Controller
 * @author: Eale
 * @date:2019/5/4/004-18:48
 */
@Controller
@RequestMapping("/")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private UserService userService;


    /**
     * @description   进入订单列表
     * @date: 2019/4/29/029 3:25
     * @author: Eale
     * @prams [request, session]
     * @return java.lang.String
     */
    @RequestMapping("orderManage")
    public String orderManage(Model model){
        List<Order> orderAll = orderService.findAll();
        model.addAttribute("orderAll",orderAll);
        return "hrsm/orderManage";
    }

    /**
     * @description   订单详情
     * @date: 2019/5/4/004 21:20
     * @author: Eale
     * @prams [model, session, orderId]
     * @return java.lang.String
     */
    @RequestMapping("orderDetail")
    public String orderDetail(Model model, HttpSession session, @RequestParam(value = "orderId",required = false) String orderId){

        User user = userService.findById((Long)session.getAttribute("userId"));

        model.addAttribute("user",user);

        if (!StringUtil.isEmpty(orderId)){
            Order order = orderService.findById(Long.parseLong(orderId));
            model.addAttribute("order",order);
            return "hrsm/orderDetail";
        }

        return "hrsm/orderDetail";
    }

    /**
     * @description   进入订单新增、修改 页面
     * @date: 2019/4/29/029 3:36
     * @author: Eale
     * @prams [model, session]
     * @return java.lang.String
     */
    @RequestMapping("orderEdit")
    public String orderEdit(Model model, HttpSession session, @RequestParam(value = "orderId",required = false) String orderId,@RequestParam(value = "houseId",required = false)String houseId){

        User user = userService.findById((Long)session.getAttribute("userId"));

        model.addAttribute("user",user);

        if (null != houseId){
            House house = houseService.findById(Long.parseLong(houseId));
            model.addAttribute("house",house);
            return "hrsm/orderBook";

        }

        if (!StringUtil.isEmpty(orderId)){
            Order order = orderService.findById(Long.parseLong(orderId));
            model.addAttribute("order",order);
            return "hrsm/orderEdit";
        }
        List<House> houseList = houseService.findAll();
        model.addAttribute("houseList",houseList);
        return "hrsm/orderEdit";
    }

    /**
     * @description   保存 修改 订单
     * @date: 2019/5/4/004 18:23
     * @author: Eale
     * @prams [order, model]
     * @return java.lang.String
     */
    @RequestMapping(value = "orderSave",method = RequestMethod.POST)
    public String orderSave(Order order,String houseId,Model model,HttpSession session){

        User user = userService.findById((Long)session.getAttribute("userId"));
        House house = houseService.findById(Long.parseLong(houseId));

        order.setHouse(house);
        order.setState(1);
        order.setCreateDate(new Date());
        order.setCreateUser(user);
        orderService.save(order);
        return "/orderManage";

    }

    /**
     * @description   完成订单，生成合同
     * @date: 2019/5/4/004 21:36
     * @author: Eale
     * @prams [orderId, model, session]
     * @return java.lang.String
     */
    @RequestMapping("orderConfirm")
    public String orderConfirm(Order order,Model model,HttpSession session,Contract contract,String houseId){

        order.setState(2);
        User user = userService.findById((Long)session.getAttribute("userId"));
        House house = houseService.findById(Long.parseLong(houseId));
        Contract contracts = new Contract();
        contracts.setHouse(house);
        contracts.setCreateUser(user);
        contracts.setFirstParty(order.getLandlady());
        contracts.setPartyB(order.getTenant());
        contracts.setAddress(order.getHouse().getDistrict().getRegion().getRegionName()+order.getHouse().getDistrict().getDisName());
        contracts.setBeginDate(order.getBeginDate());
        contracts.setBeginDate(order.getEndDate());
        contracts.setDeposit(order.getHouse().getPrice());
        contracts.setMoney(contract.getMoney());
        contracts.setRent(contract.getRent());
        contracts.setOrder(order);
        contractService.save(contracts);

        return "";
    }

    /**
     * @description   删除订单
     * @date: 2019/5/4/004 18:38
     * @author: Eale
     * @prams [orderId, model]
     * @return java.lang.String
     */
    @RequestMapping("orderDelete")
    public String orderDelete(@RequestParam(value = "orderId")String orderId,Model model){
        orderService.deleteById(Long.parseLong(orderId));
        return "/orderManage";

    }
    
    
}
