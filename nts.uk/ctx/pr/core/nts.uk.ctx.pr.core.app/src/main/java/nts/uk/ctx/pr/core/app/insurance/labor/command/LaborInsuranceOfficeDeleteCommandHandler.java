/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class LaborInsuranceOfficeDeleteCommandHandler.
 */
@Stateless
@Transactional
public class LaborInsuranceOfficeDeleteCommandHandler extends CommandHandler<LaborInsuranceOfficeDeleteCommand> {

	/** The labor insurance office repository. */
	@Inject
	private LaborInsuranceOfficeRepository laborInsuranceOfficeRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<LaborInsuranceOfficeDeleteCommand> context) {
		LaborInsuranceOfficeDeleteCommand laborInsuranceOffice = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		this.laborInsuranceOfficeRepo.remove(new CompanyCode(companyCode),
				context.getCommand().getLaborInsuranceOfficeDeleteDto().getCode(),
				context.getCommand().getLaborInsuranceOfficeDeleteDto().getVersion());
	}

}
