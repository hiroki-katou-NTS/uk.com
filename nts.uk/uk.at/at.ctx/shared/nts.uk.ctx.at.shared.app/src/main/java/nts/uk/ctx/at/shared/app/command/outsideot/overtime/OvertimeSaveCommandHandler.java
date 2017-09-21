/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.overtime;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.OvertimeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OvertimeSaveCommandHandler.
 */
@Stateless
public class OvertimeSaveCommandHandler extends CommandHandler<OvertimeSaveCommand>{

	/** The repository. */
	@Inject
	private OvertimeRepository repository;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OvertimeSaveCommand> context) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// get command
		OvertimeSaveCommand command = context.getCommand();
		
		// to domains
		List<Overtime> domains = command.getOvertimes().stream().map(dto -> new Overtime(dto))
				.collect(Collectors.toList());
		
		// validate domains
		if(CollectionUtil.isEmpty(domains)){
			throw new BusinessException("Msg_486");
		}
		if (!checkUseOvertime(domains)) {
			throw new BusinessException("Msg_486");
		}
		
		// save all list overtime
		this.repository.saveAll(domains, companyId);
	}

	/**
	 * Check use overtime.
	 *
	 * @param domains the domains
	 * @return true, if successful
	 */
	private boolean checkUseOvertime(List<Overtime> domains) {
		for (Overtime overtime : domains) {
			if (overtime.getUseClassification().value == UseClassification.UseClass_Use.value) {
				return true;
			}
		}
		return false;
	}
}
