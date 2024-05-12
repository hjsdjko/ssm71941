package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.KaoshitongzhiEntity;
import com.entity.view.KaoshitongzhiView;

import com.service.KaoshitongzhiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;

/**
 * 考试通知
 * 后端接口
 * @author 
 * @email 
 * @date 2022-05-25 10:37:24
 */
@RestController
@RequestMapping("/kaoshitongzhi")
public class KaoshitongzhiController {
    @Autowired
    private KaoshitongzhiService kaoshitongzhiService;



    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,KaoshitongzhiEntity kaoshitongzhi, 
		HttpServletRequest request){

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("xuesheng")) {
			kaoshitongzhi.setXuehao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("jiaoshi")) {
			kaoshitongzhi.setJiaoshigonghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<KaoshitongzhiEntity> ew = new EntityWrapper<KaoshitongzhiEntity>();
		PageUtils page = kaoshitongzhiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, kaoshitongzhi), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,KaoshitongzhiEntity kaoshitongzhi, 
		HttpServletRequest request){
        EntityWrapper<KaoshitongzhiEntity> ew = new EntityWrapper<KaoshitongzhiEntity>();
		PageUtils page = kaoshitongzhiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, kaoshitongzhi), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( KaoshitongzhiEntity kaoshitongzhi){
       	EntityWrapper<KaoshitongzhiEntity> ew = new EntityWrapper<KaoshitongzhiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( kaoshitongzhi, "kaoshitongzhi")); 
        return R.ok().put("data", kaoshitongzhiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(KaoshitongzhiEntity kaoshitongzhi){
        EntityWrapper< KaoshitongzhiEntity> ew = new EntityWrapper< KaoshitongzhiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( kaoshitongzhi, "kaoshitongzhi")); 
		KaoshitongzhiView kaoshitongzhiView =  kaoshitongzhiService.selectView(ew);
		return R.ok("查询考试通知成功").put("data", kaoshitongzhiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        KaoshitongzhiEntity kaoshitongzhi = kaoshitongzhiService.selectById(id);
        return R.ok().put("data", kaoshitongzhi);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        KaoshitongzhiEntity kaoshitongzhi = kaoshitongzhiService.selectById(id);
        return R.ok().put("data", kaoshitongzhi);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody KaoshitongzhiEntity kaoshitongzhi, HttpServletRequest request){
    	kaoshitongzhi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(kaoshitongzhi);

        kaoshitongzhiService.insert(kaoshitongzhi);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody KaoshitongzhiEntity kaoshitongzhi, HttpServletRequest request){
    	kaoshitongzhi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(kaoshitongzhi);

        kaoshitongzhiService.insert(kaoshitongzhi);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody KaoshitongzhiEntity kaoshitongzhi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(kaoshitongzhi);
        kaoshitongzhiService.updateById(kaoshitongzhi);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        kaoshitongzhiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<KaoshitongzhiEntity> wrapper = new EntityWrapper<KaoshitongzhiEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("xuesheng")) {
			wrapper.eq("xuehao", (String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("jiaoshi")) {
			wrapper.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
		}

		int count = kaoshitongzhiService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	







}
