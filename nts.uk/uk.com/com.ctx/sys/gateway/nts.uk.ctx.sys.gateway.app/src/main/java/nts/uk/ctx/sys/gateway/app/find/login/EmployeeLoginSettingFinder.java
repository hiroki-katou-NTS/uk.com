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
import nts.uk.ctx.sys.gateway.dom.login.InstallForm;
import nts.uk.ctx.sys.gateway.dom.login.ManageDistinct;
import nts.uk.ctx.sys.gateway.dom.login.SystemConfig;
import nts.uk.ctx.sys.gateway.dom.login.SystemConfigRepository;

/**
 * The Class EmployeeLoginSettingFinder.
 */
@Stateless
public class EmployeeLoginSettingFinder {

	/** The employee login setting repository. */
	@Inject
	private EmployeeLoginSettingRepository employeeLoginSettingRepository;

	/** The system config repository. */
	@Inject
	private SystemConfigRepository systemConfigRepository;
	
	/** The Constant FIXED_CONTRACTCODE. */
	private static final String FIXED_CONTRACTCODE = "000000000000";

	/**
	 * Find by contract code form 2.
	 *
	 * @param contractCode the contract code
	 * @return the employee login setting dto
	 */
	public EmployeeLoginSettingDto findByContractCodeForm2(String contractCode) {
		if (this.isOnPre()) {
			contractCode = FIXED_CONTRACTCODE;
		}
		Optional<EmployeeLoginSetting> emLogSetting = employeeLoginSettingRepository.getByContractCode(contractCode);
		if (emLogSetting.isPresent()) {

			// if have permit view form 2
			if (emLogSetting.get().getForm2PermitAtr().value == ManageDistinct.YES.value) {
				return new EmployeeLoginSettingDto(false);
			}
			return new EmployeeLoginSettingDto(true);
		}
		// goto form 1
		return new EmployeeLoginSettingDto(true);
	}
	
	/**
	 * Find by contract code form 3.
	 *
	 * @param contractCode the contract code
	 * @return the employee login setting dto
	 */
	public EmployeeLoginSettingDto findByContractCodeForm3(String contractCode) {
		if (this.isOnPre()) {
			contractCode = FIXED_CONTRACTCODE;
		}
		Optional<EmployeeLoginSetting> emLogSetting = employeeLoginSettingRepository.getByContractCode(contractCode);
		if (emLogSetting.isPresent()) {
			//if have permit view form 3
			if (emLogSetting.get().getForm3PermitAtr().value == ManageDistinct.YES.value) {
				return new EmployeeLoginSettingDto(false);
			}
			return new EmployeeLoginSettingDto(true);
		}
		// goto form 1
		return new EmployeeLoginSettingDto(true);
	}
	
	public boolean isOnPre()
	{
		Optional<SystemConfig> sysConfig = this.systemConfigRepository.getSystemConfig();
		if(sysConfig.isPresent())
		{
			if (sysConfig.get().getInstallForm().equals(InstallForm.OnPre)) {
				return true;
			}
		}
		return false;
	}
}
