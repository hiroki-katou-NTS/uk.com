/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.login;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.gateway.app.find.login.dto.EmployeeLoginSettingDto;

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
		// ログイン画面の1と2は当面使わない
		return new EmployeeLoginSettingDto(false);
	}
	
	/**
	 * Find by contract code form 3.
	 *
	 * @param contractCode the contract code
	 * @return the employee login setting dto
	 */
	public EmployeeLoginSettingDto findByContractCodeForm3(String contractCode) {
		// ログイン画面の1と2は当面使わない
		return new EmployeeLoginSettingDto(false);
	}
}
