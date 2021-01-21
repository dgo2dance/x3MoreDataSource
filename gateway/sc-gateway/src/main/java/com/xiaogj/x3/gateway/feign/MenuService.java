package com.xiaogj.x3.gateway.feign;

import com.xiaogj.x3.gateway.feign.fallback.MenuServiceFallbackFactory;
import com.xiaogj.x3.common.constant.ServiceNameConstants;
import com.xiaogj.x3.common.model.SysMenu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zlt
 */
@FeignClient(name = ServiceNameConstants.USER_SERVICE, fallbackFactory = MenuServiceFallbackFactory.class, decode404 = true)
public interface MenuService {
	/**
	 * 角色菜单列表
	 * @param roleCodes
	 */
	@GetMapping(value = "/menus/{roleCodes}")
	List<SysMenu> findByRoleCodes(@PathVariable("roleCodes") String roleCodes);
}
