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
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.BounceAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetCheck;
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
	private PredetemineTimeSettingRepository predetemineTimeSettingRepo;

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

			if (workType.isDeprecated()) {
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

				if (workTimeSetting.isAbolish()) {
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
			
			// process data schedule time soze
			this.addScheTimeZone(companyId, basicScheduleObj, workType);
			
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
	 * @param companyId
	 * @param basicScheduleObj
	 * @param workType
	 */
	private void addScheTimeZone(String companyId, BasicSchedule basicScheduleObj, WorkType workType) {
		List<WorkScheduleTimeZone> workScheduleTimeZones = new ArrayList<WorkScheduleTimeZone>();
		Optional<PredetemineTimeSetting> predetemineTimeSet = this.predetemineTimeSettingRepo
				.findByWorkTimeCode(companyId, basicScheduleObj.getWorkTimeCode());
		if (predetemineTimeSet.isPresent()) {
			List<TimezoneUse> listTimezoneUse = predetemineTimeSet.get().getPrescribedTimezoneSetting()
					.getLstTimezone();
			
			workScheduleTimeZones = listTimezoneUse.stream()
				.filter(x -> x.isUsed())
				.map((timezoneUse) -> {
					BounceAtr bounceAtr = addScheduleBounce(workType);
					return new WorkScheduleTimeZone(timezoneUse.getWorkNo(), timezoneUse.getStart(),
							timezoneUse.getEnd(), bounceAtr);
				}).collect(Collectors.toList());
		}
		
		basicScheduleObj.setWorkScheduleTimeZones(workScheduleTimeZones);
	}
	
	/**
	 * Add schedule bounce atr.
	 * @param workType
	 * @return
	 */
	private BounceAtr addScheduleBounce(WorkType workType) {
		List<WorkTypeSet> workTypeSetList = workType.getWorkTypeSetList();
		if (AttendanceHolidayAttr.FULL_TIME == workType.getAttendanceHolidayAttr()) {
			WorkTypeSet workTypeSet = workTypeSetList.get(0);
			return getBounceAtr(workTypeSet);
		} else if (AttendanceHolidayAttr.AFTERNOON == workType.getAttendanceHolidayAttr()) {
			WorkTypeSet workTypeSet1 = workTypeSetList.stream().filter(x -> WorkAtr.Afternoon.value == x.getWorkAtr().value).findFirst().get();
			return getBounceAtr(workTypeSet1);
		} else if (AttendanceHolidayAttr.MORNING == workType.getAttendanceHolidayAttr()) {
			WorkTypeSet workTypeSet2 = workTypeSetList.stream().filter(x -> WorkAtr.Monring.value == x.getWorkAtr().value).findFirst().get();
			return getBounceAtr(workTypeSet2);
		}
		
		return BounceAtr.NO_DIRECT_BOUNCE;
	}

	/**
	 * @param workTypeSet
	 * @return
	 */
	private BounceAtr getBounceAtr(WorkTypeSet workTypeSet) {
		if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.NO_CHECK && workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.NO_CHECK) {
			return BounceAtr.NO_DIRECT_BOUNCE;
		} else if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.CHECK && workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.NO_CHECK) {
			return BounceAtr.DIRECTLY_ONLY;
		} else if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.NO_CHECK && workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.CHECK) {
			return BounceAtr.BOUNCE_ONLY;
		} 
			
		return BounceAtr.DIRECT_BOUNCE;
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
