/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employment;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment.EmpCalMonthlyFlexRepository;

/**
 * The Class DeleteEmploymentCalMonthlyFlexCommandhandler.
 */
public class DeleteEmploymentCalMonthlyFlexCommandhandler
		extends CommandHandler<DeleteEmploymentCalMonthlyFlexCommand> {

	/** The empl cal monthly flex repo. */
	@Inject
	private EmpCalMonthlyFlexRepository emplCalMonthlyFlexRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteEmploymentCalMonthlyFlexCommand> context) {
		// TODO Auto-generated method stub

	}

}
