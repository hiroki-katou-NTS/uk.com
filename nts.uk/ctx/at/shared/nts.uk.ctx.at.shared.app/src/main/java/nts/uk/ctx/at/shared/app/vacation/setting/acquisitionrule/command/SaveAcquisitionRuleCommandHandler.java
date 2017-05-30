/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.acquisitionrule.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.Category;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UpdateVaAcRuleCommandHandler.
 */
@Stateless
public class SaveAcquisitionRuleCommandHandler extends CommandHandler<AcquisitionRuleCommand> {

	/** The va repo. */
	@Inject
	public AcquisitionRuleRepository vaRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AcquisitionRuleCommand> context) {

		String companyId = AppContexts.user().companyId();
		// Get command.
		AcquisitionRuleCommand command = context.getCommand();

		// Update VacationAcquisitionRule
		Optional<AcquisitionRule> optVaAcRule = this.vaRepo.findById(companyId);
		AcquisitionRule acquisitionRule = command.toDomain(companyId, optVaAcRule.get().getAcquisitionOrder());

		if (optVaAcRule.isPresent()) {
			this.vaRepo.update(acquisitionRule);
		} else {
			this.vaRepo.create(acquisitionRule);
		}
	}

}