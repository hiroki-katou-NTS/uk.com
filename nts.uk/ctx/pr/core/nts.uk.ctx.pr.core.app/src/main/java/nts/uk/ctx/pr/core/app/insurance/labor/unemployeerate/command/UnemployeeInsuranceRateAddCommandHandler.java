/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.service.UnemployeeInsuranceRateService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UnemployeeInsuranceRateAddCommandHandler extends CommandHandler<UnemployeeInsuranceRateAddCommand> {

	/** CompanyRepository */
	@Inject
	private UnemployeeInsuranceRateRepository unemployeeInsuranceRateRepository;
	
	/** The unemployee insurance rate service. */
	@Inject
	private UnemployeeInsuranceRateService unemployeeInsuranceRateService;

	/**
	 * Handle command.
	 * 
	 * @param context
	 *            context
	 */
	@Override
	protected void handle(CommandHandlerContext<UnemployeeInsuranceRateAddCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		UnemployeeInsuranceRate unemployeeInsuranceRate = context.getCommand().toDomain(companyCode);
		unemployeeInsuranceRate.validate();
		unemployeeInsuranceRateService.validateDateRange(unemployeeInsuranceRate);
		this.unemployeeInsuranceRateRepository.add(unemployeeInsuranceRate);
	}

}
