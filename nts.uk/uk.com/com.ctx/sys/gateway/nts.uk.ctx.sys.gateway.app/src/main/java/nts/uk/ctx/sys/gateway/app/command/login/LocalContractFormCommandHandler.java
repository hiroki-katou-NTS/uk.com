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
import nts.gul.security.hash.password.PasswordHash;
import nts.uk.ctx.sys.gateway.app.command.login.dto.CheckContractDto;
import nts.uk.ctx.sys.gateway.dom.login.Contract;
import nts.uk.ctx.sys.gateway.dom.login.ContractRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.system.config.InstallationType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class LocalContractFormCommandHandler.
 */
@Stateless
public class LocalContractFormCommandHandler
		extends CommandHandlerWithResult<LocalContractFormCommand, CheckContractDto> {

	/** The contract repository. */
	@Inject
	private ContractRepository contractRepository;

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
		try {
//			SystemConfig systemConfig = this.getSystemConfig();
			InstallationType systemConfig = AppContexts.system().getInstallationType();

			// case Cloud
			if (systemConfig.value == InstallationType.CLOUD.value) {
				if (this.isShowContract(command)) {
					return new CheckContractDto(true,false);
				}
				return new CheckContractDto(false,false);
			}
			// case OnPre
			return new CheckContractDto(false,true);
		} catch (Exception e) {
			throw new RuntimeException(e);
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

		if (contractCode == null || contractCode.isEmpty()) {
			return true;
		}
		// get domain contract
		Optional<Contract> contract = contractRepository.getContract(contractCode);
		if (!contract.isPresent()) {
			return true;
		}
		// compare contract pass
		if ((contractPassword==null)|| !PasswordHash.verifyThat(contractPassword, contractCode).isEqualTo(contract.get().getPassword().v())) {
			return true;
		}
		// check time limit
		if (this.contractPeriodInvalid(contract.get())) {
			return true;
		}
		return false;
	}

	/**
	 * Contract period invalid.
	 *
	 * @param contract
	 *            the contract
	 * @return true, if successful
	 */
	private boolean contractPeriodInvalid(Contract contract) {
		DatePeriod period = contract.getContractPeriod();
		GeneralDate currentDate = GeneralDate.today();
		return !(period.start().beforeOrEquals(currentDate) && period.end().afterOrEquals(currentDate));
	}
}
