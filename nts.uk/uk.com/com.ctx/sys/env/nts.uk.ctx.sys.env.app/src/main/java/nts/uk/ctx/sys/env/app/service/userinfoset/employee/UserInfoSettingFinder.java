/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.service.userinfoset.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UserInfoSettingFinder.
 */
@Stateless
public class UserInfoSettingFinder {

	/** The use contact setting repository. */
	@Inject
	UseContactSettingRepository useContactSettingRepository;
	
	/**
	 * Find.
	 *
	 * @return the user info setting dto
	 */
	public UserInfoSettingDto find(String employeeId){
		String companyId = AppContexts.user().companyId();
		//ドメインモデル「ユーザー情報の使用方法」を取得する
		//TODO
		//ログインユーザー特別利用者か判定する
		if (this.checkSpecialUser()) {
			//取得したドメインモデル「ユーザー情報の使用方法」.本人編集を判定する
			//Msg_1177
		} else {
			//ドメインモデル「連絡先使用設定」を取得する
			UseContactSetting useContactSetting = useContactSettingRepository.findByEmployeeId(employeeId, companyId);
			//Imported(環境)「社員連絡先」を取得する
			
			//Imported(環境)「個人連絡先」を取得する
		}
		//Imported(環境)「社員」を取得する
		
		//Imported(環境)「パスワードポリシー」を取得する
		return null;
	}

	private boolean checkSpecialUser() {
		return AppContexts.user().employeeId() == null;
	}
}
