/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;

/**
 * The Class WorkplaceBWSaveCommandHandler.
 */
@Stateless
public class WorkplaceBWSaveCommandHandler extends CommandHandler<WorkplaceBWSaveCommand> {

	/** The repository. */
	@Inject
	private WorkplaceBasicWorkRepository repository;

	/** The basic schedule service. */
	@Inject
	private BasicScheduleService basicScheduleService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkplaceBWSaveCommand> context) {

		// Get Command
		WorkplaceBWSaveCommand command = context.getCommand();

		// Get Workplace Id
		String workplaceId = command.getWorkplaceId();

		Optional<WorkplaceBasicWork> optional = this.repository.findById(workplaceId);

		// Check Worktype:
		BusinessException businessException = new BusinessException("Msg_178", "KSM006_6");
		command.getBasicWorkSetting().stream().forEach(item -> {
			switch (WorkdayDivision.valuesOf(item.getWorkDayDivision())) {
			case WORKINGDAYS:
				if (StringUtil.isNullOrEmpty(item.getWorkTypeCode(), true) || WorkStyle.ONE_DAY_REST
						.equals(basicScheduleService.checkWorkDay(item.getWorkTypeCode()))) {
					businessException.setSuppliment("KSM006_6",
							(new BusinessException("Msg_178", "KSM006_6")).getMessage());
				}
				break;
			case NON_WORKINGDAY_INLAW:
				if (StringUtil.isNullOrEmpty(item.getWorkTypeCode(), true) || WorkStyle.ONE_DAY_WORK
						.equals(basicScheduleService.checkWorkDay(item.getWorkTypeCode()))) {
					businessException.setSuppliment("KSM006_7",
							(new BusinessException("Msg_179", "KSM006_7")).getMessage());
				}
				break;
			case NON_WORKINGDAY_EXTRALEGAL:
				if (StringUtil.isNullOrEmpty(item.getWorkTypeCode(), true) || WorkStyle.ONE_DAY_WORK
						.equals(basicScheduleService.checkWorkDay(item.getWorkTypeCode()))) {
					businessException.setSuppliment("KSM006_8",
							(new BusinessException("Msg_179", "KSM006_8")).getMessage());
				}
				break;
			}
		});

		if (!businessException.getSupplements().isEmpty()) {
			throw businessException;
		}

		// check pair WorkTypeCode - WorkTimeCode require
		command.getBasicWorkSetting().stream().forEach(item -> {
			this.basicScheduleService.checkPairWorkTypeWorkTime(item.getWorkTypeCode(),
					item.getSiftCode());
		});

		// Convert to Domain
		WorkplaceBasicWork workplaceBasicWork = command.toDomain();

		// Check if exist
		if (optional.isPresent()) {
			this.repository.update(workplaceBasicWork);
		} else {
			this.repository.insert(workplaceBasicWork);
		}
	}
}
