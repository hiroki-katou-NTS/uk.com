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

@Stateless
public class EmployeeLoginSettingFinder {

	@Inject
	EmployeeLoginSettingRepository employeeLoginSettingRepository;

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
