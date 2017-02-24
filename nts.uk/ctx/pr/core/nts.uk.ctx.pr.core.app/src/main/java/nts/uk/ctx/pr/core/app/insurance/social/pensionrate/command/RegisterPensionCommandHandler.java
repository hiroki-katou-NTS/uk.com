/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service.PensionRateService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RegisterPensionCommandHandler.
 */
@Stateless
public class RegisterPensionCommandHandler extends CommandHandler<RegisterPensionCommand> {

	/** The pension rate service. */
	@Inject
	PensionRateService pensionRateService;
	
	/** The pension rate repository. */
	@Inject
	PensionRateRepository pensionRateRepository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<RegisterPensionCommand> context) {
		// Get command.
		RegisterPensionCommand command = context.getCommand();

		// Get the current company code.
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());

		// Transfer data
		PensionRate pensionRate = command.toDomain(companyCode);

		// Validate
		pensionRateService.validateDateRange(pensionRate);
//		pensionRateService.validateRequiredItem(pensionRate);

		// Insert into db.
		pensionRateRepository.add(pensionRate);
	}

}
