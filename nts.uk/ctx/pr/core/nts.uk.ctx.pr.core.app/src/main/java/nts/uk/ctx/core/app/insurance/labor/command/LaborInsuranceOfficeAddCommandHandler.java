package nts.uk.ctx.core.app.insurance.labor.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.app.company.command.AddCompanyCommand;
import nts.uk.ctx.core.dom.company.Company;
import nts.uk.ctx.core.dom.company.CompanyRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;

@Stateless
@Transactional
public class LaborInsuranceOfficeAddCommandHandler extends CommandHandler<LaborInsuranceOfficeAddCommand> {

	/** CompanyRepository */
	@Inject
	private LaborInsuranceOfficeRepository laborInsuranceOfficeRepository;

	/**
	 * Handle command.
	 * 
	 * @param context
	 *            context
	 */
	@Override
	protected void handle(CommandHandlerContext<LaborInsuranceOfficeAddCommand> context) {
		LaborInsuranceOffice laborInsuranceOffice = context.getCommand().toDomain();
		laborInsuranceOffice.validate();
		this.laborInsuranceOfficeRepository.add(laborInsuranceOffice);
	}

}
