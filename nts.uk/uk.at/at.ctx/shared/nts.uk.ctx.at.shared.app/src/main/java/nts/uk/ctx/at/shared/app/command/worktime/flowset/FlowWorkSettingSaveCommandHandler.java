/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonSaveCommandHandler;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
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
	private FlWorkSettingPolicy flowPolicy;

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
		
		String companyId = AppContexts.user().companyId();
		
		// get command
		FlowWorkSettingSaveCommand command = context.getCommand();

		// convert dto to domain
		FlowWorkSetting flowWorkSetting = command.toDomainFlowWorkSetting();

		// check policy
		this.flowPolicy.validate(flowWorkSetting, command.toDomainPredetemineTimeSetting());

		// common handler
		this.commonHandler.handle(command);

		// call repository save flow work setting
		if (command.isAddMode()) {
			//TODOflowWorkSetting.restoreDefaultData(ScreenMode.valueOf(command.getScreenMode()));
			this.flowRepo.add(flowWorkSetting);
		} else {
			Optional<FlowWorkSetting> opFlowWorkSetting = this.flowRepo.find(companyId,
					command.getWorktimeSetting().worktimeCode);
			if (opFlowWorkSetting.isPresent()) {
				flowWorkSetting.restoreData(ScreenMode.valueOf(command.getScreenMode()),
						command.getWorktimeSetting().getWorkTimeDivision(), opFlowWorkSetting.get());
				this.flowRepo.update(flowWorkSetting);
			}
		}
	}

}
