/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flexset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonSaveCommandHandler;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.policy.FlexWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FlexWorkSettingSaveCommandHandler.
 */
@Stateless
public class FlexWorkSettingSaveCommandHandler extends CommandHandler<FlexWorkSettingSaveCommand> {

	/** The flex work setting repository. */
	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;

	/** The flex policy. */
	@Inject
	private FlexWorkSettingPolicy flexPolicy;

	/** The common handler. */
	@Inject
	private WorkTimeCommonSaveCommandHandler commonHandler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<FlexWorkSettingSaveCommand> context) {

		// Get company ID
		String companyId = AppContexts.user().companyId();
		// Get command
		FlexWorkSettingSaveCommand command = context.getCommand();
		// Convert dto to domain
		FlexWorkSetting flexWorkSetting = command.toDomainFlexWorkSetting();

		// check is add mode
		if (command.isAddMode()) {
			flexWorkSetting.correctDefaultData(ScreenMode.valueOf(command.getScreenMode()));
			// Validate + common handler
			this.validate(command, flexWorkSetting);
			this.flexWorkSettingRepository.add(flexWorkSetting);
			return;
		}

		// update mode
		FlexWorkSetting oldDomain = this.flexWorkSettingRepository
				.find(companyId, command.getWorktimeSetting().worktimeCode).get();
		flexWorkSetting.correctData(ScreenMode.valueOf(command.getScreenMode()),
				command.getWorktimeSetting().getWorkTimeDivision(), oldDomain);
		// Validate + common handler
		this.validate(command, flexWorkSetting);
		this.flexWorkSettingRepository.update(flexWorkSetting);
	}

	/**
	 * Validate.
	 *
	 * @param command
	 *            the command
	 * @param flexWorkSetting
	 *            the flex work setting
	 */
	private void validate(FlexWorkSettingSaveCommand command, FlexWorkSetting flexWorkSetting) {
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
			flexWorkSetting.validate();
		} catch (BundledBusinessException e) {
			bundledBusinessExceptions.addMessage(e.cloneExceptions());
		} catch (BusinessException e) {
			bundledBusinessExceptions.addMessage(e);
		}

		// Check policy
		this.flexPolicy.validate(bundledBusinessExceptions, command.toDomainPredetemineTimeSetting(),
				command.toWorkTimeDisplayMode(), flexWorkSetting);

		// Throw exceptions if exist
		if (!bundledBusinessExceptions.cloneExceptions().isEmpty()) {
			throw bundledBusinessExceptions;
		}
	}
}
