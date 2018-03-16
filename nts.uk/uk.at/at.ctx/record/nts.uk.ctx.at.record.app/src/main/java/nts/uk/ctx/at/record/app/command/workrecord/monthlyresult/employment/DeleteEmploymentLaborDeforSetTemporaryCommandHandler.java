/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employment;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteEmploymentLaborDeforSetTemporaryCommandHandler.
 */
@Stateless
public class DeleteEmploymentLaborDeforSetTemporaryCommandHandler
		extends CommandHandler<DeleteEmploymentLaborDeforSetTemporaryCommand> {

	/** The empl labor defor set temporary repo. */
	//@Inject
	//private EmploymentLaborDeforSetTemporaryRepository emplLaborDeforSetTemporaryRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteEmploymentLaborDeforSetTemporaryCommand> context) {
		// TODO Auto-generated method stub

	}

}
