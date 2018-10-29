package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
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
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleRound;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.helper.ErrorList;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.helper.Helper;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.helper.MasterCache;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.ScheduledBreakCnt;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.PersonFeeTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.BounceAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.ScheduleEditState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
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
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 登録ボタン押下時処理
 * 
 * @author sonnh1
 *
 */
@Transactional(value = TxType.NOT_SUPPORTED)
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
	public List<String> register(
			String companyId,
			Integer modeDisplay,
			List<BasicSchedule> inputSchedules,
			List<BasicSchedule> basicScheduleListBefore,
			List<BasicSchedule> basicScheduleListAfter,
			RegistrationListDateSchedule registrationListDateSchedule) {
		
		String employeeIdLogin = AppContexts.user().employeeId();
		ErrorList errList = new ErrorList();
		
		List<DateRegistedEmpSche> listDateRegistedEmpSche = new ArrayList<>();

		MasterCache masterCache = new MasterCache(companyId, inputSchedules, workTypeRepo, workTimeSettingRepo);

		Map<String, WorkRestTimeZoneDto> mapFixedWorkSetting = new HashMap<>();
		Map<String, WorkRestTimeZoneDto> mapFlowWorkSetting = new HashMap<>();
		Map<String, WorkRestTimeZoneDto> mapDiffTimeWorkSetting = new HashMap<>();
		this.acquireData(companyId, masterCache, mapFixedWorkSetting, mapFlowWorkSetting,
				mapDiffTimeWorkSetting);
		
		// 処理できない入力を除外する
		// エラーがあればエラーリストに追加
		List<BasicSchedule> validInputSchedules = filterInvalidSchedules(
				modeDisplay,
				inputSchedules,
				errList,
				masterCache);
		
		if (validInputSchedules.isEmpty()) {
			return errList.getErrors();
		}
		
		basicScheduleListBefore.addAll(basicScheduleRepo.findSomeChildWithJDBC(validInputSchedules));

		for (BasicSchedule scheduleInput : validInputSchedules) {
			String employeeId = scheduleInput.getEmployeeId();
			GeneralDate date = scheduleInput.getDate();
			String workTypeCode = scheduleInput.getWorkTypeCode();
			// get work type
			WorkType workType = masterCache.getWorkType(workTypeCode);
			// get work time
			WorkTimeSetting workTimeSetting = masterCache.getWorkTimeSetting(scheduleInput.getWorkTimeCode());
			
			// Check exist of basicSchedule
			Optional<BasicSchedule> scheduleBefore = basicScheduleListBefore.stream()
					.filter(x -> x.getEmployeeId().equals(employeeId) && x.getDate().equals(date))
					.findFirst();
			
			// ver32 trong document
			boolean isOptional = basicScheduleService.checkNeedWorkTimeSetByList(workTypeCode, masterCache.getWorkTypes()) == SetupType.OPTIONAL;
			
			if (scheduleBefore.isPresent()) {
				// UPDATE
				if (!isOptional && workTimeSetting != null) {
					// add scheTimeZone
					if (modeDisplay.intValue() == 2) {
						// get schedule time zone from user input
						List<WorkScheduleTimeZone> workScheduleTimeZonesCommand = new ArrayList<>(
								scheduleInput.getWorkScheduleTimeZones());

						if (!CollectionUtil.isEmpty(workScheduleTimeZonesCommand)) {
							// update again data time zone for case user
							// update
							// start time, end time (mode show time)
							
							List<WorkScheduleTimeZone> timeZonesNew = new ArrayList<>();
							scheduleInput.getWorkScheduleTimeZones().forEach(item -> {
								if (item.getScheduleCnt() == 1) {
									WorkScheduleTimeZone timeZone = workScheduleTimeZonesCommand.get(0);
									item.updateTime(timeZone.getScheduleStartClock(), timeZone.getScheduleEndClock());
								}
								// set bounceAtr
								BounceAtr bounceAtr = BounceAtr.addScheduleBounce(workType);
								item.updateBounceAtr(bounceAtr);
								timeZonesNew.add(item);
							});

							scheduleInput.setWorkScheduleTimeZones(timeZonesNew);
						}
					} else {
						val scheduleTimeZones = createScheTimeZone(
								companyId, workType, scheduleInput.getWorkTimeCode(), masterCache.getWorkTypes());
						scheduleInput.setWorkScheduleTimeZones(scheduleTimeZones);
					}

					// add breakTime
					val breaks = this.createBreakTime(companyId, masterCache, scheduleInput,
							mapFixedWorkSetting, mapFlowWorkSetting, mapDiffTimeWorkSetting);
					scheduleInput.setWorkScheduleBreaks(breaks);
				}
			} else {
				// INSERT
				if (!isOptional && workTimeSetting != null) {
					// add timeZone
					val scheduleTimeZones = createScheTimeZone(
							companyId, workType, scheduleInput.getWorkTimeCode(), masterCache.getWorkTypes());
					scheduleInput.setWorkScheduleTimeZones(scheduleTimeZones);
					// add breakTime
					val breaks = this.createBreakTime(companyId, masterCache, scheduleInput,
							mapFixedWorkSetting, mapFlowWorkSetting, mapDiffTimeWorkSetting);
					scheduleInput.setWorkScheduleBreaks(breaks);
					// add childCare
					// this.addChildCare(bSchedule);
				}
			}
		}
		
		this.bulkCalculation(validInputSchedules);
		
		// persisting
		for (BasicSchedule scheduleInput : validInputSchedules) {

			String employeeId = scheduleInput.getEmployeeId();
			GeneralDate date = scheduleInput.getDate();
			
			// Check exist of basicSchedule
			Optional<BasicSchedule> scheduleBefore = basicScheduleListBefore.stream()
					.filter(x -> x.getEmployeeId().equals(employeeId) && x.getDate().equals(date))
					.findFirst();
			
			if (scheduleBefore.isPresent()) {
				// UPDATE
				// add scheMaster
				// set scheMaster = scheMaster of basicSche from DB
				// (compare to insert state and log, not to update in DB)
				// hien tai tai lieu excel dang ghi la "khong update"
				scheduleInput.setWorkScheduleMaster(scheduleBefore.get().getWorkScheduleMaster());
				// add scheState
				this.addScheState(employeeIdLogin, scheduleInput, false, scheduleBefore.get());
				
				basicScheduleRepo.update(scheduleInput);
			} else {
				// INSERT
				// add scheMaster
				this.addScheMaster(companyId, scheduleInput);
				// add scheState
				this.addScheState(employeeIdLogin, scheduleInput, true, null);
				
				// delete truoc khi insert de tranh truong hop data sai
				// co data cua con ma k co data cua cha
				basicScheduleRepo.delete(employeeId, date, scheduleInput);
				
				basicScheduleRepo.insert(scheduleInput); 
			}
			
			basicScheduleListAfter.add(scheduleInput);
			
			// 修正ログ情報を作成する (Tạo thông tin log chỉnh sửa)
			// Lam ben ngoai vong lap, phần (đăng ký record chỉnh sử data)
			
			// 登録対象日を保持しておく（暫定データ作成用） 
			Optional<DateRegistedEmpSche> optDateRegistedEmpSche = listDateRegistedEmpSche.stream()
					.filter(s -> s.getEmployeeId().equals(employeeId)).findFirst();
			if(optDateRegistedEmpSche.isPresent()){
				optDateRegistedEmpSche.get().getListDate().add(date); 
			} else {
				List<GeneralDate> listDate = new ArrayList<>();
				listDate.add(date);
				listDateRegistedEmpSche.add(new DateRegistedEmpSche(employeeId, listDate));
			}
		}
		
		registrationListDateSchedule.setRegistrationListDateSchedule(listDateRegistedEmpSche);
		
		return errList.getErrors();
	}

	private void bulkCalculation(List<BasicSchedule> validInputSchedules) {
		List<ScTimeParam> params = validInputSchedules.stream()
				.map(s -> ScTimeParam.createFrom(s))
				.collect(Collectors.toList());
		
		List<ScTimeImport> results = this.scTimeAdapter.calculation(null, params);
		
		if(results.size() == 0) return;
		
		for (BasicSchedule scheduleInput : validInputSchedules) {
			ScTimeImport scTimeImport = results.stream()
					.filter(r -> r.getEmployeeid().equals(scheduleInput.getEmployeeId()) && r.getYmd().equals(scheduleInput.getDate()))
					.findFirst()
					.get();
			WorkScheduleTime scheduleTime = Helper.scTimeImportToWorkScheduleTime(scTimeImport);
			scheduleInput.setWorkScheduleTime(scheduleTime);
		}
	}
	
	private List<BasicSchedule> filterInvalidSchedules(
			Integer modeDisplay,
			List<BasicSchedule> basicScheduleList,
			ErrorList errList,
			MasterCache masterCache) {
		
		List<BasicSchedule> validSchedules = basicScheduleList.stream().filter(bSchedule -> {
			// アルゴリズム「登録時エラーチェック処理」を実行する
			// Input.スケジュール表示形式区分を判定する
			// modeDisplay == 2 : 
			if(modeDisplay.intValue() != 2) {
				String workTypeCode = bSchedule.getWorkTypeCode();
				String workTimeCode = bSchedule.getWorkTimeCode();
				// get work type
				WorkType workType = masterCache.getWorkType(workTypeCode);
				// get work time
				WorkTimeSetting workTimeSetting = masterCache.getWorkTimeSetting(workTimeCode);
				
				// 勤務種類のマスタチェック (Kiểm tra phan loại ngày làm việc)
				if (!MasterAvailabilityPolicy.checkWorkType(workType, errList)) {
					return false;
				}
	
				if (!StringUtil.isNullOrEmpty(workTimeCode, true)) {
					// 就業時間帯のマスタチェック (Kiểm tra giờ làm việc)
					if (!MasterAvailabilityPolicy.checkWorkTime(workTimeSetting, errList)) {
						return false;
					}
				}

				// 勤務種類と就業時間帯のペアチェック (Kiểm tra cặp)
				if (!MasterAvailabilityPolicy.checkParing(basicScheduleService, workTypeCode, workTimeCode, masterCache, errList)) {
					return false;
				}
			}


			// 時刻のチェック
			// TODO
			
			if (!CollectionUtil.isEmpty(bSchedule.getWorkScheduleTimeZones())
					&& !checkTimeZone(errList, bSchedule.getWorkScheduleTimeZones())) {
				return false;
			}

			return true;
		}).collect(Collectors.toList());
		return validSchedules;
	}
	/**

	 * Check time zone for case update (mode show time)
	 * 
	 * @param errList
	 * @param workScheduleTimeZonesCommand
	 * @return
	 */
	private boolean checkTimeZone(ErrorList errList, List<WorkScheduleTimeZone> workScheduleTimeZonesCommand) {
		WorkScheduleTimeZone timeZone = workScheduleTimeZonesCommand.get(0);
		timeZone.validate();
		Map<String, String> msgErrMap = timeZone.validateTime();
		if (!msgErrMap.isEmpty()) {
			for (Map.Entry<String, String> m : msgErrMap.entrySet()) {
				errList.addMessage(m.getValue() + "," + m.getKey());
			}
			return false;
		}

		// 開始時刻と終了時刻の逆転チェック
		if (basicScheduleService.isReverseStartAndEndTime(timeZone.getScheduleStartClock(),
				timeZone.getScheduleEndClock())) {
			errList.addMessage("Msg_441,KSU001_73,KSU001_74");
			return false;
		}

		return true;
	}

	/**
	 * @param companyId
	 * @param basicScheduleObj
	 * @param workType
	 */
	private List<WorkScheduleTimeZone> createScheTimeZone(String companyId, WorkType workType, String workTimeSettingCode,
			List<WorkType> listWorkType) {
		List<WorkScheduleTimeZone> workScheduleTimeZones = new ArrayList<WorkScheduleTimeZone>();
		BounceAtr bounceAtr = BounceAtr.addScheduleBounce(workType);
		Optional<PredetemineTimeSetting> predetemineTimeSet = this.predetemineTimeSettingRepo
				.findByWorkTimeCode(companyId, workTimeSettingCode);

		if (!predetemineTimeSet.isPresent()) {
			return Collections.emptyList();
		}

		PrescribedTimezoneSetting prescribedTimezoneSetting = predetemineTimeSet.get().getPrescribedTimezoneSetting();
		List<TimezoneUse> listTimezoneUse = prescribedTimezoneSetting.getLstTimezone();
		TimezoneUse timezoneUseK1 = listTimezoneUse.stream().filter(x -> x.isUsed() && x.getWorkNo() == 1).findFirst()
				.get();
		Optional<TimezoneUse> timezoneUseK2 = listTimezoneUse.stream().filter(x -> x.isUsed() && x.getWorkNo() == 2)
				.findFirst();
		// if workTypeCode is work on morning, replace endTime = endTime of morning
		if (basicScheduleService.checkWorkDayByList(workType.getWorkTypeCode().v(),
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
		} else if (basicScheduleService.checkWorkDayByList(workType.getWorkTypeCode().v(),
				listWorkType) == WorkStyle.AFTERNOON_WORK) {
			TimeWithDayAttr afternoonStartTime = prescribedTimezoneSetting.getAfternoonStartTime();
			// if workTypeCode is work on afternoon, replace startTime =  startTime of afternoon
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

		return workScheduleTimeZones;
	}

	private List<WorkScheduleBreak> createBreakTime(String companyId, MasterCache masterCache, BasicSchedule bSchedule,
			Map<String, WorkRestTimeZoneDto> mapFixedWorkSetting, Map<String, WorkRestTimeZoneDto> mapFlowWorkSetting,
			Map<String, WorkRestTimeZoneDto> mapDiffTimeWorkSetting) {
		List<WorkScheduleBreak> listWorkScheduleBreak = new ArrayList<>();
		List<DeductionTime> listTimeZone = new ArrayList<>();

		List<DeductionTime> listScheTimeZone = bSchedule.getWorkScheduleTimeZones().stream()
				.map(x -> new DeductionTime(x.getScheduleStartClock(), x.getScheduleEndClock()))
				.collect(Collectors.toList());
		BusinessDayCal businessDayCal = this.creScheWithBusinessDayCalService.getScheduleBreakTime(companyId,
				bSchedule.getWorkTypeCode(), bSchedule.getWorkTimeCode(), masterCache.getWorkTypes(), masterCache.getWorkTimeSettings(), mapFixedWorkSetting, mapFlowWorkSetting,
				mapDiffTimeWorkSetting, listScheTimeZone);
		if (businessDayCal == null) {
			bSchedule.setWorkScheduleBreaks(listWorkScheduleBreak);
			return Collections.emptyList();
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
		
		return listWorkScheduleBreak;
	}

	private void addChildCare(BasicSchedule bSchedule) {
		List<ChildCareSchedule> listChildCareSchedule = new ArrayList<>();
		listChildCareSchedule.add(new ChildCareSchedule(EnumAdaptor.valueOf(0, ChildCareScheduleRound.class), null,
				null, EnumAdaptor.valueOf(0, ChildCareAtr.class)));
		bSchedule.setChildCareSchedules(listChildCareSchedule);
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
		// get listItemId 
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
	 * Code base on document スケ項目一覧.xls
	 * 
	 * @param bSchedule
	 * @return
	 */
	private List<Integer> getListIdInScheduleList(BasicSchedule bSchedule, boolean isInsertMode,
			BasicSchedule basicSchedule) {
		List<Integer> listId = new ArrayList<Integer>();
		if (!isInsertMode) {
			this.getListIdWhenUpdateMode(bSchedule, basicSchedule, listId);
			return listId;
		}
		// when insert mode
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
		// TODO <= 39 nhung gio truong tuong ung vs id = 39 chua co nen de < 39
		for (int i = 33; i < 39; i++) {
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
//		listId.add(64);
		listId.add(65);
		listId.add(66);
	}

	private void acquireData(String companyId, MasterCache masterCache,
			Map<String, WorkRestTimeZoneDto> mapFixedWorkSetting, Map<String, WorkRestTimeZoneDto> mapFlowWorkSetting,
			Map<String, WorkRestTimeZoneDto> mapDiffTimeWorkSetting) {
		List<String> listWorkTimeCodeFix = new ArrayList<>();
		List<String> listWorkTimeCodeFlow = new ArrayList<>();
		List<String> listWorkTimeCodeDiff = new ArrayList<>();
		masterCache.getWorkTimeSettings().forEach(workTime -> {
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
	
	private void getListIdWhenUpdateMode(BasicSchedule bScheduleAfter, BasicSchedule bScheduleBefore,
			List<Integer> listId) {
		// compare bSchedule(-from screen) to basicSchedule(-from DB)
		if (bScheduleAfter.diffWorkTypeCode(bScheduleBefore.getWorkTypeCode()))
			listId.add(1);
		if (bScheduleAfter.diffWorkTimeCode(bScheduleBefore.getWorkTimeCode()))
			listId.add(2);
		if (bScheduleAfter.diffConfirmedAtr(bScheduleBefore.getConfirmedAtr()))
			listId.add(40);
		
		// compare timezone(-from screen) to timezone(-from DB)
		List<WorkScheduleTimeZone> listTimeZoneAfter = bScheduleAfter.getWorkScheduleTimeZones();
		List<WorkScheduleTimeZone> listTimeZoneBefore = bScheduleBefore.getWorkScheduleTimeZones();
		if (listTimeZoneAfter.size() >= listTimeZoneBefore.size()) {
			this.compareTimezone(listTimeZoneAfter, listTimeZoneBefore, listId);
		} else {
			this.compareTimezone(listTimeZoneBefore, listTimeZoneAfter, listId);
		}
		
		// compare breakTime(-from screen) to breakTime(-from DB)
		List<WorkScheduleBreak> listBreakTimeAfter = bScheduleAfter.getWorkScheduleBreaks();
		List<WorkScheduleBreak> listBreakTimeBefore = bScheduleBefore.getWorkScheduleBreaks();
		if (listBreakTimeAfter.size() >= listBreakTimeBefore.size()) {
			this.compareBreakTime(listBreakTimeAfter, listBreakTimeBefore, listId);
		} else {
			this.compareBreakTime(listBreakTimeBefore, listBreakTimeAfter, listId);
		}
		
		// compare childCare(-from screen) to childCare(-from DB)
		List<ChildCareSchedule> listChildCareAfter = bScheduleAfter.getChildCareSchedules();
		List<ChildCareSchedule> listChildCareBefore = bScheduleBefore.getChildCareSchedules();
		if (listChildCareAfter.size() >= listChildCareBefore.size()) {
			this.compareChildCare(listChildCareAfter, listChildCareBefore, listId);
		} else {
			this.compareChildCare(listChildCareBefore, listChildCareAfter, listId);
		}
		
		// compare scheTime(-from screen) to scheTime(-from DB)
		Optional<WorkScheduleTime> optScheTimeAfter = bScheduleAfter.getWorkScheduleTime();
		Optional<WorkScheduleTime> optScheTimeBefore = bScheduleBefore.getWorkScheduleTime();
		if(optScheTimeAfter.isPresent() && optScheTimeBefore.isPresent()){
			WorkScheduleTime scheTimeAfter = optScheTimeAfter.get();
			WorkScheduleTime scheTimeBefore = optScheTimeBefore.get();
			if (scheTimeAfter.diffTotalLaborTime(scheTimeBefore.getTotalLaborTime()))
				listId.add(33);
			if (scheTimeAfter.diffWorkingTime(scheTimeBefore.getWorkingTime()))
				listId.add(34);
			if (scheTimeAfter.diffPredetermineTime(scheTimeBefore.getPredetermineTime()))
				listId.add(35);
			if (scheTimeAfter.diffBreakTime(scheTimeBefore.getBreakTime()))
				listId.add(36);
			if (scheTimeAfter.diffWeekdayTime(scheTimeBefore.getWeekdayTime()))
				listId.add(37);
			if (scheTimeAfter.diffFlexTime(scheTimeBefore.getFlexTime()))
				listId.add(39);
			if (scheTimeAfter.diffChildTime(scheTimeBefore.getChildTime()))
				listId.add(102);
			if (scheTimeAfter.diffCareTime(scheTimeBefore.getCareTime()))
				listId.add(103);
			// compare personFeeTime
			List<PersonFeeTime> personFeeTimeAfter = scheTimeAfter.getPersonFeeTime();
			List<PersonFeeTime> personFeeTimeBefore = scheTimeBefore.getPersonFeeTime();
			if (personFeeTimeAfter.size() >= personFeeTimeBefore.size()) {
				this.comparePersonFeeTime(personFeeTimeAfter, personFeeTimeBefore, listId);
			} else {
				this.comparePersonFeeTime(personFeeTimeBefore, personFeeTimeAfter, listId);
			}
		} else {
			listId.add(33);
			listId.add(34);
			listId.add(35);
			listId.add(36);
			listId.add(37);
			listId.add(39);
			listId.add(102);
			listId.add(103);
			int sizeFeeTime = 0;
			if(optScheTimeAfter.isPresent()){
				sizeFeeTime = optScheTimeAfter.get().getPersonFeeTime().size();
			}
			if(optScheTimeBefore.isPresent()){
				sizeFeeTime = optScheTimeBefore.get().getPersonFeeTime().size();
			}
			if(sizeFeeTime > 0){
				for(int i = 0; i< sizeFeeTime; i++){
					listId.add(43+i);
				}
			}
		}
		
		// compare scheMaster(-from screen) to scheMaster(-from DB)
		ScheMasterInfo scheMasterInfoAfter = bScheduleAfter.getWorkScheduleMaster();
		ScheMasterInfo scheMasterInfoBefore = bScheduleBefore.getWorkScheduleMaster();
		if(scheMasterInfoAfter != null && scheMasterInfoBefore != null ) {
			if (scheMasterInfoAfter.diffEmploymentCd(scheMasterInfoBefore.getEmploymentCd()))
				listId.add(63);
			if (scheMasterInfoAfter.diffClassificationCd(scheMasterInfoBefore.getClassificationCd()))
				listId.add(64);
			if (scheMasterInfoAfter.diffWorkplaceId(scheMasterInfoBefore.getWorkplaceId()))
				listId.add(65);
			if (scheMasterInfoAfter.diffJobId(scheMasterInfoBefore.getJobId()))
				listId.add(66);
		}
	}
	
	private void compareTimezone(List<WorkScheduleTimeZone> listTimezoneBig,
			List<WorkScheduleTimeZone> listTimezoneSmall, List<Integer> listId) {
		for (int i = 0; i < listTimezoneBig.size(); i++) {
			WorkScheduleTimeZone timezoneBig = listTimezoneBig.get(i);
			// find in listTimezoneSmall
			Optional<WorkScheduleTimeZone> timezoneSmall = listTimezoneSmall.stream()
					.filter(x -> (x.getScheduleCnt() == timezoneBig.getScheduleCnt())).findFirst();
			if (timezoneSmall.isPresent()) {
				if (timezoneBig.diffScheduleStartClock(timezoneSmall.get().getScheduleStartClock()))
					listId.add(3 + i * 2);
				if (timezoneBig.diffScheduleEndClock(timezoneSmall.get().getScheduleEndClock()))
					listId.add(4 + i * 2);
				if (timezoneBig.diffBounceAtr(timezoneSmall.get().getBounceAtr()))
					listId.add(41 + i);
			} else {
				listId.add(3 + i * 2);
				listId.add(4 + i * 2);
				listId.add(41 + i);
			}
		}
	}
	
	private void compareBreakTime(List<WorkScheduleBreak> listBreakTimeBig,
			List<WorkScheduleBreak> listBreakTimeSmall, List<Integer> listId) {
		for (int i = 0; i < listBreakTimeBig.size(); i++) {
			WorkScheduleBreak breakTimeBig = listBreakTimeBig.get(i);
			// find in listBreakTimeSmall
			Optional<WorkScheduleBreak> breakTimeSmall = listBreakTimeSmall.stream()
					.filter(x -> (x.getScheduleBreakCnt().v().intValue() == breakTimeBig.getScheduleBreakCnt().v().intValue())).findFirst();
			if (breakTimeSmall.isPresent()) {
				if (breakTimeBig.diffScheduleStartClock(breakTimeSmall.get().getScheduledStartClock()))
					listId.add(7 + i * 2);
				if (breakTimeBig.diffScheduleEndClock(breakTimeSmall.get().getScheduledEndClock()))
					listId.add(8 + i * 2);
			} else {
				listId.add(7 + i * 2);
				listId.add(8 + i * 2);
			}
		}
	}
	
	private void compareChildCare(List<ChildCareSchedule> listChildCareBig,
			List<ChildCareSchedule> listChildCareSmall, List<Integer> listId) {
		for (int i = 0; i < listChildCareBig.size(); i++) {
			ChildCareSchedule childCareBig = listChildCareBig.get(i);
			// find in listChildCareSmall
			Optional<ChildCareSchedule> childCareSmall = listChildCareSmall.stream()
					.filter(x -> (x.getChildCareNumber().value == childCareBig.getChildCareNumber().value)).findFirst();
			if (childCareSmall.isPresent()) {
				if (childCareBig.diffChildCareAtr(childCareSmall.get().getChildCareAtr()))
					listId.add(27 + i * 3);
				if (childCareBig.diffChildCareScheduleStart(childCareSmall.get().getChildCareScheduleStart()))
					listId.add(28 + i * 3);
				if (childCareBig.diffChildCareScheduleEnd(childCareSmall.get().getChildCareScheduleEnd()))
					listId.add(29 + i * 3);
			} else {
				listId.add(27 + i * 3);
				listId.add(28 + i * 3);
				listId.add(29 + i * 3);
			}
		}
	}
	
	private void comparePersonFeeTime(List<PersonFeeTime> listPersonFeeTimeBig,
			List<PersonFeeTime> listPersonFeeTimeSmall, List<Integer> listId) {
		for (int i = 0; i < listPersonFeeTimeBig.size(); i++) {
			PersonFeeTime personFeeTimeBig = listPersonFeeTimeBig.get(i);
			// find in listpersonFeeTimeSmall
			Optional<PersonFeeTime> optPersonFeeTimeSmall = listPersonFeeTimeSmall.stream()
					.filter(x -> (x.getNo().value == personFeeTimeBig.getNo().value)).findFirst();
			if (optPersonFeeTimeSmall.isPresent()) {
				if (personFeeTimeBig.diffPersonFeeTime(optPersonFeeTimeSmall.get().getPersonFeeTime()))
					listId.add(43 + i);
			} else {
				listId.add(43 + i);
			}
		}
	}
}
