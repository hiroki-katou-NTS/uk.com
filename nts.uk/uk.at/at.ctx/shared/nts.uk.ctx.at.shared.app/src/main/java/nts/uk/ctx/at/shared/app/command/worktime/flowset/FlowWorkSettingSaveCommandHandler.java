/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonSaveCommandHandler;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FlowWorkSettingSaveCommandHandler.
 */
@Stateless
public class FlowWorkSettingSaveCommandHandler extends CommandHandler<FlowWorkSettingSaveCommand> {

	/** The flow repo. */
	@Inject
	private FlowWorkSettingRepository flowRepo;

	/** The common handler. */
	@Inject
	private WorkTimeCommonSaveCommandHandler commonHandler;

	/** The flow policy. */
	@Inject
	private FlowWorkSettingPolicy flowPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<FlowWorkSettingSaveCommand> context) {

		// Get company ID
		String companyId = AppContexts.user().companyId();
		// Get command
		FlowWorkSettingSaveCommand command = context.getCommand();
		// Convert dto to domain
		FlowWorkSetting flowWorkSetting = command.toDomainFlowWorkSetting();
		
		// call repository save flow work setting
		if (command.isAddMode()) {
			flowWorkSetting.correctDefaultData(ScreenMode.valueOf(command.getScreenMode()));
			flowWorkSetting.setDefaultData(ScreenMode.valueOf(command.getScreenMode()));
			// Validate + common handler
			this.validate(command, flowWorkSetting);
			this.flowRepo.add(flowWorkSetting);
			return;
		}

		// update mode
		FlowWorkSetting oldDomain = this.flowRepo.find(companyId, command.getWorktimeSetting().worktimeCode).get();
		flowWorkSetting.correctData(ScreenMode.valueOf(command.getScreenMode()),
				command.getWorktimeSetting().getWorkTimeDivision(), oldDomain);
		flowWorkSetting.setDefaultData(ScreenMode.valueOf(command.getScreenMode()));
		// Validate + common handler
		this.validate(command, flowWorkSetting);
		this.flowRepo.update(flowWorkSetting);
	}

	/**
	 * Validate.
	 *
	 * @param command
	 *            the command
	 * @param flowWorkSetting
	 *            the flow work setting
	 */
	private void validate(FlowWorkSettingSaveCommand command, FlowWorkSetting flowWorkSetting) {
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
			flowWorkSetting.validate();
		} catch (BundledBusinessException e) {
			bundledBusinessExceptions.addMessage(e.cloneExceptions());
		} catch (BusinessException e) {
			bundledBusinessExceptions.addMessage(e);
		}

		// Check policy
		this.flowPolicy.validate(bundledBusinessExceptions, command.toDomainPredetemineTimeSetting(),
				command.toWorkTimeDisplayMode(), flowWorkSetting);

		// Throw exceptions if exist
		if (!bundledBusinessExceptions.cloneExceptions().isEmpty()) {
			throw bundledBusinessExceptions;
		}
	}
}
