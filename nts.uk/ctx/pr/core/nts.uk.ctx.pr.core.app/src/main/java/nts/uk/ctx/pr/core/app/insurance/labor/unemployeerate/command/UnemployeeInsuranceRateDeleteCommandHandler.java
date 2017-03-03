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
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.service.UnemployeeInsuranceRateService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
@Transactional
public class UnemployeeInsuranceRateDeleteCommandHandler extends CommandHandler<UnemployeeInsuranceRateDeleteCommand> {

	/** CompanyRepository */
	@Inject
	private UnemployeeInsuranceRateRepository unemployeeInsuranceRateRepository;

	/**
	 * Handle command.
	 * 
	 * @param context
	 *            context
	 */
	@Override
	protected void handle(CommandHandlerContext<UnemployeeInsuranceRateDeleteCommand> context) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();
		// get companyCode user login
		String companyCode = loginUserContext.companyCode();
		// get command
		UnemployeeInsuranceRateDeleteCommand command = context.getCommand();
		this.unemployeeInsuranceRateRepository.remove(new CompanyCode(companyCode), command.getCode(),
				command.getVersion());
	}

}
