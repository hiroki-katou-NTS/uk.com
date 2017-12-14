/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.security.hash.password.PasswordHash;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.gateway.dom.login.Contract;
import nts.uk.ctx.sys.gateway.dom.login.ContractRepository;
import nts.uk.ctx.sys.gateway.dom.login.User;
import nts.uk.ctx.sys.gateway.dom.login.UserRepository;

/**
 * The Class SubmitLoginFormOneCommandHandler.
 */
@Stateless
public class SubmitLoginFormOneCommandHandler extends LoginBaseCommand<SubmitLoginFormOneCommand> {

	/** The user repository. */
	@Inject
	private UserRepository userRepository;
	
	/** The contract repository. */
	@Inject
	private ContractRepository contractRepository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void internalHanler(CommandHandlerContext<SubmitLoginFormOneCommand> context) {

		SubmitLoginFormOneCommand command = context.getCommand();
		String loginId = command.getLoginId();
		String password = command.getPassword();
		// check validate input
		this.checkInput(command);

		//reCheck contract
		//pre check contract
		this.checkContractInput(command);
		//contract auth
		this.contractAccAuth(command);
		
		// find user by login id
		Optional<User> user = userRepository.getByLoginId(loginId);
		if (!user.isPresent()) {
			throw new BusinessException("Msg_301");
		}

		// check password
		this.compareHashPassword(user, password);

		// check time limit
		this.checkLimitTime(user);
		
		//set info to session
		this.initSession(user.get());
	}

	/**
	 * Check input.
	 *
	 * @param command the command
	 */
	private void checkInput(SubmitLoginFormOneCommand command) {
		//check input loginId
		if (command.getLoginId().trim().isEmpty() || command.getLoginId() == null) {
			throw new BusinessException("Msg_309");
		}
		//check input password
		if (StringUtil.isNullOrEmpty(command.getPassword(), true)) {
			throw new BusinessException("Msg_310");
		}
	}
	
	/**
	 * Check contract input.
	 *
	 * @param command the command
	 */
	private void checkContractInput(SubmitLoginFormOneCommand command) {
		if (StringUtil.isNullOrEmpty(command.getContractCode(),true)) {
			throw new RuntimeException();
		}
		if (StringUtil.isNullOrEmpty(command.getContractPassword(),true)) {
			throw new RuntimeException();
		}
	}

	/**
	 * Contract acc auth.
	 *
	 * @param command the command
	 */
	private void contractAccAuth(SubmitLoginFormOneCommand command) {
		Optional<Contract> contract = contractRepository.getContract(command.getContractCode());
		if (contract.isPresent()) {
			// check contract pass
			if (!PasswordHash.verifyThat(command.getContractPassword(), contract.get().getContractCode().v())
					.isEqualTo(contract.get().getPassword().v())) {
				throw new RuntimeException();
			}
			// check contract time
			if (contract.get().getContractPeriod().start().after(GeneralDate.today())
					|| contract.get().getContractPeriod().end().before(GeneralDate.today())) {
				throw new RuntimeException();
			}
		} else {
			throw new RuntimeException();
		}
	}
	
	/**
	 * Compare hash password.
	 *
	 * @param user the user
	 * @param password the password
	 */
	private void compareHashPassword(Optional<User> user, String password) {
		if (!PasswordHash.verifyThat(password, user.get().getUserId()).isEqualTo(user.get().getPassword().v())) {
			throw new BusinessException("Msg_302");
		}
	}

	/**
	 * Check limit time.
	 *
	 * @param user the user
	 */
	private void checkLimitTime(Optional<User> user) {
		if (user.get().getExpirationDate().before(GeneralDate.today())) {
			throw new BusinessException("Msg_316");
		}
	}
}
