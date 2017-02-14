/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.app.company.command.AddCompanyCommand;
import nts.uk.ctx.core.dom.company.Company;
import nts.uk.ctx.core.dom.company.CompanyRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.service.LaborInsuranceOfficeService;

/**
 * The Class LaborInsuranceOfficeAddCommandHandler.
 */
@Stateless
@Transactional
public class LaborInsuranceOfficeAddCommandHandler extends CommandHandler<LaborInsuranceOfficeAddCommand> {

	/** CompanyRepository */
	@Inject
	private LaborInsuranceOfficeRepository laborInsuranceOfficeRepository;

	/** The labor insurance office service. */
	@Inject
	private LaborInsuranceOfficeService laborInsuranceOfficeService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<LaborInsuranceOfficeAddCommand> context) {
		LaborInsuranceOffice laborInsuranceOffice = context.getCommand().toDomain();
		laborInsuranceOfficeService.validateRequiredItem(laborInsuranceOffice);
		laborInsuranceOfficeService.checkDuplicateCode(laborInsuranceOffice);
		this.laborInsuranceOfficeRepository.add(laborInsuranceOffice);
	}
}
