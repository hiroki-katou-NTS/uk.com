/**
 *
 */
package nts.uk.screen.at.app.query.kdp.kdp003.f;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import nts.uk.ctx.sys.gateway.app.command.loginkdp.TimeStampInputLoginDto;
import nts.uk.ctx.sys.gateway.app.command.loginkdp.TimeStampLoginCommand;
import nts.uk.ctx.sys.gateway.app.command.loginkdp.TimeStampLoginCommandHandler;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv 打刻管理者でログインする Screen 4
 *
 */
@Stateless
public class LoginAsEmbossingAdministrator {

	@Inject
	private TimeStampLoginCommandHandler timeStampLoginCommandHandler;

	public TimeStampInputLoginDto loginAsEmbossingAdmin(String cid, String scd, String passWord, String companyCode,
			boolean isPasswordInvalid, boolean isAdminMode, boolean runtimeEnvironmentCreate,
			@Context HttpServletRequest request) {
		// runtimeEnvironmentCreat = true;

		// note: アルゴリズム「打刻入力ログイン」を実行する
		TimeStampLoginCommand command = new TimeStampLoginCommand();
		command.setContractCode(AppContexts.user().contractCode());
		command.setCompanyId(cid);
		command.setCompanyCode(companyCode);
		command.setEmployeeCode(scd);
		command.setPassword(passWord);
		command.setPasswordInvalid(isPasswordInvalid);
		command.setAdminMode(isAdminMode);
		command.setRuntimeEnvironmentCreate(runtimeEnvironmentCreate);
		command.setRequest(request);

		return this.timeStampLoginCommandHandler.handle(command);
	}
}
