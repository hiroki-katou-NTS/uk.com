/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthrate.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.service.HealthInsuranceRateService;

/**
 * The Class UpdateHealthInsuranceCommandHandler.
 */
@Stateless
public class UpdateHealthInsuranceCommandHandler
		extends CommandHandler<UpdateHealthInsuranceCommand> {

	/** The health insurance rate service. */
	@Inject
	private HealthInsuranceRateService healthInsuranceRateService;

	/** The health insurance rate repository. */
	@Inject
	private HealthInsuranceRateRepository healthInsuranceRateRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UpdateHealthInsuranceCommand> context) {
		// Get command.
		UpdateHealthInsuranceCommand command = context.getCommand();

		// Get the history.
		Optional<HealthInsuranceRate> optHealthInsuranceRate = this.healthInsuranceRateRepository
				.findById(command.getHistoryId());

		// if not exsits
		if (!optHealthInsuranceRate.isPresent()) {
			throw new BusinessException("ER010");
		} else {
			// Transfer data
			HealthInsuranceRate healthInsuranceRate = optHealthInsuranceRate.get();
			healthInsuranceRate.setAutoCalculate(command.getAutoCalculate());
			healthInsuranceRate.setMaxAmount(command.getMaxAmount());
			healthInsuranceRate.setRateItems(command.getRateItems());
			healthInsuranceRate.setRoundingMethods(command.getRoundingMethods());

			// Validate
			healthInsuranceRate.validate();
			this.healthInsuranceRateService.validateRequiredItem(healthInsuranceRate);

			// Update to db.
			this.healthInsuranceRateRepository.update(healthInsuranceRate);
		}
	}

}
