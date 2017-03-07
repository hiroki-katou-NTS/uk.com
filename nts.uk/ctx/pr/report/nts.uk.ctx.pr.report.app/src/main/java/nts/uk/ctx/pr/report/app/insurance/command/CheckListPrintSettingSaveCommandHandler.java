/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.insurance.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSetting;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CheckListPrintSettingSaveCommandHandler.
 */
@Stateless
@Transactional
public class CheckListPrintSettingSaveCommandHandler extends CommandHandler<CheckListPrintSettingSaveCommand> {

	/** The checklist print setting repository. */
	@Inject
	private ChecklistPrintSettingRepository checklistPrintSettingRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CheckListPrintSettingSaveCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		ChecklistPrintSetting checklistPrintSetting = context.getCommand().toDomain(companyCode);
		this.checklistPrintSettingRepository.save(checklistPrintSetting);
	}

}
