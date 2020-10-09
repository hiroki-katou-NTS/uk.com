package nts.uk.ctx.sys.gateway.app.command.singlesignon.saml;

import lombok.Data;

@Data
public class ValidateInfo {
	
	private String employeeId;
	
	private String requestUrl;
	
	public ValidateInfo(String employeeId, String requestUrl) {
		this.employeeId = employeeId;
		this.requestUrl = requestUrl;
	}


}
