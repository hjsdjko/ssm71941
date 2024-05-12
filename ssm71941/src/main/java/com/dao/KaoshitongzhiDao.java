package com.dao;

import com.entity.KaoshitongzhiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.KaoshitongzhiVO;
import com.entity.view.KaoshitongzhiView;


/**
 * 考试通知
 * 
 * @author 
 * @email 
 * @date 2022-05-25 10:37:24
 */
public interface KaoshitongzhiDao extends BaseMapper<KaoshitongzhiEntity> {
	
	List<KaoshitongzhiVO> selectListVO(@Param("ew") Wrapper<KaoshitongzhiEntity> wrapper);
	
	KaoshitongzhiVO selectVO(@Param("ew") Wrapper<KaoshitongzhiEntity> wrapper);
	
	List<KaoshitongzhiView> selectListView(@Param("ew") Wrapper<KaoshitongzhiEntity> wrapper);

	List<KaoshitongzhiView> selectListView(Pagination page,@Param("ew") Wrapper<KaoshitongzhiEntity> wrapper);
	
	KaoshitongzhiView selectView(@Param("ew") Wrapper<KaoshitongzhiEntity> wrapper);
	

}
