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

/**
 * The Class UpdateVaAcRuleCommandHandler.
 */
@Stateless
public class UpdateVaAcRuleCommandHandler extends CommandHandler<UpdateVaAcRuleCommand> {

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
	protected void handle(CommandHandlerContext<UpdateVaAcRuleCommand> context) {

		// Get command.
		UpdateVaAcRuleCommand command = context.getCommand();

		// Get CompanyId

		// Update VacationAcquisitionRule
		Optional<AcquisitionRule> optVaAcRule = this.vaRepo.findById(command.getCompanyId());
		AcquisitionRule vaAcRule = optVaAcRule.get();
		vaAcRule.setSettingClassification(command.getSettingclassification());
		vaAcRule.setAcquisitionOrder(command.getAcquisitionOrder());

		// Update to db.
		this.vaRepo.update(vaAcRule);
	}

}