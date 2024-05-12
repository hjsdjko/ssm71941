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

import com.entity.KechengEntity;
import com.entity.view.KechengView;

import com.service.KechengService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;

/**
 * 课程
 * 后端接口
 * @author 
 * @email 
 * @date 2022-05-25 10:37:24
 */
@RestController
@RequestMapping("/kecheng")
public class KechengController {
    @Autowired
    private KechengService kechengService;



    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,KechengEntity kecheng, 
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date shangkeshijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date shangkeshijianend,
		HttpServletRequest request){

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaoshi")) {
			kecheng.setJiaoshigonghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<KechengEntity> ew = new EntityWrapper<KechengEntity>();
                if(shangkeshijianstart!=null) ew.ge("shangkeshijian", shangkeshijianstart);
                if(shangkeshijianend!=null) ew.le("shangkeshijian", shangkeshijianend);
		PageUtils page = kechengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, kecheng), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,KechengEntity kecheng, 
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date shangkeshijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date shangkeshijianend,
		HttpServletRequest request){
        EntityWrapper<KechengEntity> ew = new EntityWrapper<KechengEntity>();
                if(shangkeshijianstart!=null) ew.ge("shangkeshijian", shangkeshijianstart);
                if(shangkeshijianend!=null) ew.le("shangkeshijian", shangkeshijianend);
		PageUtils page = kechengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, kecheng), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( KechengEntity kecheng){
       	EntityWrapper<KechengEntity> ew = new EntityWrapper<KechengEntity>();
      	ew.allEq(MPUtil.allEQMapPre( kecheng, "kecheng")); 
        return R.ok().put("data", kechengService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(KechengEntity kecheng){
        EntityWrapper< KechengEntity> ew = new EntityWrapper< KechengEntity>();
 		ew.allEq(MPUtil.allEQMapPre( kecheng, "kecheng")); 
		KechengView kechengView =  kechengService.selectView(ew);
		return R.ok("查询课程成功").put("data", kechengView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        KechengEntity kecheng = kechengService.selectById(id);
		kecheng.setClicktime(new Date());
		kechengService.updateById(kecheng);
        return R.ok().put("data", kecheng);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        KechengEntity kecheng = kechengService.selectById(id);
		kecheng.setClicktime(new Date());
		kechengService.updateById(kecheng);
        return R.ok().put("data", kecheng);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody KechengEntity kecheng, HttpServletRequest request){
    	kecheng.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(kecheng);

        kechengService.insert(kecheng);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody KechengEntity kecheng, HttpServletRequest request){
    	kecheng.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(kecheng);

        kechengService.insert(kecheng);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody KechengEntity kecheng, HttpServletRequest request){
        //ValidatorUtils.validateEntity(kecheng);
        kechengService.updateById(kecheng);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        kechengService.deleteBatchIds(Arrays.asList(ids));
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
		
		Wrapper<KechengEntity> wrapper = new EntityWrapper<KechengEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaoshi")) {
			wrapper.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
		}

		int count = kechengService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,KechengEntity kecheng, HttpServletRequest request,String pre){
        EntityWrapper<KechengEntity> ew = new EntityWrapper<KechengEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicktime");
        
        params.put("order", "desc");
		PageUtils page = kechengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, kecheng), params), params));
        return R.ok().put("data", page);
    }







}
