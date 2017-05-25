/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.acquisitionrule.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;

/**
 * The Class CreateVaAcRuleCommandHandler.
 */
@Stateless
public class CreateVaAcRuleCommandHandler extends CommandHandler<CreateVaAcRuleCommand> {

	/** The va ac rule repo. */
	@Inject
	private AcquisitionRuleRepository vaAcRuleRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CreateVaAcRuleCommand> context) {

		CreateVaAcRuleCommand command = context.getCommand();
		// GET COMPANY ID
		command.setCompanyId("");

		AcquisitionRule vaAcRule = new AcquisitionRule(command);

		this.vaAcRuleRepo.create(vaAcRule);

	}

}