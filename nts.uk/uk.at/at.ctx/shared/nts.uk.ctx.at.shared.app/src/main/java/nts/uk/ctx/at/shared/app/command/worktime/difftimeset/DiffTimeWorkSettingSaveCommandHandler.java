/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonSaveCommandHandler;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DiffTimeWorkSettingSaveCommandHandler.
 */
@Stateless
public class DiffTimeWorkSettingSaveCommandHandler extends CommandHandler<DiffTimeWorkSettingSaveCommand> {

	/** The fixed work setting repository. */
	@Inject
	private DiffTimeWorkSettingRepository difftimeWorkSettingRepository;

	/** The common handler. */
	@Inject
	private WorkTimeCommonSaveCommandHandler commonHandler;

	// /** The fixed policy. */
	// @Inject
	// private FixedWorkSettingPolicy fixedPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DiffTimeWorkSettingSaveCommand> context) {

		String companyId = AppContexts.user().companyId();

		// get command
		DiffTimeWorkSettingSaveCommand command = context.getCommand();

		// get domain fixed work setting by client send
		DiffTimeWorkSetting difftimeWorkSetting = command.toDomainDiffTimeWorkSetting();

		// Validate
		// this.fixedPolicy.canRegister(fixedWorkSetting,
		// command.toDomainPredetemineTimeSetting());

		// common handler
		this.commonHandler.handle(command);

		// call repository save fixed work setting
		if (command.isAddMode()) {
			// difftimeWorkSetting.restoreDefaultData(ScreenMode.valueOf(command.));
			this.difftimeWorkSettingRepository.add(difftimeWorkSetting);
		} else {
			Optional<DiffTimeWorkSetting> opDiffTimeWorkSetting = this.difftimeWorkSettingRepository.find(companyId,
					command.getWorktimeSetting().worktimeCode);
			if (opDiffTimeWorkSetting.isPresent()) {
				// fixedWorkSetting.restoreData(ScreenMode.valueOf(command.getScreenMode()),
				// command.getWorktimeSetting().getWorkTimeDivision(),
				// opFixedWorkSetting.get());
				this.difftimeWorkSettingRepository.update(difftimeWorkSetting);
			}
		}
	}

}
