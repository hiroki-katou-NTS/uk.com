/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.singlesignon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccount;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccountGetMemento;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccountInfo;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccountRepository;

/**
 * The Class SaveWindowAccountCommandHandler.
 */
@Stateless
public class SaveWindowAccountCommandHandler extends CommandHandler<SaveWindowAccountCommand> {

	/** The window account repository. */
	@Inject
	private WindowsAccountRepository windowAccountRepository;

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

			List<WindowAccountDto> listWinAccDto = new ArrayList<>();
						
			if (command.getWinAcc1() != null) {
					this.validate(command.getWinAcc1());
				
				listWinAccDto.add(command.getWinAcc1());
			}
			if (command.getWinAcc2() != null) {
					this.validate(command.getWinAcc2());

				listWinAccDto.add(command.getWinAcc2());
			}
			if (command.getWinAcc3() != null) {
					this.validate(command.getWinAcc3());

				listWinAccDto.add(command.getWinAcc3());
			}
			if (command.getWinAcc4() != null) {
					this.validate(command.getWinAcc4());

				listWinAccDto.add(command.getWinAcc4());
			}
			if (command.getWinAcc5() != null) {
					this.validate(command.getWinAcc5());

				listWinAccDto.add(command.getWinAcc5());
			}
			
			// remove old domain
			Optional<WindowsAccount> optWindowAcc = windowAccountRepository.findByUserId(command.getUserId());
		
			// add and update data to db
			this.save(optWindowAcc,listWinAccDto );

		}
	
	private void save(Optional<WindowsAccount> optWindowAccDB, List<WindowAccountDto> listWinAccDto) {
		WindowsAccount windAccCommand = this.toWindowsAccountDomain(listWinAccDto);
		windAccCommand.validate();
		
		Map<Integer, WindowsAccountInfo> mapWinAcc = new HashMap<Integer, WindowsAccountInfo>();
		
		if(optWindowAccDB.isPresent()) {
			mapWinAcc = optWindowAccDB.get().getAccountInfos().stream()
					.collect(Collectors.toMap(WindowsAccountInfo::getNo, Function.identity()));
		}
		
		List<Integer> lstWinAccSaved = new ArrayList<>();
		
		for (WindowsAccountInfo domain : windAccCommand.getAccountInfos()) {
			
			lstWinAccSaved.add(domain.getNo());
			
			WindowsAccountInfo winAccDb = mapWinAcc.get(domain.getNo());
			
			// not existed, insert DB
			if (winAccDb == null) {
				this.windowAccountRepository.add(windAccCommand.getUserId(), domain);
			} else {
				this.windowAccountRepository.update(windAccCommand.getUserId(), domain, winAccDb);
			}
		}
		
		// remove item not setting
		if(optWindowAccDB.isPresent()) {
			optWindowAccDB.get().getAccountInfos().stream().filter(
					domain -> domain.getNo() != null && !lstWinAccSaved.contains(domain.getNo()))
					.forEach(domain -> {
						this.windowAccountRepository.remove(windAccCommand.getUserId(), domain.getNo());
					});
		}
	}
	
	/**
	 * Validate.
	 *
	 * @param dto
	 *            the dto
	 */
	private void validate(WindowAccountDto dto) {
		if (dto.getUseAtr() == UseAtr.NotUse) {
			return;
		}
		// check error domain
		boolean isError = false;
		BundledBusinessException exceptions = BundledBusinessException.newInstance();

		if (!StringUtils.isEmpty(dto.getHostName().v()) && !StringUtils.isEmpty(dto.getUserName().v())) {
			Optional<WindowsAccount> opWindowAccount = windowAccountRepository
					.findbyUserNameAndHostNameAndIsUsed(dto.getUserName().v(), dto.getHostName().v());

			// Check condition
			if (opWindowAccount.isPresent() && !opWindowAccount.get().getUserId().equals(dto.getUserId())) {
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
		
	/**
	 * To domain.
	 *
	 * @return the total condition
	 */
	public WindowsAccount toWindowsAccountDomain(List<WindowAccountDto> windowAccountDtos) {
		return new WindowsAccount(new WindowsAccountDtoGetMemento(windowAccountDtos));
	}

	/**
	 * The Class DtoGetMemento.
	 */
	private class WindowsAccountDtoGetMemento implements WindowsAccountGetMemento {

		/** The command. */
		private List<WindowAccountDto> windowAccountDtos;

		/**
		 * Instantiates a new dto get memento.
		 *
		 * @param command
		 *            the command
		 */
		public WindowsAccountDtoGetMemento(List<WindowAccountDto> windowAccountDtos) {
			this.windowAccountDtos = windowAccountDtos;
		}


		@Override
		public String getUserId() {
			if (CollectionUtil.isEmpty(this.windowAccountDtos)) {
				return null;
			}
			return this.windowAccountDtos.stream().findFirst().get().getUserId();
		}

		@Override
		public List<WindowsAccountInfo> getAccountInfos() {
			// Check empty
			if (CollectionUtil.isEmpty(this.windowAccountDtos)) {
				return Collections.emptyList();
			}
			
			return this.windowAccountDtos.stream().map(item -> new WindowsAccountInfo(item))
					.collect(Collectors.toList());
		}

	}
}
