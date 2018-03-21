/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveCompanyCalMonthlyFlexCommandHandler.
 */
@Stateless
public class SaveWkpMonthCalSetCommandHandler extends CommandHandler<SaveWkpMonthCalSetCommand> {

	/** The com defor labor month act cal set repo. */
	@Inject
	private WkpDeforLaborMonthActCalSetRepository deforLaborMonthActCalSetRepo;

	/** The com flex month act cal set repo. */
	@Inject
	private WkpFlexMonthActCalSetRepository flexMonthActCalSetRepo;

	/** The com regula month act cal set repo. */
	@Inject
	private WkpRegulaMonthActCalSetRepository regulaMonthActCalSetRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveWkpMonthCalSetCommand> context) {
		String cid = AppContexts.user().companyId();
	}

}
