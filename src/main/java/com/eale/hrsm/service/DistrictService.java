package com.eale.hrsm.service;

import com.eale.hrsm.bean.District;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @description
 * @author: Eale
 * @date:2019/4/28/028-15:09
 */
@Service
@Transactional
public interface DistrictService {

    /**
     * @description   根据ID查询
     * @date: 2019/4/28/028 15:10
     * @author: Eale
     * @prams [districtId]
     * @return com.eale.hrsm.bean.District
     */
    District findById(Long districtId);

    /**
     * @description   保存
     * @date: 2019/4/28/028 15:11
     * @author: Eale
     * @prams [district]
     * @return void
     */
    void save(District district);

    /**
     * @description   修改
     * @date: 2019/4/28/028 15:13
     * @author: Eale
     * @prams [house]
     * @return com.eale.hrsm.bean.House
     */
    District update(District district);

    /**
     * @description   查询全部
     * @date: 2019/4/28/028 15:14
     * @author: Eale
     * @prams []
     * @return java.util.List<com.eale.hrsm.bean.District>
     */
    List<District> findAll();

    /**
     * @description   根据条件查询
     * @date: 2019/4/28/028 15:14
     * @author: Eale
     * @prams [district]
     * @return java.util.List<com.eale.hrsm.bean.District>
     */
    List<District> findByConditions(District district);

    /**
     * @description   根据Id删除
     * @date: 2019/5/4/004 18:42
     * @author: Eale
     * @prams [parseLong]
     * @return void
     */
    void deleteById(long districtId);
}
