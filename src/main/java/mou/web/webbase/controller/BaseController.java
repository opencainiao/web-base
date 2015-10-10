package mou.web.webbase.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.mou.common.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import com.mongodb.DBObject;
import com.mou.mongodb.base.domain.PageVO;

import mou.web.webbase.domain.RequestResult;
import mou.web.webbase.domain.ValidResult;
import mou.web.webbase.domain.exception.ValidateException;
import mou.web.webbase.handler.ErrorHandler;
import mou.web.webbase.handler.PageSearchResultHandler;
import mou.web.webbase.util.ValidateUtil;

public class BaseController {

	@Autowired
	private Validator validator;

	/****
	 * 基于JSR301校验对象<br>
	 * 
	 * 样例：<br>
	 * 
	 * <PRE>
	 * ValidResult validResult = this.validate(XXX);
	 * if (validResult.hasErrors()) {
	 * 	return this.handleValidateFalse(validResult);
	 * }
	 * </PRE>
	 * 
	 * @param source
	 * @return
	 */
	public ValidResult validate(Object source) {

		ValidResult validresult = new ValidResult();

		Set<ConstraintViolation<Object>> violations = validator
				.validate(source);

		if (violations != null && violations.size() > 0) {

			Map<String, String> errors = new HashMap<String, String>();

			for (ConstraintViolation<Object> violation : violations) {
				// 这部分是获取验证的出错的字段名
				String field = violation.getPropertyPath().toString();
				String message = violation.getMessage();

				errors.put(field, message);
			}

			validresult.setErrors(errors);
		}

		return validresult;
	}

	/****
	 * 判断是否合法的objectId
	 * 
	 * @param _id
	 * @return
	 */
	public boolean isValidObjId(String _id) {
		return ValidateUtil.isValidObjId(_id);
	}

	public RequestResult handleException(Exception e) {

		e.printStackTrace();

		if (e instanceof ValidateException) {
			return ((ValidateException) e).getRequestResult();
		} else {
			RequestResult rr = new RequestResult();
			rr.setSuccess(false);
			rr.setMessage(StringUtil.getStackTrace(e));

			return rr;
		}
	}

	/****
	 * 对校验不合法的情况进行处理，生成对应的返回对象
	 * 
	 * @param message
	 * @return
	 */
	public RequestResult handleValidateFalse(String message) {

		RequestResult rr = new RequestResult();
		rr.setSuccess(false);
		rr.setMessage(message);

		return rr;
	}

	/****
	 * 对校验不合法的情况进行处理，生成对应的返回对象
	 * 
	 * @param message
	 * @return
	 */
	public RequestResult handleValidateFalse(BindingResult br) {

		return ErrorHandler.getRequestResultFromBindingResult(br);
	}
	
	/****
	 * 对校验不合法的情况进行处理，生成对应的返回对象
	 * 
	 * @param message
	 * @return
	 */
	public RequestResult handleValidateFalse(ValidResult validResult) {

		return ErrorHandler.getRequestResultFromValidResult(validResult);
	}

	/****
	 * 处理不分页的查询情况
	 * 
	 * @param list
	 * @return
	 */
	public PageVO handleOnePageList(List<DBObject> list) {
		int num = 0;

		if (list == null || list.size() == 0) {
			num = 0;
		} else {
			num = list.size();
		}

		return PageSearchResultHandler.handleDBObjList(list, 1, num, num);

	}
}
