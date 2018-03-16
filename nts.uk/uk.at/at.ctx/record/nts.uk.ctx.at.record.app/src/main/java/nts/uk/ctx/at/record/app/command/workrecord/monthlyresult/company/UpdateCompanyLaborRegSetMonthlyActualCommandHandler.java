/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.company;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class UpdateCompanyLaborRegSetMonthlyActualCommandHandler.
 */
@Stateless
public class UpdateCompanyLaborRegSetMonthlyActualCommandHandler
		extends CommandHandler<UpdateCompanyLaborRegSetMonthlyActualCommand> {

	/** The com labor reg set monthly actual repo. */
	//@Inject
	//private CompanyLaborRegSetMonthlyActualRepository comLaborRegSetMonthlyActualRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateCompanyLaborRegSetMonthlyActualCommand> context) {
		// TODO Auto-generated method stub

	}

}
