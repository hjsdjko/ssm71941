package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.KaoshitongzhiDao;
import com.entity.KaoshitongzhiEntity;
import com.service.KaoshitongzhiService;
import com.entity.vo.KaoshitongzhiVO;
import com.entity.view.KaoshitongzhiView;

@Service("kaoshitongzhiService")
public class KaoshitongzhiServiceImpl extends ServiceImpl<KaoshitongzhiDao, KaoshitongzhiEntity> implements KaoshitongzhiService {
	

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<KaoshitongzhiEntity> page = this.selectPage(
                new Query<KaoshitongzhiEntity>(params).getPage(),
                new EntityWrapper<KaoshitongzhiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<KaoshitongzhiEntity> wrapper) {
		  Page<KaoshitongzhiView> page =new Query<KaoshitongzhiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
    @Override
	public List<KaoshitongzhiVO> selectListVO(Wrapper<KaoshitongzhiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public KaoshitongzhiVO selectVO(Wrapper<KaoshitongzhiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<KaoshitongzhiView> selectListView(Wrapper<KaoshitongzhiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public KaoshitongzhiView selectView(Wrapper<KaoshitongzhiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
