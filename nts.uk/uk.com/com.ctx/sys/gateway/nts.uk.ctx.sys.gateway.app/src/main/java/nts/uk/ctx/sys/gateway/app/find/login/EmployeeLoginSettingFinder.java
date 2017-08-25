/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.app.find.login.dto.EmployeeLoginSettingDto;
import nts.uk.ctx.sys.gateway.dom.login.EmployeeLoginSetting;
import nts.uk.ctx.sys.gateway.dom.login.EmployeeLoginSettingRepository;
import nts.uk.ctx.sys.gateway.dom.login.ManageDistinct;

/**
 * The Class EmployeeLoginSettingFinder.
 */
@Stateless
public class EmployeeLoginSettingFinder {

	/** The employee login setting repository. */
	@Inject
	EmployeeLoginSettingRepository employeeLoginSettingRepository;

	/**
	 * Find by contract code form 2.
	 *
	 * @param contractCode the contract code
	 * @return the employee login setting dto
	 */
	public EmployeeLoginSettingDto findByContractCodeForm2(String contractCode) {

		Optional<EmployeeLoginSetting> emLogSetting = employeeLoginSettingRepository.getByContractCode(contractCode);
		if (emLogSetting.isPresent()) {

			if (emLogSetting.get().getForm2PermitAtr().value == ManageDistinct.YES.value) {
				return new EmployeeLoginSettingDto(false);
			} else {
				return new EmployeeLoginSettingDto(true);
			}
		} else {// goto form 1
			return new EmployeeLoginSettingDto(true);
		}
	}
	
	/**
	 * Find by contract code form 3.
	 *
	 * @param contractCode the contract code
	 * @return the employee login setting dto
	 */
	public EmployeeLoginSettingDto findByContractCodeForm3(String contractCode) {

		Optional<EmployeeLoginSetting> emLogSetting = employeeLoginSettingRepository.getByContractCode(contractCode);
		if (emLogSetting.isPresent()) {

			if (emLogSetting.get().getForm3PermitAtr().value == ManageDistinct.YES.value) {
				return new EmployeeLoginSettingDto(false);
			} else {
				return new EmployeeLoginSettingDto(true);
			}
		} else {// goto form 1
			return new EmployeeLoginSettingDto(true);
		}
	}
}
