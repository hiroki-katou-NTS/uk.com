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
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.service.LaborInsuranceOfficeService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class LaborInsuranceOfficeUpdateCommandHandler.
 */
@Stateless
public class LaborInsuranceOfficeUpdateCommandHandler extends CommandHandler<LaborInsuranceOfficeUpdateCommand> {

	/** The labor insurance office repository. */
	@Inject
	private LaborInsuranceOfficeRepository laborInsuranceOfficeRepository;
	
	/** The Labor insurance office service. */
	@Inject
	private LaborInsuranceOfficeService LaborInsuranceOfficeService;
	

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<LaborInsuranceOfficeUpdateCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		LaborInsuranceOffice laborInsuranceOffice = context.getCommand().toDomain(companyCode);
		laborInsuranceOffice.validate();
		LaborInsuranceOfficeService.validateRequiredItem(laborInsuranceOffice);
		this.laborInsuranceOfficeRepository.update(laborInsuranceOffice);
	}

}
