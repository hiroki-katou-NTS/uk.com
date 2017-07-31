/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.app.find.login.dto.CheckContractDto;
import nts.uk.ctx.sys.gateway.dom.login.Contract;
import nts.uk.ctx.sys.gateway.dom.login.ContractRepository;
import nts.uk.ctx.sys.gateway.dom.login.Period;
import nts.uk.ctx.sys.gateway.dom.login.SystemConfig;
import nts.uk.ctx.sys.gateway.dom.login.SystemConfigRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class LoginFinder.
 */
@Stateless
public class LoginFormOneFinder {

	/** The system config repository. */
	@Inject
	SystemConfigRepository systemConfigRepository;

	/** The contract repository. */
	@Inject
	ContractRepository contractRepository;

	public CheckContractDto getStartStatus() {
		String savedContractPassword = "TODO";// get from client
		SystemConfig systemConfig = this.getSystemConfig();
		// case Cloud
//		if (systemConfig.getInstallForm().value == InstallForm.Cloud.value) {
//			if (this.isShowContract(savedContractPassword)) {
//				return "show contract";
//			} else {
//				return "not show contract";
//			}
//		}
//		// case OnPre
//		else {
//			return "not show contract";
//		}
		return new CheckContractDto("show contract");
	}

	private boolean isShowContract(String savedContractPassword) {
		// get contract info
		String contractCode = AppContexts.user().contractCode();
		if (contractCode == null) {
			return true;
		}
		// get domain contract
		Optional<Contract> contract = contractRepository.getContract();
		if (!contract.isPresent()) {
			return true;
		}
		// compare contract pass
		if (!contract.get().getPassword().equals(savedContractPassword)) {
			return true;
		}
		// check time limit
		if (this.contractPeriodInvalid(contract.get())) {
			return true;
		}
		return false;
	}

	private SystemConfig getSystemConfig() {
		Optional<SystemConfig> systemConfig = systemConfigRepository.getSystemConfig();
		if (systemConfig.isPresent()) {
			return systemConfig.get();
		} else {
			// TODO go to system error screen
			return null;
		}
	}

	/**
	 * Check contract period.
	 *
	 * @param period
	 *            the period
	 * @return true, if successful
	 */
	private boolean contractPeriodInvalid(Contract contract) {
		Period period = contract.getContractPeriod();
		GeneralDate currentDate = GeneralDate.today();
		return period.getStartDate().before(currentDate) && period.getEndDate().after(currentDate);
	}

}
