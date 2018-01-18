/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.singlesignon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccount;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountRepository;

/**
 * The Class SaveWindowAccountCommandHandler.
 */
@Stateless
public class SaveWindowAccountCommandHandler extends CommandHandler<SaveWindowAccountCommand> {

	/** The window account repository. */
	@Inject
	private WindowAccountRepository windowAccountRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveWindowAccountCommand> context) {
		// Get command
		SaveWindowAccountCommand command = context.getCommand();

		// remove in special case, when remove just only an item
		if (command.getWinAcc1() == null && command.getWinAcc2() == null && command.getWinAcc3() == null
				&& command.getWinAcc4() == null && command.getWinAcc5() == null) {
			List<WindowAccount> listWindowAcc = windowAccountRepository.findByUserId(command.getUserId());
			if (!listWindowAcc.isEmpty()) {
				listWindowAcc.stream().forEach(wd -> {
					windowAccountRepository.remove(wd.getUserId(), wd.getUserName(), wd.getHostName());
				});
			}

		} else {

			List<WindowAccountDto> listWinAccDto = new ArrayList<>();
						
			// TODO: need refactor
			if (command.getWinAcc1() != null) {
				if (command.getWinAcc1().getIsChange()) {
					this.validate(command.getWinAcc1());
				}
				
				listWinAccDto.add(command.getWinAcc1());
			}
			if (command.getWinAcc2() != null) {
				if (command.getWinAcc2().getIsChange()) {
					this.validate(command.getWinAcc2());
				}

				listWinAccDto.add(command.getWinAcc2());
			}
			if (command.getWinAcc3() != null) {
				if (command.getWinAcc3().getIsChange()) {
					this.validate(command.getWinAcc3());
				}

				listWinAccDto.add(command.getWinAcc3());
			}
			if (command.getWinAcc4() != null) {
				if (command.getWinAcc4().getIsChange()) {
					this.validate(command.getWinAcc4());
				}

				listWinAccDto.add(command.getWinAcc4());
			}
			if (command.getWinAcc5() != null) {
				if (command.getWinAcc5().getIsChange()) {
					this.validate(command.getWinAcc5());
				}

				listWinAccDto.add(command.getWinAcc5());
			}
			
			// remove old domain
			List<WindowAccount> listWindowAcc = windowAccountRepository.findByUserId(command.getUserId());
			
			if (!listWindowAcc.isEmpty()) {
				listWindowAcc.stream().forEach(wd -> {
					windowAccountRepository.remove(wd.getUserId(), wd.getUserName(), wd.getHostName());
				});
			}

			// Save new domain
			listWinAccDto.forEach(winAcc -> this.windowAccountRepository.add(new WindowAccount(winAcc)));
			
		}
	}

	/**
	 * Validate.
	 *
	 * @param dto
	 *            the dto
	 */
	private void validate(WindowAccountDto dto) {
		// check error domain
		boolean isError = false;
		BundledBusinessException exceptions = BundledBusinessException.newInstance();

		Optional<WindowAccount> opWindowAccount = windowAccountRepository.findbyUserNameAndHostName(dto.getUserName(),
				dto.getHostName());

		// Check condition
		if (opWindowAccount.isPresent()) {
			// Has error, throws message
			isError = true;
			exceptions.addMessage("Msg_616");
		}

		if (isError) {			
			//show error list
			exceptions.throwExceptions();
		}
	}
		
}
