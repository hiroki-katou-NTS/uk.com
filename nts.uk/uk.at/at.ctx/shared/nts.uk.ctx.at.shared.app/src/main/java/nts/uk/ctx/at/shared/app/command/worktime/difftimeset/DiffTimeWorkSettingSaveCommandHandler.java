/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonSaveCommandHandler;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;

/**
 * The Class DiffTimeWorkSettingSaveCommandHandler.
 */
@Stateless
public class DiffTimeWorkSettingSaveCommandHandler extends CommandHandler<DiffTimeWorkSettingSaveCommand> {

	/** The diff time work setting repository. */
	@Inject
	private DiffTimeWorkSettingRepository diffTimeWorkSettingRepository;

	/** The work time common save command handler. */
	@Inject
	private WorkTimeCommonSaveCommandHandler workTimeCommonSaveCommandHandler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DiffTimeWorkSettingSaveCommand> context) {

		DiffTimeWorkSettingSaveCommand command = context.getCommand();

		DiffTimeWorkSetting diffTimeDomain = command.toDomainDiffTimeWorkSetting();

		// save common
		this.workTimeCommonSaveCommandHandler.handle(command);

		// save difftime to DB
		this.diffTimeWorkSettingRepository.save(diffTimeDomain);
	}

}
