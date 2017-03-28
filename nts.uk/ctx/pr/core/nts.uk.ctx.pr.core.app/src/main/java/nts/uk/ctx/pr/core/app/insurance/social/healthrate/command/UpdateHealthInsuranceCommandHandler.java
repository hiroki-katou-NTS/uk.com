/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthrate.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.service.HealthInsuranceRateService;
import nts.uk.shr.com.context.AppContexts;

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

		// Get the current company code.
		String companyCode = AppContexts.user().companyCode();

		// Get the history.
		HealthInsuranceRate findhealthInsuranceRate = healthInsuranceRateRepository
				.findById(command.getHistoryId()).get();

		// if not exsits
		if (findhealthInsuranceRate == null) {
			throw new BusinessException("ER010");
		} else {
			// Transfer data
			HealthInsuranceRate updatedHealthInsuranceRate = command.toDomain(companyCode,
					findhealthInsuranceRate.getHistoryId(),
					findhealthInsuranceRate.getOfficeCode());
			updatedHealthInsuranceRate.validate();
			// Validate
			healthInsuranceRateService.validateRequiredItem(updatedHealthInsuranceRate);

			// Update to db.
			healthInsuranceRateRepository.update(updatedHealthInsuranceRate);
		}
	}

}
