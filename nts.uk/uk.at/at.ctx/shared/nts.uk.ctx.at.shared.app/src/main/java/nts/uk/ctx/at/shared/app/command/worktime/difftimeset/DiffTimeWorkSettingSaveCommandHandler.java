/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonSaveCommandHandler;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DiffTimeWorkSettingSaveCommandHandler.
 */
@Stateless
public class DiffTimeWorkSettingSaveCommandHandler extends CommandHandler<DiffTimeWorkSettingSaveCommand> {

	/** The difftime repo. */
	@Inject
	private DiffTimeWorkSettingRepository difftimeRepo;

	/** The common handler. */
	@Inject
	private WorkTimeCommonSaveCommandHandler commonHandler;

	/** The difftime policy. */
	@Inject
	private DiffTimeWorkSettingPolicy difftimePolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<DiffTimeWorkSettingSaveCommand> context) {

		// Get company ID
		String companyId = AppContexts.user().companyId();
		// Get command
		DiffTimeWorkSettingSaveCommand command = context.getCommand();
		// Convert dto to domain
		DiffTimeWorkSetting difftimeWorkSetting = command.toDomainDiffTimeWorkSetting();

		// Validate + common handler
		this.validate(command, difftimeWorkSetting);
		// Validate
		// this.difftimePolicy.validate(command.toDomainPredetemineTimeSetting(),
		// difftimeWorkSetting);
		// command.toDomainPredetemineTimeSetting());

		// common handler
		//this.commonHandler.handle(command);

		// call repository save fixed work setting
		if (command.isAddMode()) {
			// difftimeWorkSetting.restoreDefaultData(ScreenMode.valueOf(command.getScreenMode()));
			this.difftimeRepo.add(difftimeWorkSetting);
		} else {
			Optional<DiffTimeWorkSetting> opDiffTimeWorkSetting = this.difftimeRepo.find(companyId,
					command.getWorktimeSetting().worktimeCode);
			if (opDiffTimeWorkSetting.isPresent()) {
				// difftimeWorkSetting.restoreData(ScreenMode.valueOf(command.getScreenMode()),
				// command.getWorktimeSetting().getWorkTimeDivision(),
				// opDiffTimeWorkSetting.get());
				this.difftimeRepo.update(difftimeWorkSetting);
			}
		}
	}

	/**
	 * Validate.
	 *
	 * @param command
	 *            the command
	 * @param diffTimeWorkSetting
	 *            the diff time work setting
	 */
	private void validate(DiffTimeWorkSettingSaveCommand command, DiffTimeWorkSetting diffTimeWorkSetting) {
		BundledBusinessException bundledBusinessExceptions = BundledBusinessException.newInstance();

		// Check common handler
		try {
			this.commonHandler.handle(command);
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
			diffTimeWorkSetting.validate();
		} catch (BundledBusinessException e) {
			bundledBusinessExceptions.addMessage(e.cloneExceptions());
		} catch (BusinessException e) {
			bundledBusinessExceptions.addMessage(e);
		}

		// Check policy
		this.difftimePolicy.validate(bundledBusinessExceptions, command.toDomainPredetemineTimeSetting(),
				diffTimeWorkSetting);

		// Throw exceptions if exist
		if (!bundledBusinessExceptions.cloneExceptions().isEmpty()) {
			throw bundledBusinessExceptions;
		}
	}
}
