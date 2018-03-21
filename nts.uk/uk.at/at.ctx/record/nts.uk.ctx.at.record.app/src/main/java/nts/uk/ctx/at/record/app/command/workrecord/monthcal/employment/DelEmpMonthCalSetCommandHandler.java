/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.employment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSetRepository;

/**
 * The Class DeleteCompanyCalMonthlyFlexCommandHandler.
 */
@Stateless
public class DelEmpMonthCalSetCommandHandler
		extends CommandHandler<DelEmpMonthCalSetCommand> {

	/** The com defor labor month act cal set repo. */
	@Inject
	private EmpDeforLaborMonthActCalSetRepository deforLaborMonthActCalSetRepo;

	/** The com flex month act cal set repo. */
	@Inject
	private EmpFlexMonthActCalSetRepository flexMonthActCalSetRepo;

	/** The com regula month act cal set repo. */
	@Inject
	private EmpRegulaMonthActCalSetRepository regulaMonthActCalSetRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DelEmpMonthCalSetCommand> context) {
		// this.comFlexMonthActCalSetRepo.remove(AppContexts.user().companyId());
	}

}
