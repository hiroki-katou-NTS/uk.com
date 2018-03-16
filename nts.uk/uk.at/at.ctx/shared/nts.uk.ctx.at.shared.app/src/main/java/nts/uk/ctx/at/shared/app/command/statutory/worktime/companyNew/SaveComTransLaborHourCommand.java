/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborHourRepository;

/**
 * The Class SaveCompanyTransLaborHourCommand.
 */
@Getter
@Setter
public class SaveComTransLaborHourCommand extends CommandHandler<SaveComTransLaborHourCommand> {

	/** The com trans labor hour repo. */
	@Inject
	private ComTransLaborHourRepository comTransLaborHourRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveComTransLaborHourCommand> context) {
		// TODO Auto-generated method stub

	}

}
