package org.ibase4j.web.sys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ibase4j.core.config.Resources;
import org.ibase4j.core.listener.SessionListener;
import org.ibase4j.core.util.WebUtil;
import org.ibase4j.facade.sys.SysSessionFacade;
import org.ibase4j.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

/**
 * 用户会话管理
 */
@Controller
@RequestMapping("/session")
public class SysSessionController extends BaseController {
	@Reference
	private SysSessionFacade sysSessionFacade;

	// 查询会话
	@ResponseBody
	@RequestMapping(value = "/read/list")
	public ModelMap get(ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> params = WebUtil.getParameterMap(request);
		PageInfo<?> list = sysSessionFacade.query(params);
		Long number = SessionListener.getAllUserNumber();
		modelMap.put("userNumber", number); // 用户数大于会话数,有用户没有登录
		return setSuccessModelMap(modelMap, list);
	}

	// 删除会话
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelMap update(HttpServletRequest request, ModelMap modelMap,
			@RequestParam(value = "id", required = false) Integer id) {
		Assert.notNull(id, Resources.getMessage("ID_IS_NULL"));
		sysSessionFacade.delete(id);
		return setSuccessModelMap(modelMap);
	}
}
