/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.security.hash.password.PasswordHash;
import nts.uk.ctx.sys.gateway.dom.login.Contract;
import nts.uk.ctx.sys.gateway.dom.login.ContractRepository;

/**
 * The Class SubmitContractFormCommandHandler.
 */
@Stateless
public class SubmitContractFormCommandHandler extends CommandHandler<SubmitContractFormCommand> {

	/** The contract repository. */
	@Inject
	private ContractRepository contractRepository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SubmitContractFormCommand> context) {
		SubmitContractFormCommand command = context.getCommand();
		String contractCode = command.getContractCode();
		String password = command.getPassword();
		// pre check
		this.checkInput(command);
		// contract auth
		this.contractAccAuth(contractCode, password);
	}

	/**
	 * Contract acc auth.
	 *
	 * @param contractCode the contract code
	 * @param password the password
	 */
	private void contractAccAuth(String contractCode, String password) {
		Optional<Contract> contract = contractRepository.getContract(contractCode);
		if (contract.isPresent()) {
			this.checkPassword(contract, password);
			this.checkTime(contract);
		} else {
			throw new BusinessException("Msg_314");
		}
	}

	/**
	 * Check time.
	 *
	 * @param contract the contract
	 */
	private void checkTime(Optional<Contract> contract) {
		if (contract.get().getContractPeriod().start().after(GeneralDate.today())
				|| contract.get().getContractPeriod().end().before(GeneralDate.today())) {
			throw new BusinessException("Msg_315");
		}
	}

	/**
	 * Check password.
	 *
	 * @param contract the contract
	 * @param password the password
	 */
	private void checkPassword(Optional<Contract> contract, String password) {
		if (!PasswordHash.verifyThat(password, contract.get().getContractCode().v()).isEqualTo(contract.get().getPassword().v())) {
			throw new BusinessException("Msg_302");
		}
	}

	/**
	 * Check input.
	 *
	 * @param command the command
	 */
	private void checkInput(SubmitContractFormCommand command) {
		if (command.getContractCode() == null || command.getContractCode().isEmpty()) {
			throw new BusinessException("Msg_313");
		}
		if (command.getPassword() == null || command.getPassword().isEmpty()) {
			throw new BusinessException("Msg_310");
		}
	}

}
