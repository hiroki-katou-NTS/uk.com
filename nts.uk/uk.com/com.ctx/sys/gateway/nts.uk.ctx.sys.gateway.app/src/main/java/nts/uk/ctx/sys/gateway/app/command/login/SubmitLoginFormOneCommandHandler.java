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
import nts.uk.ctx.sys.gateway.dom.login.User;
import nts.uk.ctx.sys.gateway.dom.login.UserRepository;

@Stateless
public class SubmitLoginFormOneCommandHandler extends CommandHandler<SubmitLoginFormOneCommand> {

	/** The user repository. */
	@Inject
	UserRepository userRepository;

	@Override
	protected void handle(CommandHandlerContext<SubmitLoginFormOneCommand> context) {

		SubmitLoginFormOneCommand command = context.getCommand();
		String loginId = command.getLoginId();
		String password = command.getPassword();
		// check validate input
		this.checkInput(command);

		// find user by login id
		Optional<User> user = userRepository.getByLoginId(loginId);
		if (!user.isPresent()) {
			throw new BusinessException("Msg_301");
		}

		// check password
		this.compareHashPassword(user, password);

		// check time limit
		this.checkLimitTime(user);

		// TODO check save to local storage(client incharge)
		// if check -> save
		// else -> remove

	}

	private void checkInput(SubmitLoginFormOneCommand command) {
		if (command.getLoginId().isEmpty() || command.getLoginId() == null) {
			throw new BusinessException("Msg_309");
		}

		if (command.getPassword().isEmpty() || command.getPassword() == null) {
			throw new BusinessException("Msg_310");
		}
	}

	private void compareHashPassword(Optional<User> user, String password) {
		if (!PasswordHash.verifyThat(password, "salt").isEqualTo(user.get().getPassword().v())) {
			throw new BusinessException("Msg_302");
		}
	}

	private void checkLimitTime(Optional<User> user) {
		if (!user.get().getExpirationDate().after(GeneralDate.today())) {
			throw new BusinessException("Msg_316");
		}
	}
}
