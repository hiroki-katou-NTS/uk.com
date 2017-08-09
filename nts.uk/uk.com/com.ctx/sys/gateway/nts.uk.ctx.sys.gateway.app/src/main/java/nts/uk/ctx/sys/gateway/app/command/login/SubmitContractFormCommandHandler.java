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

@Stateless
public class SubmitContractFormCommandHandler extends CommandHandler<SubmitContractFormCommand> {

	@Inject
	ContractRepository contractRepository;

	@Override
	protected void handle(CommandHandlerContext<SubmitContractFormCommand> context) {
		SubmitContractFormCommand command = context.getCommand();
		String contractCode = command.getContractCode();
		String password = command.getPassword();
		// pre check
		this.checkInput(command);
		// contract auth
		this.contractAccAuth(contractCode, password);
		// TODO local storage
	}

	private void contractAccAuth(String contractCode, String password) {
		Optional<Contract> contract = contractRepository.getContract(contractCode);
		if (contract.isPresent()) {
			this.checkPassword(contract, password);
			this.checkTime(contract);
		} else {
			throw new BusinessException("#Msg_314");
		}
	}

	private void checkTime(Optional<Contract> contract) {
		if (contract.get().getContractPeriod().getStartDate().after(GeneralDate.today()) || contract.get().getContractPeriod().getEndDate().before(GeneralDate.today())) {
			throw new BusinessException("#Msg_315");
		}
	}

	private void checkPassword(Optional<Contract> contract, String password) {
		if (!PasswordHash.verifyThat(password, "salt").isEqualTo(contract.get().getPassword().v())) {
			throw new BusinessException("#Msg_302");
		}
	}

	private void checkInput(SubmitContractFormCommand command) {
		if (command.getContractCode() == null) {
			throw new BusinessException("#Msg_313");
		}
		if (command.getPassword() == null) {
			throw new BusinessException("#Msg_310");
		}
	}

}
