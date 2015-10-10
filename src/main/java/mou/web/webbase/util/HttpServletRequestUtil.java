package mou.web.webbase.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.JsonUtil;
import org.mou.common.StringUtil;

public class HttpServletRequestUtil {

	private static final Logger logger = LogManager.getLogger(HttpServletRequestUtil.class);

	/****
	 * 取参数的trim后的值
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getTrimParameter(HttpServletRequest request, String key) {

		String value = request.getParameter(key);

		if (StringUtil.isNotEmpty(value)) {
			value = value.trim();
		}

		return value;
	}

	public static void debugParams(HttpServletRequest request) {

		logger.debug("-----------<params>----------");

		Enumeration<String> paramNames = request.getParameterNames();

		while (paramNames.hasMoreElements()) {

			String key = (String) paramNames.nextElement();

			String[] values = request.getParameterValues(key);

			if (values.length > 1) {
				logger.debug(key + "-->" + JsonUtil.toJsonStr(values));
			} else {
				logger.debug(key + "-->[" + values[0] + "]");
			}
		}

		logger.debug("----------</params>----------");
	}

	public static String getParams(HttpServletRequest request) {

		StringBuilder sb = new StringBuilder();

		Enumeration<String> paramNames = request.getParameterNames();

		while (paramNames.hasMoreElements()) {

			String key = (String) paramNames.nextElement();

			String[] values = request.getParameterValues(key);

			if (values.length > 1) {
				sb.append(key + "-->" + JsonUtil.toJsonStr(values)).append("&");
			} else {
				sb.append(key + "-->[" + values[0] + "]").append("&");
			}
		}

		return sb.toString();
	}

	/****
	 * 清除所有session缓存
	 * 
	 * @param request
	 */
	public static void clearAllSessionAttributes(HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (session == null) {
			return;
		}

		Enumeration<String> attributes = session.getAttributeNames();

		while (attributes.hasMoreElements()) {

			String key = (String) attributes.nextElement();
			session.removeAttribute(key);
		}
	}

	/****
	 * 取所有的session缓存属性
	 * 
	 * @param request
	 * @return
	 */
	public static String getAllSessionAttributes(HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (session == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		Enumeration<String> attributes = session.getAttributeNames();

		while (attributes.hasMoreElements()) {

			String key = (String) attributes.nextElement();
			Object value = session.getAttribute(key);

			sb.append(key).append("-->[").append(JsonUtil.toJsonStr(value)).append("]\n");
		}

		return sb.toString();
	}

	/****
	 * 获取uri和controller<br>
	 * [uri,controller,controllerhead]<br>
	 * 样例：<br>
	 * ["/WEBNIU2/dpt/toDetail.do?123213210i\u003d1fioafewi?jfoeajo",
	 * "dpt/toDetail.do","dpt"]
	 * 
	 * @param request
	 * @return
	 */
	public static String[] getUriController(HttpServletRequest request) {
		String[] rtnArray = new String[3];

		String uri = request.getRequestURI();
		String ctx = request.getContextPath();

		String controller = null;
		int idx_start = ctx.length() + 1;
		int idx_end = uri.indexOf("?");

		if (idx_end > 0) {
			controller = uri.substring(idx_start, idx_end);
		} else {
			controller = uri.substring(idx_start);
		}

		String action_head = controller;
		int idx_head = controller.indexOf("/");

		if (idx_head > 0) {
			action_head = controller.substring(0, idx_head);
		}

		rtnArray[0] = uri;
		rtnArray[1] = controller;
		rtnArray[2] = action_head;
		return rtnArray;
	}

	/****
	 * 获取远程客户端的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getRemoteInf(HttpServletRequest request) {

		Map<String, String> resultMap = new HashMap<String, String>();

		String agent = request.getHeader("user-agent");
		logger.info("useragent == null!");
		logger.info("agent\n{}", agent);
		UserAgent useragent = UserAgentUtil.getUserAgent(agent);

		if (useragent == null) {
			logger.info("useragent == null!");
			logger.info("agent\n{}", agent);
		}

		if (useragent != null && StringUtil.isNotEmpty(useragent.getBrowserType())) {
			resultMap.put("userbrowser", useragent.getBrowserType());
			resultMap.put("userbrowser_version", useragent.getBrowserVersion());
			resultMap.put("useros", useragent.getPlate_type_series());
			resultMap.put("useragent", agent);
		} else {
			resultMap.put("userbrowser", "");
		}

		// resultMap.put("userip", getClientIp(request));

		return resultMap;
	}
}
