/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service.PensionRateService;

/**
 * The Class UpdatePensionCommandHandler.
 */
@Stateless
public class UpdatePensionCommandHandler extends CommandHandler<UpdatePensionCommand> {

	/** The pension rate service. */
	@Inject
	private PensionRateService pensionRateService;

	/** The pension rate repository. */
	@Inject
	private PensionRateRepository pensionRateRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UpdatePensionCommand> context) {
		// Get command.
		UpdatePensionCommand command = context.getCommand();

		// Get the history.
		PensionRate pensionRate = this.pensionRateRepository.findById(command.getHistoryId()).get();

		// Update data
		pensionRate.setAutoCalculate(command.getAutoCalculate());
		pensionRate.setChildContributionRate(command.getChildContributionRate());
		pensionRate.setFundInputApply(command.getFundInputApply());
		pensionRate.setFundRateItems(command.getFundRateItems());
		pensionRate.setMaxAmount(command.getMaxAmount());
		pensionRate.setPremiumRateItems(command.getPremiumRateItems());
		pensionRate.setRoundingMethods(command.getRoundingMethods());

		// Validate
		pensionRate.validate();
		this.pensionRateService.validateRequiredItem(pensionRate);

		// Update to db.
		this.pensionRateRepository.update(pensionRate);

	}

}
