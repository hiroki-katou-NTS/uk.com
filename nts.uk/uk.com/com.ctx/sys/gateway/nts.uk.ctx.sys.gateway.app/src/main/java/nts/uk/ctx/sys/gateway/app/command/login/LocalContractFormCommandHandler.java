/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.app.command.login.dto.CheckContractDto;
import nts.uk.ctx.sys.gateway.dom.login.Contract;
import nts.uk.ctx.sys.gateway.dom.login.ContractRepository;
import nts.uk.ctx.sys.gateway.dom.login.InstallForm;
import nts.uk.ctx.sys.gateway.dom.login.Period;
import nts.uk.ctx.sys.gateway.dom.login.SystemConfig;
import nts.uk.ctx.sys.gateway.dom.login.SystemConfigRepository;

/**
 * The Class LocalContractFormCommandHandler.
 */
@Stateless
public class LocalContractFormCommandHandler
		extends CommandHandlerWithResult<LocalContractFormCommand, CheckContractDto> {

	/** The system config repository. */
	@Inject
	SystemConfigRepository systemConfigRepository;

	/** The contract repository. */
	@Inject
	ContractRepository contractRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandlerWithResult#handle(nts.arc.layer.
	 * app.command.CommandHandlerContext)
	 */
	@Override
	protected CheckContractDto handle(CommandHandlerContext<LocalContractFormCommand> context) {
		LocalContractFormCommand command = context.getCommand();

		SystemConfig systemConfig = this.getSystemConfig();
		// case Cloud
		if (systemConfig.getInstallForm().value == InstallForm.Cloud.value) {
			if (this.isShowContract(command)) {
				return new CheckContractDto(true);
			} else {
				return new CheckContractDto(false);
			}
		}
		// case OnPre
		else {
			return new CheckContractDto(false);
		}
	}

	/**
	 * Checks if is show contract.
	 *
	 * @param command
	 *            the command
	 * @return true, if is show contract
	 */
	private boolean isShowContract(LocalContractFormCommand command) {
		// get contract info
		String contractCode = command.getContractCode();
		String contractPassword = command.getContractPassword();

		if (contractCode.isEmpty() ||contractCode == null) {
			return true;
		}
		// get domain contract
		Optional<Contract> contract = contractRepository.getContract(contractCode);
		if (!contract.isPresent()) {
			return true;
		}
		// compare contract pass
		if (!contract.get().getPassword().v().equals(contractPassword)) {
			return true;
		}
		// check time limit
		if (this.contractPeriodInvalid(contract.get())) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the system config.
	 *
	 * @return the system config
	 */
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
	 * Contract period invalid.
	 *
	 * @param contract
	 *            the contract
	 * @return true, if successful
	 */
	private boolean contractPeriodInvalid(Contract contract) {
		Period period = contract.getContractPeriod();
		GeneralDate currentDate = GeneralDate.today();
		return !(period.getStartDate().before(currentDate) && period.getEndDate().after(currentDate));
	}
}
