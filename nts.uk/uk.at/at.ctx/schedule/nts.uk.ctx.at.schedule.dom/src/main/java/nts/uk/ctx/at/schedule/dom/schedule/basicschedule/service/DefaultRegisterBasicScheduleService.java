package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.adapter.employment.EmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.employment.ScEmploymentAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.EmployeeJobHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.workplace.SWkpHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.BounceAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfo;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetCheck;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 登録ボタン押下時処理
 * 
 * @author sonnh1
 *
 */
@Stateless
public class DefaultRegisterBasicScheduleService implements RegisterBasicScheduleService {
	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepo;

	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepo;

	@Inject
	private BasicScheduleRepository basicScheduleRepo;

	// TODO: need check again this service, can move to package
	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private ScEmploymentAdapter scEmploymentAdapter;

	@Inject
	private SyJobTitleAdapter syJobTitleAdapter;

	@Inject
	private SyWorkplaceAdapter syWorkplaceAdapter;

	@Override
	public List<String> register(String companyId, List<BasicSchedule> basicScheduleList) {
		List<String> errList = new ArrayList<>();

		List<String> listWorkTypeCode = basicScheduleList.stream().map(x -> {
			return x.getWorkTypeCode();
		}).distinct().collect(Collectors.toList());
		List<String> listWorkTimeCode = basicScheduleList.stream().map(x -> {
			return x.getWorkTimeCode();
		}).distinct().collect(Collectors.toList());

		List<WorkType> listWorkType = workTypeRepo.getPossibleWorkType(companyId, listWorkTypeCode);
		List<WorkTimeSetting> listWorkTime = workTimeSettingRepo.findByCodes(companyId, listWorkTimeCode);

		Map<String, WorkType> workTypeMap = listWorkType.stream().collect(Collectors.toMap(x -> {
			return x.getWorkTypeCode().v();
		}, x -> x));

		Map<String, WorkTimeSetting> workTimeMap = listWorkTime.stream().collect(Collectors.toMap(x -> {
			return x.getWorktimeCode().v();
		}, x -> x));

		for (BasicSchedule bSchedule : basicScheduleList) {
			// get work type
			WorkType workType = workTypeMap.get(bSchedule.getWorkTypeCode());

			// check work type
			if (!checkWorkType(errList, workType)) {
				continue;
			}

			// get work time
			WorkTimeSetting workTimeSetting = workTimeMap.get(bSchedule.getWorkTimeCode());

			if (!StringUtil.isNullOrEmpty(bSchedule.getWorkTimeCode(), true)) {
				if (!checkWorkTime(errList, workTimeSetting)) {
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
			} catch (BusinessException ex) {
				addMessage(errList, ex.getMessageId());
				continue;
			}

			// get schedule time zone from user input
			List<WorkScheduleTimeZone> workScheduleTimeZonesCommand = new ArrayList<>(
					bSchedule.getWorkScheduleTimeZones());

			// process data schedule time zone
			this.addScheTimeZone(companyId, bSchedule, workType);

			// add ScheMaster for object basicSchedule
			this.addScheMaster(companyId, bSchedule);

			// Check exist of basicSchedule
			Optional<BasicSchedule> basicSchedule = basicScheduleRepo.find(bSchedule.getEmployeeId(),
					bSchedule.getDate());

			// Insert/Update
			if (basicSchedule.isPresent()) {
				// Flag to determine whether to handle
				// the update function or not
				if (!CollectionUtil.isEmpty(workScheduleTimeZonesCommand)) {
					// update again data time zone for case user update start
					// time, end time (mode show time)
					if (!checkTimeZone(errList, workScheduleTimeZonesCommand)) {
						continue;
					}

					// update again data time zone for case user update start
					// time, end time (mode show time)
					List<WorkScheduleTimeZone> timeZonesNew = new ArrayList<>();
					bSchedule.getWorkScheduleTimeZones().forEach(item -> {
						if (item.getScheduleCnt() == 1) {
							WorkScheduleTimeZone timeZone = workScheduleTimeZonesCommand.get(0);
							item.updateTime(timeZone.getScheduleStartClock(), timeZone.getScheduleEndClock());
						}
						timeZonesNew.add(item);
					});

					bSchedule.setWorkScheduleTimeZones(timeZonesNew);
				}

				basicScheduleRepo.update(bSchedule);
			} else {
				basicScheduleRepo.insert(bSchedule);
			}
		}
		return errList;
	}

	/**
	 * Check time zone for case update (mode show time)
	 * 
	 * @param errList
	 * @param workScheduleTimeZonesCommand
	 * @return
	 */
	private boolean checkTimeZone(List<String> errList, List<WorkScheduleTimeZone> workScheduleTimeZonesCommand) {
		WorkScheduleTimeZone timeZone = workScheduleTimeZonesCommand.get(0);
		timeZone.validate();
		Map<String, String> msgErrMap = timeZone.validateTime();
		if (!msgErrMap.isEmpty()) {
			for (Map.Entry<String, String> m : msgErrMap.entrySet()) {
				addMessage(errList, m.getValue() + "," + m.getKey());
			}
			return false;
		}

		if (basicScheduleService.isReverseStartAndEndTime(timeZone.getScheduleStartClock(),
				timeZone.getScheduleEndClock())) {
			addMessage(errList, "Msg_441,KSU001_73,KSU001_74");
			return false;
		}

		return true;
	}

	/**
	 * Check work time
	 * 
	 * @param errList
	 * @param workTimeSetting
	 */
	private boolean checkWorkTime(List<String> errList, WorkTimeSetting workTimeSetting) {
		if (workTimeSetting == null) {
			// Set error to list
			addMessage(errList, "Msg_437");
			return false;
		}

		if (workTimeSetting.isAbolish()) {
			// Set error to list
			addMessage(errList, "Msg_469");
			return false;
		}

		return true;
	}

	/**
	 * Check work type
	 * 
	 * @param errList
	 * @param workType
	 */
	private boolean checkWorkType(List<String> errList, WorkType workType) {
		if (workType == null) {
			// set error to list
			addMessage(errList, "Msg_436");
			return false;
		}

		if (workType.isDeprecated()) {
			// set error to list
			addMessage(errList, "Msg_468");
			return false;
		}

		return true;
	}

	/**
	 * @param companyId
	 * @param basicScheduleObj
	 * @param workType
	 */
	private void addScheTimeZone(String companyId, BasicSchedule basicScheduleObj, WorkType workType) {
		List<WorkScheduleTimeZone> workScheduleTimeZones = new ArrayList<WorkScheduleTimeZone>();
		BounceAtr bounceAtr = addScheduleBounce(workType);
		Optional<PredetemineTimeSetting> predetemineTimeSet = this.predetemineTimeSettingRepo
				.findByWorkTimeCode(companyId, basicScheduleObj.getWorkTimeCode());

		if (predetemineTimeSet.isPresent()) {
			PrescribedTimezoneSetting prescribedTimezoneSetting = predetemineTimeSet.get()
					.getPrescribedTimezoneSetting();
			List<TimezoneUse> listTimezoneUse = prescribedTimezoneSetting.getLstTimezone();
			TimezoneUse timezoneUseK1 = listTimezoneUse.stream().filter(x -> x.isUsed() && x.getWorkNo() == 1)
					.findFirst().get();
			Optional<TimezoneUse> timezoneUseK2 = listTimezoneUse.stream().filter(x -> x.isUsed() && x.getWorkNo() == 2)
					.findFirst();
			// if workTypeCode is work on morning, replace endTime = endTime of
			// morning
			if (basicScheduleService.checkWorkDay(basicScheduleObj.getWorkTypeCode()) == WorkStyle.MORNING_WORK) {
				TimeWithDayAttr morningEndTime = prescribedTimezoneSetting.getMorningEndTime();
				if (morningEndTime.valueAsMinutes() <= timezoneUseK1.getEnd().valueAsMinutes()) {
					workScheduleTimeZones.add(new WorkScheduleTimeZone(timezoneUseK1.getWorkNo(),
							timezoneUseK1.getStart(), morningEndTime, bounceAtr));
				} else {
					listTimezoneUse.forEach((timezoneUse) -> {
						if (timezoneUse.getWorkNo() == 2) {
							workScheduleTimeZones.add(new WorkScheduleTimeZone(timezoneUse.getWorkNo(),
									timezoneUse.getStart(), morningEndTime, bounceAtr));
						} else {
							workScheduleTimeZones.add(new WorkScheduleTimeZone(timezoneUse.getWorkNo(),
									timezoneUse.getStart(), timezoneUse.getEnd(), bounceAtr));
						}
					});
				}
			} else
			// if workTypeCode is work on afternoon, replace startTime =
			// startTime of afternoon
			if (basicScheduleService.checkWorkDay(basicScheduleObj.getWorkTypeCode()) == WorkStyle.AFTERNOON_WORK) {
				TimeWithDayAttr afternoonStartTime = prescribedTimezoneSetting.getAfternoonStartTime();

				// if (!timezoneUseK2.isPresent()) {
				if (afternoonStartTime.valueAsMinutes() <= timezoneUseK1.getEnd().valueAsMinutes()) {
					// workScheduleTimeZones =
					// listTimezoneUse.stream().map((timezoneUse) -> {
					// if (timezoneUse.getWorkNo() == 1) {
					// return new WorkScheduleTimeZone(timezoneUse.getWorkNo(),
					// afternoonStartTime,
					// timezoneUse.getEnd(), bounceAtr);
					// } else if (timezoneUse.getWorkNo() == 2 &&
					// timezoneUseK2.isPresent()) {
					// return new WorkScheduleTimeZone(timezoneUse.getWorkNo(),
					// timezoneUse.getStart(),
					// timezoneUse.getEnd(), bounceAtr);
					// }
					// }).collect(Collectors.toList());

					listTimezoneUse.forEach(timezoneUse -> {
						if (timezoneUse.getWorkNo() == 1) {
							workScheduleTimeZones.add(new WorkScheduleTimeZone(timezoneUse.getWorkNo(),
									afternoonStartTime, timezoneUse.getEnd(), bounceAtr));
						} else if (timezoneUse.getWorkNo() == 2 && timezoneUseK2.isPresent()) {
							workScheduleTimeZones.add(new WorkScheduleTimeZone(timezoneUse.getWorkNo(),
									timezoneUse.getStart(), timezoneUse.getEnd(), bounceAtr));
						}
					});
				} else {
					workScheduleTimeZones.add(new WorkScheduleTimeZone(timezoneUseK2.get().getWorkNo(),
							afternoonStartTime, timezoneUseK2.get().getEnd(), bounceAtr));
				}
				// }

			} else {
				listTimezoneUse.stream().filter(x -> x.isUsed()).forEach((timezoneUse) -> {
					workScheduleTimeZones.add(new WorkScheduleTimeZone(timezoneUse.getWorkNo(), timezoneUse.getStart(),
							timezoneUse.getEnd(), bounceAtr));
				});
			}
		}

		basicScheduleObj.setWorkScheduleTimeZones(workScheduleTimeZones);
	}

	/**
	 * Add schedule bounce atr.
	 * 
	 * @param workType
	 * @return
	 */
	private BounceAtr addScheduleBounce(WorkType workType) {
		List<WorkTypeSet> workTypeSetList = workType.getWorkTypeSetList();
		if (AttendanceHolidayAttr.FULL_TIME == workType.getAttendanceHolidayAttr()) {
			WorkTypeSet workTypeSet = workTypeSetList.get(0);
			return getBounceAtr(workTypeSet);
		} else if (AttendanceHolidayAttr.AFTERNOON == workType.getAttendanceHolidayAttr()) {
			WorkTypeSet workTypeSet1 = workTypeSetList.stream()
					.filter(x -> WorkAtr.Afternoon.value == x.getWorkAtr().value).findFirst().get();
			return getBounceAtr(workTypeSet1);
		} else if (AttendanceHolidayAttr.MORNING == workType.getAttendanceHolidayAttr()) {
			WorkTypeSet workTypeSet2 = workTypeSetList.stream()
					.filter(x -> WorkAtr.Monring.value == x.getWorkAtr().value).findFirst().get();
			return getBounceAtr(workTypeSet2);
		}

		return BounceAtr.NO_DIRECT_BOUNCE;
	}

	/**
	 * 
	 * @param companyId
	 * @param basicSchedule
	 */
	private void addScheMaster(String companyId, BasicSchedule basicSchedule) {
		String workplaceId = null;
		String employmentCd = null;
		String jobId = null;
		String employeeId = basicSchedule.getEmployeeId();
		GeneralDate baseDate = basicSchedule.getDate();

		Optional<EmploymentHistoryImported> employmentHistoryImported = this.scEmploymentAdapter
				.getEmpHistBySid(companyId, employeeId, baseDate);
		if (employmentHistoryImported.isPresent()) {
			employmentCd = employmentHistoryImported.get().getEmploymentCode();
		}

		Optional<EmployeeJobHistImported> employeeJobHistImported = this.syJobTitleAdapter.findBySid(employeeId,
				baseDate);
		if (employeeJobHistImported.isPresent()) {
			jobId = employeeJobHistImported.get().getJobTitleID();
		}

		Optional<SWkpHistImported> sWkpHistImported = this.syWorkplaceAdapter.findBySid(employeeId, baseDate);
		if (sWkpHistImported.isPresent()) {
			workplaceId = sWkpHistImported.get().getWorkplaceId();
		}

		// now, set classificationCd and businessTypeCd = null
		ScheMasterInfo scheMasterInfo = new ScheMasterInfo(employeeId, baseDate, employmentCd, null, null, jobId,
				workplaceId);

		basicSchedule.setWorkScheduleMaster(scheMasterInfo);
	}

	/**
	 * @param workTypeSet
	 * @return
	 */
	private BounceAtr getBounceAtr(WorkTypeSet workTypeSet) {
		if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.NO_CHECK
				&& workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.NO_CHECK) {
			return BounceAtr.NO_DIRECT_BOUNCE;
		} else if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.CHECK
				&& workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.NO_CHECK) {
			return BounceAtr.DIRECTLY_ONLY;
		} else if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.NO_CHECK
				&& workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.CHECK) {
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
