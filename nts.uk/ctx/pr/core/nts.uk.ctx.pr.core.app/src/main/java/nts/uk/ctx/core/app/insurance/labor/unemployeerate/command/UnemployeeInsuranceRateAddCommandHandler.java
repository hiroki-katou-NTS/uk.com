package nts.uk.ctx.core.app.insurance.labor.unemployeerate.command;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.app.insurance.labor.command.LaborInsuranceOfficeAddCommand;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;

public class UnemployeeInsuranceRateAddCommandHandler extends CommandHandler<UnemployeeInsuranceRateAddCommand> {

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
	protected void handle(CommandHandlerContext<UnemployeeInsuranceRateAddCommand> context) {
		UnemployeeInsuranceRate unemployeeInsuranceRate = context.getCommand().toDomain();
		unemployeeInsuranceRate.validate();
		this.unemployeeInsuranceRateRepository.add(unemployeeInsuranceRate);	
	}

}
