package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service.PensionRateService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdatePensionCommandHandler extends CommandHandler<UpdatePensionCommand> {

	@Inject
	private PensionRateService pensionRateService;
	private PensionRateRepository pensionRateRepository;

	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UpdatePensionCommand> context) {
		// Get command.
		UpdatePensionCommand command = context.getCommand();

		// Get the current company code.
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());

		// Get the history.
		PensionRate pensionRate = pensionRateRepository.findById(command.getHistoryId()).get();

		// Transfer data
		PensionRate updatedPensionRate = command.toDomain(companyCode, pensionRate.getHistoryId(),
				pensionRate.getOfficeCode());

		// Validate
		pensionRateService.validateRequiredItem(updatedPensionRate);
		pensionRateService.validateDateRange(updatedPensionRate);

		// Update to db.
		pensionRateRepository.update(updatedPensionRate);

	}

}
