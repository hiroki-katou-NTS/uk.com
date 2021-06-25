/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.login;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.gateway.app.find.login.dto.EmployeeLoginSettingDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.system.config.InstallationType;

/**
 * The Class EmployeeLoginSettingFinder.
 */
@Stateless
public class EmployeeLoginSettingFinder {

	/**
	 * Find by contract code form 2.
	 *
	 * @param contractCode the contract code
	 * @return the employee login setting dto
	 */
	public EmployeeLoginSettingDto findByContractCodeForm2(String contractCode) {
		//ログイン画面の１と２は当面使わないためfalseを固定で返す
		return new EmployeeLoginSettingDto(false);
	}
	
	/**
	 * Find by contract code form 3.
	 *
	 * @param contractCode the contract code
	 * @return the employee login setting dto
	 */
	public EmployeeLoginSettingDto findByContractCodeForm3(String contractCode) {
		//ログイン画面の１と２は当面使わないためfalseを固定で返す
		return new EmployeeLoginSettingDto(false);
	}
	
	public boolean isOnPre() {
		InstallationType systemConfig = AppContexts.system().getInstallationType();
		if (systemConfig.equals(InstallationType.ON_PREMISES)) {
			return true;
		}
		return false;
	}
}
