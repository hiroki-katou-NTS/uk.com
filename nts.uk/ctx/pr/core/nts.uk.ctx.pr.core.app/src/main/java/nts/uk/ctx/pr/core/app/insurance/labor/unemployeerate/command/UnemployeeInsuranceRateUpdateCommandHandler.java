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
public class UnemployeeInsuranceRateUpdateCommandHandler extends CommandHandler<UnemployeeInsuranceRateUpdateCommand> {

	/** CompanyRepository */
	@Inject
	private UnemployeeInsuranceRateRepository unemployeeInsuranceRateRepository;

	@Inject
	private UnemployeeInsuranceRateService unemployeeInsuranceRateService;

	/**
	 * Handle command.
	 * 
	 * @param context
	 *            context
	 */
	@Override
	protected void handle(CommandHandlerContext<UnemployeeInsuranceRateUpdateCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		UnemployeeInsuranceRate unemployeeInsuranceRate = context.getCommand().toDomain(companyCode);
		unemployeeInsuranceRate.validate();
		unemployeeInsuranceRateService.validateDateRangeUpdate(unemployeeInsuranceRate);
		this.unemployeeInsuranceRateRepository.update(unemployeeInsuranceRate);
	}

}
