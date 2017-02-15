/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthrate.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.service.HealthInsuranceRateService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UpdateHealthInsuranceCommandHandler.
 */
@Stateless
public class UpdateHealthInsuranceCommandHandler extends CommandHandler<UpdateHealthInsuranceCommand> {

	/** The health insurance rate service. */
	@Inject
	HealthInsuranceRateService healthInsuranceRateService;
	
	/** The health insurance rate repository. */
	@Inject
	HealthInsuranceRateRepository healthInsuranceRateRepository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UpdateHealthInsuranceCommand> context) {
		// Get command.
		UpdateHealthInsuranceCommand command = context.getCommand();

		// Get the current company code.
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());

		// Get the history.
		HealthInsuranceRate healthInsuranceRate = healthInsuranceRateRepository.findById(command.getHistoryId()).get();

		// Transfer data
		HealthInsuranceRate updatedHealthInsuranceRate = command.toDomain(companyCode,
				healthInsuranceRate.getHistoryId(), healthInsuranceRate.getOfficeCode());

		// Validate
		healthInsuranceRateService.validateRequiredItem(updatedHealthInsuranceRate);
		healthInsuranceRateService.validateDateRange(updatedHealthInsuranceRate);

		// Update to db.
		healthInsuranceRateRepository.update(updatedHealthInsuranceRate);

	}

}
