/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.service.AccidentInsuranceRateService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class AccidentInsuranceRateUpdateCommandHandler.
 */
@Stateless
public class AccidentInsuranceRateUpdateCommandHandler extends CommandHandler<AccidentInsuranceRateUpdateCommand> {

	/** The accident insurance rate repository. */
	@Inject
	private AccidentInsuranceRateRepository accidentInsuranceRateRepo;

	/** The accident insurance rate service. */
	@Inject
	private AccidentInsuranceRateService accidentInsuranceRateService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<AccidentInsuranceRateUpdateCommand> context) {
		
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get companyCode by user login
		String companyCode = loginUserContext.companyCode();
		
		// getCommand
		AccidentInsuranceRateUpdateCommand command = context.getCommand();
		
		// to domain
		AccidentInsuranceRate accidentInsuranceRate = command.toDomain(companyCode);
		
		// validate domain
		accidentInsuranceRate.validate();
		
		// validate input
		this.accidentInsuranceRateService.validateDateRangeUpdate(accidentInsuranceRate);
		
		// get first by update
		Optional<AccidentInsuranceRate> optionalUpdate = this.accidentInsuranceRateRepo.findBetweenUpdate(
				accidentInsuranceRate.getCompanyCode().v(), accidentInsuranceRate.getApplyRange().getStartMonth(),
				accidentInsuranceRate.getHistoryId());
		
		if (optionalUpdate.isPresent()) {
			this.accidentInsuranceRateRepo.updateYearMonth(optionalUpdate.get(),
					accidentInsuranceRate.getApplyRange().getStartMonth().previousMonth());
		}
		
		// connection service update
		this.accidentInsuranceRateRepo.update(accidentInsuranceRate);
	}

}
