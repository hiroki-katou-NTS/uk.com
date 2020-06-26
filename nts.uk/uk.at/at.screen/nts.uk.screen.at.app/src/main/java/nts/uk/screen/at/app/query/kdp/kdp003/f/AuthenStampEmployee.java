/**
 * 
 */
package nts.uk.screen.at.app.query.kdp.kdp003.f;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.ctx.sys.gateway.app.command.loginkdp.TimeStampInputLoginAlg;
import nts.uk.ctx.sys.gateway.app.command.loginkdp.TimeStampInputLoginCommand;
import nts.uk.ctx.sys.gateway.app.command.loginkdp.TimeStampInputLoginDto;

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
	private TimeStampInputLoginAlg timeStampInputLoginAlg;
	
	
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
	public TimeStampInputLoginDto authenticateStampedEmployees(String cid, Optional<String> scd, Optional<String> sid, Optional<String> passWord,
			Boolean passwordInvalid, Boolean isAdminMode, Boolean runtimeEnvironmentCreat){
		
		TimeStampInputLoginDto result = null;
		if (!scd.isPresent()) {
			// truong hop scd = null là cảu màn KDP004, KDP005. Khi đó sẽ truyền sid lên
			// Imported（GateWay）「社員」を取得する
			PersonInfoExport personInfo = personInfoPub.getPersonInfo(sid.get());
			// アルゴリズム「打刻入力ログイン」を実行する
			TimeStampInputLoginCommand input = new TimeStampInputLoginCommand();
			input.setCompanyId(cid);
			input.setEmployeeCode(personInfo.getEmployeeCode());
			input.setSid(sid.get());
			input.setPw(passWord);
			input.setPasswordInvalid(passwordInvalid);
			input.setIsAdminMode(isAdminMode);
			input.setRuntimeEnvironmentCreat(runtimeEnvironmentCreat);

			result = timeStampInputLoginAlg.handle(input);
			
		}else if(scd.isPresent()){
			// アルゴリズム「打刻入力ログイン」を実行する
			TimeStampInputLoginCommand input = new TimeStampInputLoginCommand();
			input.setCompanyId(cid);
			input.setEmployeeCode(scd.get());
			input.setSid(null);
			input.setPw(passWord);
			input.setPasswordInvalid(passwordInvalid);
			input.setIsAdminMode(isAdminMode);
			input.setRuntimeEnvironmentCreat(runtimeEnvironmentCreat);
			
			result = timeStampInputLoginAlg.handle(input);
		}
		
		return result;
	}

}
