/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;

/**
 * The Class LaborInsuranceOfficeDeleteCommandHandler.
 */
@Stateless
@Transactional
public class LaborInsuranceOfficeDeleteCommandHandler extends CommandHandler<LaborInsuranceOfficeDeleteCommand> {

	/** The labor insurance office repository. */
	@Inject
	private LaborInsuranceOfficeRepository laborInsuranceOfficeRepo;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<LaborInsuranceOfficeDeleteCommand> context) {
		LaborInsuranceOfficeDeleteCommand laborInsuranceOffice = context.getCommand();
		this.laborInsuranceOfficeRepo.remove(laborInsuranceOffice.getCode(), 1000L);
	}

}
