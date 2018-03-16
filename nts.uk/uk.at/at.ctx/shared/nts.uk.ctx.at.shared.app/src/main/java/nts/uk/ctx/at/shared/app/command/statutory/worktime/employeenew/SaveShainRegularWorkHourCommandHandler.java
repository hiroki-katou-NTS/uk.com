/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;


/**
 * The Class SaveShainRegularWorkHourCommandHandler.
 */
@Getter
@Setter
public class SaveShainRegularWorkHourCommandHandler extends CommandHandler<SaveShainRegularWorkHourCommand> {

	/** The emp reg work hour repo. */
	//@Inject
	//private ShainRegularWorkHourRepository empRegWorkHourRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveShainRegularWorkHourCommand> context) {
		// TODO Auto-generated method stub

	}
}
