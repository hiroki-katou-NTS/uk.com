package nts.uk.ctx.sys.gateway.app.command.login.url;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@Data
public class UrlLoginCommand implements LoginCommandHandlerBase.TenantAuth {
    private String urlId;
    private String tenantCode;
    private String tenantPasswordPlainText;
    private HttpServletRequest request;
}
