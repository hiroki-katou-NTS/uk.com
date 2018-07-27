/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.fixedset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonSaveCommandHandler;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.policy.FixedWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FixedWorkSettingSaveCommandHandler.
 */
@Stateless
public class FixedWorkSettingSaveCommandHandler extends CommandHandler<FixedWorkSettingSaveCommand> {

	/** The fixed work setting repository. */
	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;

	/** The common handler. */
	@Inject
	private WorkTimeCommonSaveCommandHandler commonHandler;

	/** The fixed policy. */
	@Inject
	private FixedWorkSettingPolicy fixedPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<FixedWorkSettingSaveCommand> context) {

		// Get company ID
		String companyId = AppContexts.user().companyId();
		// Get command
		FixedWorkSettingSaveCommand command = context.getCommand();
		// Convert dto to domain
		FixedWorkSetting fixedWorkSetting = command.toDomainFixedWorkSetting();

		// call repository save fixed work setting
		if (command.isAddMode()) {
			fixedWorkSetting.correctDefaultData(ScreenMode.valueOf(command.getScreenMode()));
			fixedWorkSetting.setDefaultData(ScreenMode.valueOf(command.getScreenMode()));
			// Validate + common handler
			this.validate(command, fixedWorkSetting);
			this.fixedWorkSettingRepository.add(fixedWorkSetting);
			return;
		}

		// update mode
		FixedWorkSetting oldDomain = this.fixedWorkSettingRepository
				.findByKey(companyId, command.getWorktimeSetting().worktimeCode).get();
		fixedWorkSetting.correctData(ScreenMode.valueOf(command.getScreenMode()),
				command.getWorktimeSetting().getWorkTimeDivision(), oldDomain);
		fixedWorkSetting.setDefaultData(ScreenMode.valueOf(command.getScreenMode()));
		// Validate + common handler
		this.validate(command, fixedWorkSetting);
		this.fixedWorkSettingRepository.update(fixedWorkSetting);
	}

	/**
	 * Validate.
	 *
	 * @param command
	 *            the command
	 * @param fixedWorkSetting
	 *            the fixed work setting
	 */
	private void validate(FixedWorkSettingSaveCommand command, FixedWorkSetting fixedWorkSetting) {
		BundledBusinessException bundledBusinessExceptions = BundledBusinessException.newInstance();

		// Check common handler
		try {
			this.commonHandler.handle(ScreenMode.valueOf(command.getScreenMode()), command);
		} catch (Exception e) {
			if (e.getCause() instanceof BundledBusinessException) {
				bundledBusinessExceptions.addMessage(((BundledBusinessException) e.getCause()).cloneExceptions());
			} else if (e.getCause() instanceof BusinessException) {
				bundledBusinessExceptions.addMessage((BusinessException) e.getCause());
			} else {
				throw e;
			}
		}

		// Check domain
		try {
			fixedWorkSetting.validate();
		} catch (BundledBusinessException e) {
			bundledBusinessExceptions.addMessage(e.cloneExceptions());
		} catch (BusinessException e) {
			bundledBusinessExceptions.addMessage(e);
		}

		// Check policy
		this.fixedPolicy.validate(bundledBusinessExceptions, command.toDomainPredetemineTimeSetting(),
				command.toWorkTimeDisplayMode(), fixedWorkSetting);

		// Throw exceptions if exist
		if (!bundledBusinessExceptions.cloneExceptions().isEmpty()) {
			throw bundledBusinessExceptions;
		}
	}
}
