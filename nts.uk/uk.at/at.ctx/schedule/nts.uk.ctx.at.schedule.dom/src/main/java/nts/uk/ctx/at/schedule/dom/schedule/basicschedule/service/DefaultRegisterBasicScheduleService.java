package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeImport;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeParam;
import nts.uk.ctx.at.schedule.dom.adapter.employment.EmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.employment.ScEmploymentAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.EmployeeJobHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.workplace.SWkpHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.schedule.algorithm.BusinessDayCal;
import nts.uk.ctx.at.schedule.dom.schedule.algorithm.CreScheWithBusinessDayCalService;
import nts.uk.ctx.at.schedule.dom.schedule.algorithm.WorkRestTimeZoneDto;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.ExtraTimeItemNo;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.ScheduledBreakCnt;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.PersonFeeTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.BounceAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.ScheduleEditState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetCheck;
import nts.uk.shr.com.context.AppContexts;
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

	@Inject
	private ScTimeAdapter scTimeAdapter;

	@Inject
	private CreScheWithBusinessDayCalService creScheWithBusinessDayCalService;

	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;

	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepository;

	@Inject
	private DiffTimeWorkSettingRepository diffTimeWorkSettingRepository;

	@Override
	public List<String> register(String companyId, Integer modeDisplay, List<BasicSchedule> basicScheduleList) {
		String employeeIdLogin = AppContexts.user().employeeId();
		List<String> errList = new ArrayList<>();

		List<String> listWorkTypeCode = basicScheduleList.stream().map(x -> {
			return x.getWorkTypeCode();
		}).distinct().collect(Collectors.toList());
		List<String> listWorkTimeCode = basicScheduleList.stream().map(x -> {
			return x.getWorkTimeCode();
		}).distinct().collect(Collectors.toList());

		// 勤務種類を取得する (Lấy dữ lieu loại làm việc)
		List<WorkType> listWorkType = workTypeRepo.getPossibleWorkType(companyId, listWorkTypeCode);
		// 就業時間帯を取得する (Lấy dữ liệu thời gian làm việc)
		List<WorkTimeSetting> listWorkTime = workTimeSettingRepo.findByCodes(companyId, listWorkTimeCode);

		Map<String, WorkType> workTypeMap = listWorkType.stream().collect(Collectors.toMap(x -> {
			return x.getWorkTypeCode().v();
		}, x -> x));

		Map<String, WorkTimeSetting> workTimeMap = listWorkTime.stream().collect(Collectors.toMap(x -> {
			return x.getWorktimeCode().v();
		}, x -> x));

		Map<String, WorkRestTimeZoneDto> mapFixedWorkSetting = new HashMap<>();
		Map<String, WorkRestTimeZoneDto> mapFlowWorkSetting = new HashMap<>();
		Map<String, WorkRestTimeZoneDto> mapDiffTimeWorkSetting = new HashMap<>();

		List<Integer> startClock = new ArrayList<>();
		List<Integer> endClock = new ArrayList<>();
		List<Integer> breakStartTime = new ArrayList<>();
		List<Integer> breakEndTime = new ArrayList<>();
		List<Integer> childCareStartTime = new ArrayList<>();
		List<Integer> childCareEndTime = new ArrayList<>();

		this.acquireData(companyId, listWorkType, listWorkTime, mapFixedWorkSetting, mapFlowWorkSetting,
				mapDiffTimeWorkSetting);

		for (BasicSchedule bSchedule : basicScheduleList) {
			String employeeId = bSchedule.getEmployeeId();
			GeneralDate date = bSchedule.getDate();
			String workTypeCode = bSchedule.getWorkTypeCode();
			String workTimeCode = bSchedule.getWorkTimeCode();
			// get work type
			WorkType workType = workTypeMap.get(workTypeCode);

			// 勤務種類のマスタチェック (Kiểm tra phan loại ngày làm việc)
			if (!checkWorkType(errList, workType)) {
				continue;
			}

			// get work time
			WorkTimeSetting workTimeSetting = workTimeMap.get(workTimeCode);

			if (!StringUtil.isNullOrEmpty(workTimeCode, true)) {
				// 就業時間帯のマスタチェック (Kiểm tra giờ làm việc)
				if (!checkWorkTime(errList, workTimeSetting)) {
					continue;
				}
			}

			// 勤務種類と就業時間帯のペアチェック (Kiểm tra cặp)
			try {
				if (workTimeSetting == null) {
					basicScheduleService.checkPairWorkTypeWorkTime(workTypeCode, workTimeCode);
				} else {
					basicScheduleService.checkPairWorkTypeWorkTime(workTypeCode, workTimeSetting.getWorktimeCode().v());
				}
			} catch (BusinessException ex) {
				addMessage(errList, ex.getMessageId());
				continue;
			}

			// Check exist of basicSchedule
			Optional<BasicSchedule> basicSchedule = basicScheduleRepo.findWithAllChild(employeeId, date);

			/****************************************************************/

			// Update/Insert
			// get schedule time zone from user input
			boolean isInsertMode = true;
			List<WorkScheduleTimeZone> workScheduleTimeZonesCommand = new ArrayList<>(
					bSchedule.getWorkScheduleTimeZones());
			if (basicSchedule.isPresent()) {
				BasicSchedule basicSche = basicSchedule.get();
				isInsertMode = false;
				// UPDATE
				// from workTimeCd != null to workTimeCd = null
				if (basicSche.getWorkTimeCode() != null && workTimeSetting == null) {
					basicScheduleRepo.updateScheBasic(bSchedule);
					basicScheduleRepo.deleteWithWorkTimeCodeNull(employeeId, date);
				} else if (basicSche.getWorkTimeCode() == null && workTimeSetting == null) {
					// from workTimeCd = null to workTimeCd = null
					basicScheduleRepo.updateScheBasic(bSchedule);
				} else {
					if (workTimeSetting != null) {
						if (modeDisplay.intValue() == 2) {
							// TODO
						}

						// add new scheTimeZone
						if (!CollectionUtil.isEmpty(workScheduleTimeZonesCommand)) {
							// update again data time zone for case user update
							// start time, end time (mode show time)
							if (!checkTimeZone(errList, workScheduleTimeZonesCommand)) {
								continue;
							}

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
						// add timeZone
						this.addScheTimeZone(companyId, bSchedule, workType, listWorkType);
						// add breakTime
						this.addBreakTime(companyId, workTypeCode, workTimeCode, listWorkType, listWorkTime, bSchedule,
								mapFixedWorkSetting, mapFlowWorkSetting, mapDiffTimeWorkSetting);
						// add scheTime
						workScheduleTimeZonesCommand.forEach(x -> {
							startClock.add(x.getScheduleStartClock().v());
							endClock.add(x.getScheduleEndClock().v());
						});

						bSchedule.getWorkScheduleBreaks().forEach(x -> {
							breakStartTime.add(x.getScheduledStartClock().v());
							breakEndTime.add(x.getScheduledEndClock().v());
						});

						bSchedule.getChildCareSchedules().forEach(x -> {
							childCareStartTime.add(x.getChildCareScheduleStart().v());
							childCareEndTime.add(x.getChildCareScheduleEnd().v());
						});

						ScTimeParam param = new ScTimeParam(employeeId, date, workType.getWorkTypeCode(),
								workTimeSetting.getWorktimeCode(), startClock, endClock, breakStartTime, breakEndTime,
								childCareStartTime, childCareEndTime);
						this.addScheTime(param, bSchedule);
					}

					if (basicSche.getWorkTimeCode() == null) {
						// from workTimeCd = null to workTimeCd != null
						// update scheBasic
						basicScheduleRepo.updateScheBasic(bSchedule);
						// insert scheTimeZone
						basicScheduleRepo.insertScheTimeZone(bSchedule);
						// insert-update scheTime
						if (basicSche.getWorkScheduleTime().isPresent()) {
							basicScheduleRepo.updateScheTime(bSchedule);
						} else {
							basicScheduleRepo.insertScheTime(bSchedule);
						}
						// insert-update scheBreak
						if (CollectionUtil.isEmpty(basicSche.getWorkScheduleBreaks())) {
							basicScheduleRepo.insertScheBreak(bSchedule);
						} else {
							basicScheduleRepo.updateScheBreak(bSchedule);
						}
					} else {
						// from workTimeCd != null to workTimeCd != null
						this.addScheState(employeeIdLogin, bSchedule, isInsertMode, basicSche);
						basicScheduleRepo.update(bSchedule);
					}
				}
			} else {
				if (workTimeSetting != null) {
					// add timeZone
					this.addScheTimeZone(companyId, bSchedule, workType, listWorkType);
					// add breakTime
					this.addBreakTime(companyId, workTypeCode, workTimeCode, listWorkType, listWorkTime, bSchedule,
							mapFixedWorkSetting, mapFlowWorkSetting, mapDiffTimeWorkSetting);
					this.addChildCare(bSchedule);
					// add scheTime
					workScheduleTimeZonesCommand.forEach(x -> {
						startClock.add(x.getScheduleStartClock().v());
						endClock.add(x.getScheduleEndClock().v());
					});

					bSchedule.getWorkScheduleBreaks().forEach(x -> {
						breakStartTime.add(x.getScheduledStartClock().v());
						breakEndTime.add(x.getScheduledEndClock().v());
					});

					bSchedule.getChildCareSchedules().forEach(x -> {
						childCareStartTime.add(x.getChildCareScheduleStart().v());
						childCareEndTime.add(x.getChildCareScheduleEnd().v());
					});

					ScTimeParam param = new ScTimeParam(employeeId, date, workType.getWorkTypeCode(),
							workTimeSetting.getWorktimeCode(), startClock, endClock, breakStartTime, breakEndTime,
							childCareStartTime, childCareEndTime);
					this.addScheTime(param, bSchedule);
				}
				this.addScheMaster(companyId, bSchedule);
				this.addScheState(employeeIdLogin, bSchedule, isInsertMode, null);
				basicScheduleRepo.insert(bSchedule);
			}

			/****************************************************************/

			// get schedule time zone from user input
			// List<WorkScheduleTimeZone> workScheduleTimeZonesCommand = new
			// ArrayList<>(
			// bSchedule.getWorkScheduleTimeZones());
			//
			// // process data schedule time zone
			// this.addScheTimeZone(companyId, bSchedule, workType);
			//
			// this.addBreakTime(companyId, workTypeCode, workTimeCode,
			// listWorkType, listWorkTime, bSchedule);
			//
			// this.addChildCare();
			//
			// List<Integer> startClock = new ArrayList<>();
			// List<Integer> endClock = new ArrayList<>();
			// List<Integer> breakStartTime = new ArrayList<>();
			// List<Integer> breakEndTime = new ArrayList<>();
			// List<Integer> childCareStartTime = new ArrayList<>();
			// List<Integer> childCareEndTime = new ArrayList<>();
			//
			// workScheduleTimeZonesCommand.forEach(x -> {
			// startClock.add(x.getScheduleStartClock().v());
			// endClock.add(x.getScheduleEndClock().v());
			// });
			//
			// bSchedule.getWorkScheduleBreaks().forEach(x -> {
			// breakStartTime.add(x.getScheduledStartClock().v());
			// breakEndTime.add(x.getScheduledEndClock().v());
			// });
			//
			// bSchedule.getChildCareSchedules().forEach(x -> {
			// childCareStartTime.add(x.getChildCareScheduleStart().v());
			// childCareEndTime.add(x.getChildCareScheduleEnd().v());
			// });
			//
			// ScTimeParam param = new ScTimeParam(employeeId, date,
			// workType.getWorkTypeCode(),
			// workTimeSetting.getWorktimeCode(), startClock, endClock,
			// breakStartTime, breakEndTime,
			// childCareStartTime, childCareEndTime);
			// this.addScheTime(param, bSchedule);
			//
			// // add ScheMaster for object basicSchedule
			// this.addScheMaster(companyId, bSchedule);
			//
			// // Check exist of basicSchedule
			// Optional<BasicSchedule> basicSchedule =
			// basicScheduleRepo.find(employeeId, date);
			//
			// // Update/Insert
			// if (basicSchedule.isPresent()) {
			// // Flag to determine whether to handle
			// // the update function or not
			// if (!CollectionUtil.isEmpty(workScheduleTimeZonesCommand)) {
			// // update again data time zone for case user update start
			// // time, end time (mode show time)
			// if (!checkTimeZone(errList, workScheduleTimeZonesCommand)) {
			// continue;
			// }
			//
			// List<WorkScheduleTimeZone> timeZonesNew = new ArrayList<>();
			// bSchedule.getWorkScheduleTimeZones().forEach(item -> {
			// if (item.getScheduleCnt() == 1) {
			// WorkScheduleTimeZone timeZone =
			// workScheduleTimeZonesCommand.get(0);
			// item.updateTime(timeZone.getScheduleStartClock(),
			// timeZone.getScheduleEndClock());
			// }
			// timeZonesNew.add(item);
			// });
			//
			// bSchedule.setWorkScheduleTimeZones(timeZonesNew);
			// }
			//
			// basicScheduleRepo.update(bSchedule);
			// } else {
			// // add ScheState for object basicSchedule
			// this.addScheState(employeeIdLogin, bSchedule);
			//
			// basicScheduleRepo.insert(bSchedule);
			// }
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
	private void addScheTimeZone(String companyId, BasicSchedule basicScheduleObj, WorkType workType,
			List<WorkType> listWorkType) {
		List<WorkScheduleTimeZone> workScheduleTimeZones = new ArrayList<WorkScheduleTimeZone>();
		BounceAtr bounceAtr = addScheduleBounce(workType);
		Optional<PredetemineTimeSetting> predetemineTimeSet = this.predetemineTimeSettingRepo
				.findByWorkTimeCode(companyId, basicScheduleObj.getWorkTimeCode());

		if (!predetemineTimeSet.isPresent()) {
			return;
		}

		PrescribedTimezoneSetting prescribedTimezoneSetting = predetemineTimeSet.get().getPrescribedTimezoneSetting();
		List<TimezoneUse> listTimezoneUse = prescribedTimezoneSetting.getLstTimezone();
		TimezoneUse timezoneUseK1 = listTimezoneUse.stream().filter(x -> x.isUsed() && x.getWorkNo() == 1).findFirst()
				.get();
		Optional<TimezoneUse> timezoneUseK2 = listTimezoneUse.stream().filter(x -> x.isUsed() && x.getWorkNo() == 2)
				.findFirst();
		// if workTypeCode is work on morning, replace endTime = endTime of
		// morning
		if (basicScheduleService.checkWorkDayByList(basicScheduleObj.getWorkTypeCode(),
				listWorkType) == WorkStyle.MORNING_WORK) {
			TimeWithDayAttr morningEndTime = prescribedTimezoneSetting.getMorningEndTime();
			if (morningEndTime.valueAsMinutes() <= timezoneUseK1.getEnd().valueAsMinutes()) {
				workScheduleTimeZones.add(new WorkScheduleTimeZone(timezoneUseK1.getWorkNo(), timezoneUseK1.getStart(),
						morningEndTime, bounceAtr));
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
		} else if (basicScheduleService.checkWorkDayByList(basicScheduleObj.getWorkTypeCode(),
				listWorkType) == WorkStyle.AFTERNOON_WORK) {
			TimeWithDayAttr afternoonStartTime = prescribedTimezoneSetting.getAfternoonStartTime();
			// if workTypeCode is work on afternoon, replace startTime =
			// startTime
			// of afternoon
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
						workScheduleTimeZones.add(new WorkScheduleTimeZone(timezoneUse.getWorkNo(), afternoonStartTime,
								timezoneUse.getEnd(), bounceAtr));
					} else if (timezoneUse.getWorkNo() == 2 && timezoneUseK2.isPresent()) {
						workScheduleTimeZones.add(new WorkScheduleTimeZone(timezoneUse.getWorkNo(),
								timezoneUse.getStart(), timezoneUse.getEnd(), bounceAtr));
					}
				});
			} else {
				workScheduleTimeZones.add(new WorkScheduleTimeZone(timezoneUseK2.get().getWorkNo(), afternoonStartTime,
						timezoneUseK2.get().getEnd(), bounceAtr));
			}
			// }

		} else {
			listTimezoneUse.stream().filter(x -> x.isUsed()).forEach((timezoneUse) -> {
				workScheduleTimeZones.add(new WorkScheduleTimeZone(timezoneUse.getWorkNo(), timezoneUse.getStart(),
						timezoneUse.getEnd(), bounceAtr));
			});
		}

		basicScheduleObj.setWorkScheduleTimeZones(workScheduleTimeZones);
	}

	private void addBreakTime(String companyId, String workTypeCode, String workTimeCode, List<WorkType> listWorkType,
			List<WorkTimeSetting> listWorkTime, BasicSchedule bSchedule,
			Map<String, WorkRestTimeZoneDto> mapFixedWorkSetting, Map<String, WorkRestTimeZoneDto> mapFlowWorkSetting,
			Map<String, WorkRestTimeZoneDto> mapDiffTimeWorkSetting) {
		List<WorkScheduleBreak> listWorkScheduleBreak = new ArrayList<>();
		List<DeductionTime> listTimeZone = new ArrayList<>();

		List<DeductionTime> listScheTimeZone = bSchedule.getWorkScheduleTimeZones().stream()
				.map(x -> new DeductionTime(x.getScheduleStartClock(), x.getScheduleEndClock()))
				.collect(Collectors.toList());
		BusinessDayCal businessDayCal = this.creScheWithBusinessDayCalService.getScheduleBreakTime(companyId,
				workTypeCode, workTimeCode, listWorkType, listWorkTime, mapFixedWorkSetting, mapFlowWorkSetting,
				mapDiffTimeWorkSetting, listScheTimeZone);
		if (businessDayCal == null) {
			bSchedule.setWorkScheduleBreaks(listWorkScheduleBreak);
			return;
		}

		listTimeZone = businessDayCal.getTimezones();
		if (listTimeZone == null) {
			listTimeZone = new ArrayList<>();
		}

		for (int i = 0; i < listTimeZone.size(); i++) {
			WorkScheduleBreak wBreakSaveCommand = new WorkScheduleBreak(new ScheduledBreakCnt(i + 1),
					listTimeZone.get(i).getStart(), listTimeZone.get(i).getEnd());
			listWorkScheduleBreak.add(wBreakSaveCommand);
		}
		bSchedule.setWorkScheduleBreaks(listWorkScheduleBreak);
	}

	private void addChildCare(BasicSchedule bSchedule) {
		// List<ChildCareSchedule> listChildCareSchedule = new ArrayList<>();
		// listChildCareSchedule.add(new
		// ChildCareSchedule(EnumAdaptor.valueOf(0,
		// ChildCareScheduleRound.class), null , null, EnumAdaptor.valueOf(0,
		// ChildCareAtr.class)));
		// bSchedule.setChildCareSchedules(listChildCareSchedule);
	}

	private void addScheTime(ScTimeParam param, BasicSchedule bSchedule) {
		ScTimeImport scTimeImport = this.scTimeAdapter.calculation(param);
		List<AttendanceTime> listPersonFeeTime = scTimeImport.getPersonalExpenceTime();
		List<PersonFeeTime> personFeeTime = new ArrayList<>();
		for (int i = 0; i < listPersonFeeTime.size(); i++) {
			personFeeTime.add(new PersonFeeTime(ExtraTimeItemNo.valueOf(i + 1), listPersonFeeTime.get(i)));
		}
		WorkScheduleTime scheduleTime = new WorkScheduleTime(personFeeTime, scTimeImport.getBreakTime(),
				scTimeImport.getActualWorkTime(), scTimeImport.getWeekDayTime(), scTimeImport.getPreTime(),
				scTimeImport.getTotalWorkTime(), scTimeImport.getChildCareTime());
		bSchedule.setWorkScheduleTime(scheduleTime);
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

	private void addScheState(String employeeIdLogin, BasicSchedule bSchedule, boolean isInsertMode,
			BasicSchedule basicSchedule) {
		List<WorkScheduleState> listWorkScheduleStates = new ArrayList<WorkScheduleState>();
		String sId = bSchedule.getEmployeeId();
		GeneralDate ymd = bSchedule.getDate();
		List<Integer> listId = this.getListIdInScheduleList(bSchedule, isInsertMode, basicSchedule);
		if (employeeIdLogin.equals(sId)) {
			listId.forEach(x -> {
				listWorkScheduleStates.add(
						new WorkScheduleState(ScheduleEditState.HAND_CORRECTION_PRINCIPAL, x.intValue(), ymd, sId));
			});
		} else {
			listId.forEach(x -> {
				listWorkScheduleStates
						.add(new WorkScheduleState(ScheduleEditState.HAND_CORRECTION_ORDER, x.intValue(), ymd, sId));
			});
		}

		bSchedule.setWorkScheduleState(listWorkScheduleStates);
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

	/**
	 * Code base on document スケ項目一覧.xls
	 * 
	 * @param bSchedule
	 * @return
	 */
	private List<Integer> getListIdInScheduleList(BasicSchedule bSchedule, boolean isInsertMode,
			BasicSchedule basicSchedule) {
		List<Integer> listId = new ArrayList<Integer>();
		if (!isInsertMode) {
			// compare bSchedule(-from screen) to basicSchedule(-from DB)
			if (!bSchedule.equalWorkTypeCode(basicSchedule.getWorkTypeCode()))
				listId.add(1);
			if (!bSchedule.equalWorkTimeCode(basicSchedule.getWorkTimeCode()))
				listId.add(2);
			if (!bSchedule.equalConfirmedAtr(basicSchedule.getConfirmedAtr()))
				listId.add(40);
			// TODO
		}
		int sizeWorkTimeZone = bSchedule.getWorkScheduleTimeZones().size();
		int sizeBreakTime = bSchedule.getWorkScheduleBreaks().size();
		int sizeChildCare = bSchedule.getChildCareSchedules().size();

		if (bSchedule.getWorkTypeCode() != null)
			listId.add(1);
		if (!(isInsertMode && bSchedule.getWorkTimeCode() == null))
			listId.add(2);
		if (bSchedule.getConfirmedAtr() != null)
			listId.add(40);

		this.checkSizeWorkScheduleTimeZones(listId, sizeWorkTimeZone);
		this.checkSizeBreakTime(listId, sizeBreakTime);
		this.checkSizeChildCare(listId, sizeChildCare);
		this.checkWorkScheduleTime(listId, bSchedule.getWorkScheduleTime());
		// 勤務予定人件費(phí nhân công dự định làm việc) TODO
		this.checkScheMaster(listId, bSchedule.getWorkScheduleMaster());
		// 勤務予定休暇(vacation dự định làm việc)- chua co table TODO
		// nhung cai con lai chua doi ung domain nen chua lam TODO

		return listId;
	}

	private void checkSizeWorkScheduleTimeZones(List<Integer> listId, int sizeWorkTimeZone) {
		switch (sizeWorkTimeZone) {
		case 1:
			listId.add(3);
			listId.add(4);
			listId.add(41);
			break;
		case 2:
			listId.add(3);
			listId.add(4);
			listId.add(5);
			listId.add(6);
			listId.add(41);
			listId.add(42);
			break;
		default:
			break;
		}
	}

	private void checkSizeBreakTime(List<Integer> listId, int sizeBreakTime) {
		int i = 7, y = sizeBreakTime * 2 + i;
		for (; i < y; i += 2) {
			listId.add(i);
			listId.add(i + 1);
		}
	}

	private void checkSizeChildCare(List<Integer> listId, int sizeChildCare) {
		int i = 27, y = sizeChildCare * 3 + i;
		for (; i < y; i += 3) {
			listId.add(i);
			listId.add(i + 1);
			listId.add(i + 2);
		}
	}

	private void checkWorkScheduleTime(List<Integer> listId, Optional<WorkScheduleTime> workScheduleTime) {
		if (!workScheduleTime.isPresent()) {
			return;
		}

		for (int i = 33; i <= 39; i++) {
			listId.add(i);
		}
		List<PersonFeeTime> listPersonFeeTimes = workScheduleTime.get().getPersonFeeTime();
		if (listPersonFeeTimes == null) {
			return;
		}
		int sizePersonFeeTime = listPersonFeeTimes.size();

		for (int i = 43; i < (43 + sizePersonFeeTime); i++) {
			listId.add(i);
		}
	}

	private void checkScheMaster(List<Integer> listId, ScheMasterInfo workScheduleMaster) {
		listId.add(63);
		listId.add(64);
		listId.add(65);
		listId.add(66);
	}

	private void acquireData(String companyId, List<WorkType> listWorkType, List<WorkTimeSetting> listWorkTimeSetting,
			Map<String, WorkRestTimeZoneDto> mapFixedWorkSetting, Map<String, WorkRestTimeZoneDto> mapFlowWorkSetting,
			Map<String, WorkRestTimeZoneDto> mapDiffTimeWorkSetting) {
		List<String> listWorkTimeCodeFix = new ArrayList<>();
		List<String> listWorkTimeCodeFlow = new ArrayList<>();
		List<String> listWorkTimeCodeDiff = new ArrayList<>();
		listWorkTimeSetting.forEach(workTime -> {
			WorkTimeDivision workTimeDivision = workTime.getWorkTimeDivision();
			if (workTimeDivision.getWorkTimeDailyAtr() == WorkTimeDailyAtr.REGULAR_WORK) {
				if (workTimeDivision.getWorkTimeMethodSet() == WorkTimeMethodSet.FIXED_WORK) {
					listWorkTimeCodeFix.add(workTime.getWorktimeCode().v());
				} else if (workTimeDivision.getWorkTimeMethodSet() == WorkTimeMethodSet.FLOW_WORK) {
					listWorkTimeCodeFlow.add(workTime.getWorktimeCode().v());
				} else {
					listWorkTimeCodeDiff.add(workTime.getWorktimeCode().v());
				}
			}
		});
		// ドメインモデル「固定勤務設定」を取得する
		if (!listWorkTimeCodeFix.isEmpty()) {
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapFixOffdayWorkRestTimezones = this.fixedWorkSettingRepository
					.getFixOffdayWorkRestTimezones(companyId, listWorkTimeCodeFix);
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapFixHalfDayWorkRestTimezones = this.fixedWorkSettingRepository
					.getFixHalfDayWorkRestTimezones(companyId, listWorkTimeCodeFix);
			this.setDataForMap(mapFixedWorkSetting, mapFixOffdayWorkRestTimezones, mapFixHalfDayWorkRestTimezones);
		}
		// ドメインモデル「流動勤務設定」を取得する
		if (!listWorkTimeCodeFlow.isEmpty()) {
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapFlowOffdayWorkRestTimezones = this.flowWorkSettingRepository
					.getFlowOffdayWorkRestTimezones(companyId, listWorkTimeCodeFlow);
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapFlowHalfDayWorkRestTimezones = this.flowWorkSettingRepository
					.getFlowHalfDayWorkRestTimezones(companyId, listWorkTimeCodeFlow);
			this.setDataForMap(mapFlowWorkSetting, mapFlowOffdayWorkRestTimezones, mapFlowHalfDayWorkRestTimezones);
		}
		// ドメインモデル「時差勤務設定」を取得する
		if (!listWorkTimeCodeDiff.isEmpty()) {
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapDiffOffdayWorkRestTimezones = this.diffTimeWorkSettingRepository
					.getDiffOffdayWorkRestTimezones(companyId, listWorkTimeCodeDiff);
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapDiffHalfDayWorkRestTimezones = this.diffTimeWorkSettingRepository
					.getDiffHalfDayWorkRestTimezones(companyId, listWorkTimeCodeDiff);

			this.setDataForMap(mapDiffTimeWorkSetting, mapDiffOffdayWorkRestTimezones, mapDiffHalfDayWorkRestTimezones);
		}

	}

	private void setDataForMap(Map<String, WorkRestTimeZoneDto> map, Map<WorkTimeCode, List<AmPmWorkTimezone>> map1,
			Map<WorkTimeCode, List<AmPmWorkTimezone>> map2) {
		if (map1.size() >= map2.size()) {
			map1.forEach((key, value) -> {
				map.put(key.v(), new WorkRestTimeZoneDto(value, map2.get(key)));
			});
		} else {
			map2.forEach((key, value) -> {
				map.put(key.v(), new WorkRestTimeZoneDto(map1.get(key), value));
			});
		}
	}
}
