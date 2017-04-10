/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.salarydetail.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSetting;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSettingRepository;

/**
 * The Class SaveSalaryPrintSettingCommandHandler.
 */
@Stateless
public class SaveSalaryPrintSettingCommandHandler extends CommandHandler<SaveSalaryPrintSettingCommand> {

	/** The repository. */
	@Inject
	private SalaryPrintSettingRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveSalaryPrintSettingCommand> context) {
		SaveSalaryPrintSettingCommand command = context.getCommand();
		SalaryPrintSetting salaryPrintSetting = command.toDomain();
		repository.save(salaryPrintSetting);
	}
}
