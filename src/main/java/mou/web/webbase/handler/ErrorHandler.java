package mou.web.webbase.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import mou.web.webbase.domain.RequestResult;
import mou.web.webbase.domain.ValidResult;

/****
 * 通用错误处理器
 * 
 * @author NBQ
 *
 */
public class ErrorHandler {

	/****
	 * 提取BindingResult的错误对象信息
	 * 
	 * @param br
	 * @return
	 */
	private static Map<String, String> getErrorInfFromBindingResult(BindingResult br) {
		Map<String, String> errorNew = new HashMap<String, String>();

		List<FieldError> errors = br.getFieldErrors();
		for (FieldError error : errors) {
			errorNew.put(error.getField(), error.getDefaultMessage());
		}

		return errorNew;
	}

	/****
	 * 根据BindingResult创建RequestResult
	 * 
	 * @param br
	 * @return
	 */
	public static RequestResult getRequestResultFromBindingResult(BindingResult br) {

		Map<String, String> errors = getErrorInfFromBindingResult(br);

		RequestResult rr = new RequestResult();
		rr.setErrors(errors);

		return rr;
	}

	/****
	 * 根据validResult创建RequestResult
	 * 
	 * @param validResult
	 * @return
	 */
	public static RequestResult getRequestResultFromValidResult(ValidResult validResult) {

		Map<String, String> errors = validResult.getErrors();

		RequestResult rr = new RequestResult();
		rr.setErrors(errors);

		return rr;
	}

}
