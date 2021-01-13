/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.acquisitionrule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.SettingDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AnnualHoliday;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.HoursHoliday;
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
		AcquisitionRule acquisitionRuleCommand = command.toDomain(companyId);

		// Update VacationAcquisitionRule
		Optional<AcquisitionRule> optVaAcRule = this.vaRepo.findById(companyId);
		
		//Check is managed, keep old values when is not managed
		if (acquisitionRuleCommand.getCategory().equals(SettingDistinct.NO)) {
			if (optVaAcRule.isPresent()) {
				AcquisitionRule acquisitionRuleDB = optVaAcRule.get();
				acquisitionRuleDB.setCategory(acquisitionRuleCommand.getCategory());
				this.vaRepo.update(acquisitionRuleDB);
			} else {
				acquisitionRuleCommand.setAnnualHoliday(new AnnualHoliday(false, false, false));
				this.vaRepo.create(acquisitionRuleCommand);
			}
		} else {
			if (optVaAcRule.isPresent()) {
				this.vaRepo.update(acquisitionRuleCommand);
			} else {
				this.vaRepo.create(acquisitionRuleCommand);
			}
		}
	}
}