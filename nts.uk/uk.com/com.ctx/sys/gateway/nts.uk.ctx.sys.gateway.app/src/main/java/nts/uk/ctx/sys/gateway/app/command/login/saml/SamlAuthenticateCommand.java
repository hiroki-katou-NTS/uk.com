package nts.uk.ctx.sys.gateway.app.command.login.saml;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;
import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import org.apache.commons.lang3.StringUtils;

@Data
public class SamlAuthenticateCommand implements LoginCommandHandlerBase.TenantAuth {

	private String tenantCode;

	private String tenantPassword;
	
	private String issueUrl;

	private String requestUrl;
	
	private HttpServletRequest request;

	@Override
	public String getTenantPasswordPlainText() {
		return this.tenantPassword;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void checkInput() {
		if (StringUtils.isEmpty(tenantCode)) {
			throw new BusinessException("Msg_313");
		}
		if (StringUtils.isEmpty(tenantPassword)) {
			throw new BusinessException("Msg_310");
		}
	}
}
