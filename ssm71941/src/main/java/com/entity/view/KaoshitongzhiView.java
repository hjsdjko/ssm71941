package com.entity.view;

import com.entity.KaoshitongzhiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * 考试通知
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2022-05-25 10:37:24
 */
@TableName("kaoshitongzhi")
public class KaoshitongzhiView  extends KaoshitongzhiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public KaoshitongzhiView(){
	}
 
 	public KaoshitongzhiView(KaoshitongzhiEntity kaoshitongzhiEntity){
 	try {
			BeanUtils.copyProperties(this, kaoshitongzhiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
