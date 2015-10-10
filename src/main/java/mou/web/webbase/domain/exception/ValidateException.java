package mou.web.webbase.domain.exception;

import mou.web.webbase.domain.RequestResult;
import mou.web.webbase.domain.ValidResult;
import mou.web.webbase.handler.ErrorHandler;

public class ValidateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ValidResult validResult;

	public ValidateException(String message) {
		super(message);
	}

	public ValidateException(ValidResult validResult) {

		this.validResult = validResult;
	}

	public RequestResult getRequestResult() {
		
		if (this.validResult != null) {
			return ErrorHandler.getRequestResultFromValidResult(validResult);
		} else {
			RequestResult rr = new RequestResult();
			rr.setSuccess(false);
			rr.setMessage(this.getMessage());

			return rr;
		}
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
