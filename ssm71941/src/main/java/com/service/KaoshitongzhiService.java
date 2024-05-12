package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.KaoshitongzhiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.KaoshitongzhiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.KaoshitongzhiView;


/**
 * 考试通知
 *
 * @author 
 * @email 
 * @date 2022-05-25 10:37:24
 */
public interface KaoshitongzhiService extends IService<KaoshitongzhiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<KaoshitongzhiVO> selectListVO(Wrapper<KaoshitongzhiEntity> wrapper);
   	
   	KaoshitongzhiVO selectVO(@Param("ew") Wrapper<KaoshitongzhiEntity> wrapper);
   	
   	List<KaoshitongzhiView> selectListView(Wrapper<KaoshitongzhiEntity> wrapper);
   	
   	KaoshitongzhiView selectView(@Param("ew") Wrapper<KaoshitongzhiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<KaoshitongzhiEntity> wrapper);
   	

}

