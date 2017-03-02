/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.service.HealthInsuranceAvgearnService;

/**
 * The Class CreateHealthInsuranceAvgearnCommandHandler.
 */
@Stateless
public class CreateHealthInsuranceAvgearnCommandHandler extends CommandHandler<CreateHealthInsuranceAvgearnCommand> {

	/** The health insurance avgearn service. */
	@Inject
	private HealthInsuranceAvgearnService healthInsuranceAvgearnService;

	/** The health insurance avgearn repository. */
	@Inject
	private HealthInsuranceAvgearnRepository healthInsuranceAvgearnRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CreateHealthInsuranceAvgearnCommand> context) {
		// Get command.
		CreateHealthInsuranceAvgearnCommand command = context.getCommand();

		command.getListHealthInsuranceAvgearn().forEach(dto -> {

			// Transfer data
			HealthInsuranceAvgearn healthInsuranceAvgearn = dto.toDomain();

			// Validate
			healthInsuranceAvgearnService.validateRequiredItem(healthInsuranceAvgearn);
			// Update to db.
			healthInsuranceAvgearnRepository.add(healthInsuranceAvgearn);

		});
	}
}
