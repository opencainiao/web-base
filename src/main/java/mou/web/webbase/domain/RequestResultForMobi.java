package mou.web.webbase.domain;

/****
 * 移动请求的返回对象
 * 
 * @author NBQ
 *
 */
public class RequestResultForMobi extends RequestResult {
	private int success_num; // 成功条数
	private int failure_num; // 失败条数

	public int getSuccess_num() {
		return success_num;
	}

	public void setSuccess_num(int success_num) {
		this.success_num = success_num;
	}

	public int getFailure_num() {
		return failure_num;
	}

	public void setFailure_num(int failure_num) {
		this.failure_num = failure_num;
	}

}
