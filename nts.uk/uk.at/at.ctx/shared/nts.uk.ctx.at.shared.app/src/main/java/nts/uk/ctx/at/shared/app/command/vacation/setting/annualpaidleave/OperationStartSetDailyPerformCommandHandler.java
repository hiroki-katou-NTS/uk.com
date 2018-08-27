/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.annualpaidleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;

/**
 * The Class OperationStartSetDailyPerformCommandHandler.
 */
@Stateless
public class OperationStartSetDailyPerformCommandHandler extends CommandHandler<OperationStartSetDailyPerformCommand>{

	@Inject
	private OperationStartSetDailyPerformRepository operationStartSetDailyPerformRepository; 
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OperationStartSetDailyPerformCommand> context) {
		OperationStartSetDailyPerformCommand command = context.getCommand();
		CompanyId companyId = command.getCompanyId();
		Optional<OperationStartSetDailyPerform> opt = operationStartSetDailyPerformRepository.findByCid(companyId);
		OperationStartSetDailyPerform domain = new OperationStartSetDailyPerform(command);
		if (opt.isPresent()) {
			operationStartSetDailyPerformRepository.update(domain);
		} else {
			operationStartSetDailyPerformRepository.add(domain);
		}
	}

}

