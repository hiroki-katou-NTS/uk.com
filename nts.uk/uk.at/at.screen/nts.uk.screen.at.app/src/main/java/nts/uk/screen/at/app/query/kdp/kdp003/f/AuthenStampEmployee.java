/**
 * 
 */
package nts.uk.screen.at.app.query.kdp.kdp003.f;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.ctx.sys.gateway.app.command.loginkdp.TimeStampInputLoginDto;
import nts.uk.ctx.sys.gateway.app.command.loginkdp.TimeStampLoginCommand;
import nts.uk.ctx.sys.gateway.app.command.loginkdp.TimeStampLoginCommandHandler;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * 打刻入力社員の認証のみを行う
 * Screen 3
 *
 */
@Stateless
public class AuthenStampEmployee {
	
	@Inject
	private IPersonInfoPub personInfoPub;

	@Inject
	private TimeStampLoginCommandHandler timeStampLoginCommandHandler;

	/**
	 *【input】
	  ・会社ID cid
  	  ・社員コード(Optional） scd
	  ・社員ID(Option) sid
	  ・パスワード(Optional) passWord
	  ・パスワード無効 passwordInvalid
	  ・管理者  isAdminMode
	  ・実行時環境作成 runtimeEnvironmentCreat
	  【output】 
	  ・結果(True/False)
	  ・社員
	  ・エラーメッセージ 
	 */
	public TimeStampInputLoginDto authenticateStampedEmployees(String cid, Optional<String> scd, Optional<String> sid,
			Optional<String> passWord, boolean passwordInvalid, boolean isAdminMode, boolean runtimeEnvironmentCreate,
			@Context HttpServletRequest request) {

		TimeStampInputLoginDto result = null;

		if (!scd.isPresent()) {
			// truong hop scd = null là cảu màn KDP004, KDP005. Khi đó sẽ truyền sid lên
			// Imported（GateWay）「社員」を取得する
			PersonInfoExport personInfo = personInfoPub.getPersonInfo(sid.get());
			// アルゴリズム「打刻入力ログイン」を実行する
			TimeStampLoginCommand command = new TimeStampLoginCommand();
			command.setContractCode(AppContexts.user().contractCode());
			command.setCompanyId(cid);
			command.setEmployeeCode(personInfo.getEmployeeCode());
			command.setPassword(passWord.isPresent() ? passWord.get() : null);
			command.setPasswordInvalid(passwordInvalid);
			command.setAdminMode(isAdminMode);
			command.setRuntimeEnvironmentCreate(runtimeEnvironmentCreate);
			command.setRequest(request);

			result = this.timeStampLoginCommandHandler.handle(command);
		} else if (scd.isPresent()) {
			// アルゴリズム「打刻入力ログイン」を実行する
			TimeStampLoginCommand command = new TimeStampLoginCommand();
			command.setContractCode(AppContexts.user().contractCode());
			command.setCompanyId(cid);
			command.setEmployeeCode(scd.get());
			command.setPassword(passWord.isPresent() ? passWord.get() : null);
			command.setPasswordInvalid(passwordInvalid);
			command.setAdminMode(isAdminMode);
			command.setRuntimeEnvironmentCreate(runtimeEnvironmentCreate);
			command.setRequest(request);

			result = this.timeStampLoginCommandHandler.handle(command);
		}

		return result;
	}

}
