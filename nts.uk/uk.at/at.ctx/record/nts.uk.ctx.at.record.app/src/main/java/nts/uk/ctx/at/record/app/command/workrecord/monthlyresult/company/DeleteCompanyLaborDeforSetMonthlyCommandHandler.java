/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.company;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteCompanyLaborDeforSetMonthlyCommandHandler.
 */
@Stateless
public class DeleteCompanyLaborDeforSetMonthlyCommandHandler
		extends CommandHandler<DeleteCompanyLaborDeforSetMonthlyCommand> {

	/** The com labor defor set monthly repo. */
	//@Inject
	//private CompanyLaborDeforSetMonthlyRepository comLaborDeforSetMonthlyRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteCompanyLaborDeforSetMonthlyCommand> context) {
		// TODO Auto-generated method stub

	}

}
