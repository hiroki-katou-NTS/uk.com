/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayModeRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkTimeCommonSaveCommandHandler.
 */
@Stateless
public class WorkTimeCommonSaveCommandHandler {

	/** The work time setting repository. */
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	/** The work time display mode repository. */
	@Inject
	private WorkTimeDisplayModeRepository workTimeDisplayModeRepository;

	/** The predetemine time setting repository. */
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

	/** The work time set policy. */
	@Inject
	private WorkTimeSettingPolicy workTimeSetPolicy;

	/**
	 * Handle.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param command
	 *            the command
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	public void handle(ScreenMode screenMode, WorkTimeCommonSaveCommand command) {

		// Get company ID
		String companyId = AppContexts.user().companyId();
		// get work time setting by client send
		WorkTimeSetting workTimeSetting = command.toDomainWorkTimeSetting();
		// get work time display mode
		WorkTimeDisplayMode displayMode = command.toWorkTimeDisplayMode();
		// get pred setting by client send
		PredetemineTimeSetting predseting = command.toDomainPredetemineTimeSetting();

		// check is add mode
		if (command.isAddMode()) {
			// call repository add predetemine time setting
			predseting.correctDefaultData(screenMode, command.getWorktimeSetting().getWorkTimeDivision());

			// Validate
			this.validate(command, workTimeSetting, predseting);

			// Call repository add work time setting
			this.workTimeSettingRepository.add(workTimeSetting);
			this.workTimeDisplayModeRepository.add(displayMode);
			this.predetemineTimeSettingRepository.add(predseting);
		} else {
			// call repository update predetemine time setting
			Optional<PredetemineTimeSetting> opPredetemineTimeSetting = this.predetemineTimeSettingRepository
					.findByWorkTimeCode(companyId, command.getWorktimeSetting().worktimeCode);
			if (opPredetemineTimeSetting.isPresent()) {
				predseting.correctData(screenMode, command.getWorktimeSetting().getWorkTimeDivision(),
						opPredetemineTimeSetting.get());

				// Validate
				this.validate(command, workTimeSetting, predseting);

				// Call repository add work time setting
				this.workTimeSettingRepository.update(workTimeSetting);
				this.workTimeDisplayModeRepository.update(displayMode);
				this.predetemineTimeSettingRepository.update(predseting);
			}
		}
	}

	/**
	 * Validate.
	 *
	 * @param command
	 *            the command
	 * @param workTimeSetting
	 *            the work time setting
	 * @param predseting
	 *            the predseting
	 */
	private void validate(WorkTimeCommonSaveCommand command, WorkTimeSetting workTimeSetting,
			PredetemineTimeSetting predseting) {
		BundledBusinessException bundledBusinessExceptions = BundledBusinessException.newInstance();

		// Check workTimeSetting domain
		try {
			workTimeSetting.validate();
		} catch (BundledBusinessException e) {
			bundledBusinessExceptions.addMessage(e.cloneExceptions());
		} catch (BusinessException e) {
			bundledBusinessExceptions.addMessage(e);
		}

		// Check predSetting domain
		try {
			predseting.validate();
		} catch (BundledBusinessException e) {
			bundledBusinessExceptions.addMessage(e.cloneExceptions());
		} catch (BusinessException e) {
			bundledBusinessExceptions.addMessage(e);
		}

		// check register
		if (command.isAddMode()) {
			this.workTimeSetPolicy.validateExist(bundledBusinessExceptions, workTimeSetting);
		}

		// Throw exceptions if exist
		if (!bundledBusinessExceptions.cloneExceptions().isEmpty()) {
			throw bundledBusinessExceptions;
		}
	}
}
