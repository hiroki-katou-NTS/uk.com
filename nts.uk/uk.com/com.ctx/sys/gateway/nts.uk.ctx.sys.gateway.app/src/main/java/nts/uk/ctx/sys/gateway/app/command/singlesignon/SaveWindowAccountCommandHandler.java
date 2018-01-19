/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.singlesignon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

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
			for (WindowAccount wd : listWindowAcc) {
					windowAccountRepository.remove(wd.getUserId(), wd.getNo());
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
		
			// add and update data to db
			save(listWindowAcc,listWinAccDto );

		}
	}
	
	private void save(List<WindowAccount> listWindowAccDB, List<WindowAccountDto> listWinAccDto) {
		List<WindowAccount> lstWindAccCommand = listWinAccDto.stream().map(item -> new WindowAccount(item)).collect(Collectors.toList());
		
		Map<Integer, WindowAccount> mapWinAcc = listWindowAccDB.stream()
				.collect(Collectors.toMap(item -> ((WindowAccount) item).getNo(), Function.identity()));
		
		List<Integer> lstWinAccSaved = new ArrayList<>();
		
		lstWindAccCommand.forEach(domain -> {
			lstWinAccSaved.add(domain.getNo());
			
			WindowAccount winAccDb = mapWinAcc.get(domain.getNo());
			
			// not existed, insert DB
			if (winAccDb == null) {
				this.windowAccountRepository.add(domain);
			} else {
				this.windowAccountRepository.update(domain, winAccDb);
			}
		});
		
		// remove item not setting
		listWindowAccDB.stream()
		.filter(domain -> domain.getNo() != null && !lstWinAccSaved.contains(domain.getNo()))
		.forEach(domain -> {
			this.windowAccountRepository.remove(domain.getUserId(), domain.getNo());
		});
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

		if (!StringUtils.isEmpty(dto.getHostName().v()) && !StringUtils.isEmpty(dto.getUserName().v())) {
			Optional<WindowAccount> opWindowAccount = windowAccountRepository
					.findbyUserNameAndHostName(dto.getUserName().v(), dto.getHostName().v());

			// Check condition
			if (opWindowAccount.isPresent() && !opWindowAccount.get().getUserId().contains(dto.getUserId())) {
				// Has error, throws message
				isError = true;
				exceptions.addMessage("Msg_616");
			}

			if (isError) {
				// show error list
				exceptions.throwExceptions();
			}
		}
	}
		
}
