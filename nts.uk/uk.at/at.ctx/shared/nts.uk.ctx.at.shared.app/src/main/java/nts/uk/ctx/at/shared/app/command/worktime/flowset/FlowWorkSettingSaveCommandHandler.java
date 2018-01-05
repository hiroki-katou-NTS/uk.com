/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonSaveCommandHandler;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;

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
		// get command
		FlowWorkSettingSaveCommand command = context.getCommand();

		// convert dto to domain
		FlowWorkSetting flowWorkSetting = command.toDomainFlowWorkSetting();

		// check policy
		this.flowPolicy.validate(flowWorkSetting, command.toDomainPredetemineTimeSetting());

		// common handler
		this.commonHandler.handle(command);

		// call repository save flow work setting
		this.flowRepo.save(flowWorkSetting);
	}

}
