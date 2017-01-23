package nts.uk.ctx.core.app.insurance.labor.unemployeerate.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;

@Stateless
@Transactional		
public class UnemployeeInsuranceRateUpdateCommandHandler extends CommandHandler<UnemployeeInsuranceRateUpdateCommand> {

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
	protected void handle(CommandHandlerContext<UnemployeeInsuranceRateUpdateCommand> context) {
		UnemployeeInsuranceRate unemployeeInsuranceRate = context.getCommand().toDomain();
		unemployeeInsuranceRate.validate();
		this.unemployeeInsuranceRateRepository.update(unemployeeInsuranceRate);
	}

}
