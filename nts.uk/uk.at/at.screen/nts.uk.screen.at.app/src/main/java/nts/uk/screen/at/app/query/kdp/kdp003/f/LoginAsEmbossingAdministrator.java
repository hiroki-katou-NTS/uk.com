/**
 * 
 */
package nts.uk.screen.at.app.query.kdp.kdp003.f;

import java.util.Optional;

import nts.uk.ctx.sys.gateway.app.command.loginkdp.TimeStampInputLoginCommand;

/**
 * @author laitv 打刻管理者でログインする Screen 4
 *
 */
public class LoginAsEmbossingAdministrator {

	public void loginAsEmbossingAdmin(String cid, String scd, String passWord, Boolean isAdminMode,
			Boolean runtimeEnvironmentCreat) {

		if(runtimeEnvironmentCreat){
			
			
			
		}

		// アルゴリズム「打刻入力ログイン」を実行する
		TimeStampInputLoginCommand input = new TimeStampInputLoginCommand();
		input.setCompanyId(cid);
		input.setEmployeeCode(scd);
		input.setPw(Optional.of(passWord));
		input.setPasswordInvalid(passwordInvalid);
		input.setIsAdminMode(isAdminMode);
		input.setRuntimeEnvironmentCreat(runtimeEnvironmentCreat);

		result = timeStampInputLoginAlg.handle(input);

	}
}
