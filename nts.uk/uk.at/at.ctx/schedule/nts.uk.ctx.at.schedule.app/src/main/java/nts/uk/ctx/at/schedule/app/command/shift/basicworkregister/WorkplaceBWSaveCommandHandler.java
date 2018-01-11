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
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

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

	/** The monthly pattern repository. */
	@Inject
	private WorkTimeSettingRepository workTimeRepository;

	/** The monthly pattern repository. */
	@Inject
	private WorkTypeRepository workTypeRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkplaceBWSaveCommand> context) {
		// get company id user login
		String companyId = AppContexts.user().companyId();

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
				if (!this.checkExistWorkDay(companyId, item.getWorkTypeCode(), item.getSiftCode())
						|| WorkStyle.ONE_DAY_REST.equals(
								basicScheduleService.checkWorkDay(item.getWorkTypeCode()))) {
					businessException.setSuppliment("KSM006_6",
							(new BusinessException("Msg_178", "KSM006_6")).getMessage());
				}
				break;
			case NON_WORKINGDAY_INLAW:
				if (!this.checkExistWorkDay(companyId, item.getWorkTypeCode(), item.getSiftCode())
						|| WorkStyle.ONE_DAY_WORK.equals(
								basicScheduleService.checkWorkDay(item.getWorkTypeCode()))) {
					businessException.setSuppliment("KSM006_7",
							(new BusinessException("Msg_179", "KSM006_7")).getMessage());
				}
				break;
			case NON_WORKINGDAY_EXTRALEGAL:
				if (!this.checkExistWorkDay(companyId, item.getWorkTypeCode(), item.getSiftCode())
						|| WorkStyle.ONE_DAY_WORK.equals(
								basicScheduleService.checkWorkDay(item.getWorkTypeCode()))) {
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

	/**
	 * Check exist work day.
	 *
	 * @param companyId
	 *            the company id
	 * @param workTypeCode
	 *            the work type code
	 * @param workingCode
	 *            the working code
	 * @return true, if successful
	 */
	private boolean checkExistWorkDay(String companyId, String workTypeCode, String workingCode) {
		// Check empty
		if (StringUtil.isNullOrEmpty(workTypeCode, true)) {
			return false;
		}

		// Check setting work type
		if (!StringUtil.isNullOrEmpty(workTypeCode, true)) {
			// check setting work type
			Optional<WorkType> optWorktype = this.workTypeRepository.findByPK(companyId,
					workTypeCode);

			// not exist data
			if (!optWorktype.isPresent()) {
				throw new BusinessException("Msg_389");
			}

			// not use
			if (optWorktype.get()
					.getDeprecate().value == DeprecateClassification.Deprecated.value) {
				throw new BusinessException("Msg_416");
			}

		}

		// check setting work time
		if (!StringUtil.isNullOrEmpty(workingCode, true)) {
			Optional<WorkTimeSetting> worktime = this.workTimeRepository.findByCode(companyId,
					workingCode);

			// not exist data
			if (!worktime.isPresent()) {
				throw new BusinessException("Msg_390");
			}

			// not use
//			if (worktime.get().getDispAtr().value == UseSetting.UseAtr_NotUse.value) {
//				throw new BusinessException("Msg_417");
//			}
		}

		return true;
	}

}
