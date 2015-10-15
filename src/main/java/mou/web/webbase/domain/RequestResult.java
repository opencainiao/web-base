package mou.web.webbase.domain;

import java.util.List;
import java.util.Map;

import org.mou.common.JsonUtil;

import com.mongodb.DBObject;

/****
 * web请求的通用返回结果对象
 * 
 * @author sks
 *
 */
@SuppressWarnings("rawtypes")
public class RequestResult {

	private String success; // y-成功,n-失败
	private String message; // 消息
	private String _id_m; // insert成功后的id字符串表示
	private Map<String, String> brErrors;
	private Object object;// 业务对象
	private List objects; // 一组业务对象

	public String getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		if (success) {
			this.success = "y";
		} else {
			this.success = "n";
		}
	}

	public Map<String, String> getErrors() {
		return brErrors;
	}

	public void setErrors(Map<String, String> errors) {
		this.brErrors = errors;
		this.success = "n";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {

		if (object instanceof DBObject) {
			((DBObject) object).put("_id_m", ((DBObject) object).get("_id").toString());
		}

		this.object = object;
	}

	public List getObjects() {
		return objects;
	}

	public void setObjects(List objects) {

		if (objects != null && !objects.isEmpty()) {
			for (Object obj : objects) {
				if (obj instanceof DBObject) {
					((DBObject) obj).put("_id_m", ((DBObject) obj).get("_id").toString());
				}
			}
		}

		this.objects = objects;
	}

	public String get_id_m() {
		return _id_m;
	}

	public void set_id_m(String _id_m) {
		this._id_m = _id_m;
	}

	@Override
	public String toString() {
		return JsonUtil.toJsonStr(this);
	}
}
