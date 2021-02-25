/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.company.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.company.SaveComMonthCalSetCommandHandler;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew.SaveComStatWorkTimeSetCommandHandler;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.ReferencePredTimeOfFlex;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class Kmk004ComAddCommandHandler.
 */
@Stateless
@Transactional
public class Kmk004ComSaveCommandHandler extends CommandHandler<Kmk004ComSaveCommand> {

	/** The save com stat work time set command handler. */
	@Inject
	private SaveComStatWorkTimeSetCommandHandler saveStatCommand;

	/** The save com flex command. */
	@Inject
	private SaveComMonthCalSetCommandHandler saveMonthCommand;
	
	/** The get flex pred work time repository. */
	@Inject
	private GetFlexPredWorkTimeRepository getFlexPredWorkTimeRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<Kmk004ComSaveCommand> context) {
		Kmk004ComSaveCommand addCommand = context.getCommand();
		this.saveStatCommand.handle(addCommand.getSaveStatCommand());
		this.saveMonthCommand.handle(addCommand.getSaveMonthCommand());
		
		String companyId = AppContexts.user().companyId();
		ReferencePredTimeOfFlex reference = ReferencePredTimeOfFlex.valueOf(addCommand.getReferenceFlexPred());
		GetFlexPredWorkTime getFlexPredWorkTime = GetFlexPredWorkTime.of(companyId, reference);
		getFlexPredWorkTimeRepository.persistAndUpdate(getFlexPredWorkTime);
	}
}
