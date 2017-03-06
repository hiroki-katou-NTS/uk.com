/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthrate.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;

@Stateless
public class DeleteHealthInsuranceCommandHandler extends CommandHandler<DeleteHealthInsuranceCommand> {

	/** The health insurance rate repository. */
	@Inject
	private HealthInsuranceRateRepository healthInsuranceRateRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteHealthInsuranceCommand> command) {
		String historyId = command.getCommand().getHistoryId();
		
		healthInsuranceRateRepository.remove(historyId);
		return;
	}
}