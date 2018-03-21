/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveCompanyCalMonthlyFlexCommandHandler.
 */
@Stateless
public class SaveShaMonthCalSetCommandHandler
		extends CommandHandler<SaveShaMonthCalSetCommand> {

	/** The com defor labor month act cal set repo. */
	@Inject
	private ShaDeforLaborMonthActCalSetRepository deforLaborMonthActCalSetRepo;

	/** The com flex month act cal set repo. */
	@Inject
	private ShaFlexMonthActCalSetRepository flexMonthActCalSetRepo;

	/** The com regula month act cal set repo. */
	@Inject
	private ShaRegulaMonthActCalSetRepository regulaMonthActCalSetRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveShaMonthCalSetCommand> context) {
		String cid = AppContexts.user().companyId();
	}

}
