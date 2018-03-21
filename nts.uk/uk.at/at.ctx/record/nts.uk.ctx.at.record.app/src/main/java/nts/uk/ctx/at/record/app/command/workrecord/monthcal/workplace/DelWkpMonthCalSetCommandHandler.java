/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSetRepository;

/**
 * The Class DeleteCompanyCalMonthlyFlexCommandHandler.
 */
@Stateless
public class DelWkpMonthCalSetCommandHandler
		extends CommandHandler<DelWkpMonthCalSetCommand> {

	/** The com defor labor month act cal set repo. */
	@Inject
	private ComDeforLaborMonthActCalSetRepository deforLaborMonthActCalSetRepo;

	/** The com flex month act cal set repo. */
	@Inject
	private ComFlexMonthActCalSetRepository flexMonthActCalSetRepo;

	/** The com regula month act cal set repo. */
	@Inject
	private ComRegulaMonthActCalSetRepository regulaMonthActCalSetRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DelWkpMonthCalSetCommand> context) {
		// this.comFlexMonthActCalSetRepo.remove(AppContexts.user().companyId());
	}

}
