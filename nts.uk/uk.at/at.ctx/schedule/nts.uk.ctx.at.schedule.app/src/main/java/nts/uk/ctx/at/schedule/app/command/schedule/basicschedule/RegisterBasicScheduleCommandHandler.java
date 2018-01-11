package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Insert or Update data to DB BASIC_SCHEDULE. If error exist return error
 * 
 * @author sonnh1
 */
@RequestScoped
public class RegisterBasicScheduleCommandHandler
		extends CommandHandlerWithResult<List<RegisterBasicScheduleCommand>, List<String>> {
	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepo;

	@Inject
	private BasicScheduleRepository basicScheduleRepo;

	@Inject
	private BasicScheduleService basicScheduleService;

	@Override
	protected List<String> handle(CommandHandlerContext<List<RegisterBasicScheduleCommand>> context) {
		List<String> errList = new ArrayList<String>();

		String companyId = AppContexts.user().companyId();
		List<RegisterBasicScheduleCommand> bScheduleCommand = context.getCommand();

		List<String> listWorkTypeCode = bScheduleCommand.stream().map(x -> {
			return x.getWorkTypeCode();
		}).collect(Collectors.toList());
		List<String> listWorkTimeCode = bScheduleCommand.stream().map(x -> {
			return x.getWorkTimeCode();
		}).collect(Collectors.toList());

		List<WorkType> listWorkType = workTypeRepo.getPossibleWorkType(companyId, listWorkTypeCode);
		List<WorkTimeSetting> listWorkTime = workTimeSettingRepo.findByCodes(companyId, listWorkTimeCode);

		Map<String, WorkType> workTypeMap = listWorkType.stream().collect(Collectors.toMap(x -> {
			return x.getWorkTypeCode().v();
		}, x -> x));

		Map<String, WorkTimeSetting> workTimeMap = listWorkTime.stream().collect(Collectors.toMap(x -> {
			return x.getWorktimeCode().v();
		}, x -> x));

		for (RegisterBasicScheduleCommand bSchedule : bScheduleCommand) {
			BasicSchedule basicScheduleObj = bSchedule.toDomain();

			// Check WorkType
			WorkType workType = workTypeMap.get(bSchedule.getWorkTypeCode());

			if (workType == null) {
				// set error to list
				addMessage(errList, "Msg_436");
				continue;
			}

			if (workType.getDeprecate() == DeprecateClassification.Deprecated) {
				// set error to list
				addMessage(errList, "Msg_468");
				continue;
			}

			// Check WorkTime
			WorkTimeSetting workTimeSetting = workTimeMap.get(bSchedule.getWorkTimeCode());

			if (!StringUtil.isNullOrEmpty(bSchedule.getWorkTimeCode(), true)
					&& !("000").equals(bSchedule.getWorkTimeCode())) {

				if (workTimeSetting == null) {
					// Set error to list
					addMessage(errList, "Msg_437");
					continue;
				}

				if (workTimeSetting.getAbolishAtr().value == AbolishAtr.ABOLISH.value) {
					// Set error to list
					addMessage(errList, "Msg_469");
					continue;
				}
			}

			// Check workType-workTime
			try {
				if (workTimeSetting == null) {
					basicScheduleService.checkPairWorkTypeWorkTime(workType.getWorkTypeCode().v(),
							bSchedule.getWorkTimeCode());
				} else {
					basicScheduleService.checkPairWorkTypeWorkTime(workType.getWorkTypeCode().v(),
							workTimeSetting.getWorktimeCode().v());
				}
			} catch (RuntimeException ex) {
				BusinessException businessException = (BusinessException) ex.getCause();
				addMessage(errList, businessException.getMessageId());
				continue;
			}

			// Check exist of basicSchedule
			Optional<BasicSchedule> basicSchedule = basicScheduleRepo.find(bSchedule.getEmployeeId(),
					bSchedule.getDate());
			// Insert/Update
			if (basicSchedule.isPresent()) {
				// Flag to determine whether to handle
				// the update function or not
				boolean isAllowUpdate = true;
				List<WorkScheduleTimeZone> workScheduleTimeZones = basicScheduleObj.getWorkScheduleTimeZones();
				for (int i = 0; i < workScheduleTimeZones.size(); i++) {
					workScheduleTimeZones.get(i).validate();
					try {
						if (workScheduleTimeZones.get(i).getScheduleStartClock() != null
								|| workScheduleTimeZones.get(i).getScheduleEndClock() != null) {
							workScheduleTimeZones.get(i).validateTime();

							if (basicScheduleService.isReverseStartAndEndTime(
									workScheduleTimeZones.get(i).getScheduleStartClock(),
									workScheduleTimeZones.get(i).getScheduleEndClock())) {
								addMessage(errList, "Msg_441,KSU001_73,KSU001_74");
								isAllowUpdate = false;
							}
						}
					} catch (BusinessException ex) {
						addMessage(errList, ex.getMessageId());
						continue;
					}
				}

				if (isAllowUpdate) {
					basicScheduleRepo.update(basicScheduleObj);
				}
			} else {
				basicScheduleRepo.insert(basicScheduleObj);
			}
		}
		return errList;
	}

	/**
	 * Add exception message
	 * 
	 * @param exceptions
	 * @param messageId
	 */
	private void addMessage(List<String> errorsList, String messageId) {
		if (!errorsList.contains(messageId)) {
			errorsList.add(messageId);
		}
	}
}
