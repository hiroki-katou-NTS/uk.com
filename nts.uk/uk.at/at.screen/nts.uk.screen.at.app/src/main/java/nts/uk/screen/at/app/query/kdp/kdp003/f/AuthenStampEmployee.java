/**
 * 
 */
package nts.uk.screen.at.app.query.kdp.kdp003.f;

import java.util.Optional;

import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;

/**
 * @author laitv
 * 打刻入力社員の認証のみを行う
 *
 */
public class AuthenStampEmployee {
	
	private IPersonInfoPub personInfoPub;
	
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
	public AuthenStampEmployeeDto authenticateStampedEmployees(String cid, Optional<String> scd, Optional<String> sid, Optional<String> passWord,
			Boolean passwordInvalid, Boolean isAdminMode, Boolean runtimeEnvironmentCreat){
		if (sid.isPresent()) {
			// Imported（GateWay）「社員」を取得する
			PersonInfoExport personInfo =  personInfoPub.getPersonInfo(sid.get()); 
			
			
			return null;
		}
		
		return null;
	}

}
