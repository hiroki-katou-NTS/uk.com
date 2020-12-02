package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NResources;
import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TimePlaceOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.InitWkTypeWkTimeOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.CalculationResult;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessState;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessStateDetail;
import nts.uk.ctx.at.request.dom.application.overtime.FrameNo;
import nts.uk.ctx.at.request.dom.application.overtime.HolidayMidNightTime;
import nts.uk.ctx.at.request.dom.application.overtime.OutDateApplication;
import nts.uk.ctx.at.request.dom.application.overtime.OverStateOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeApplicationSetting;
import nts.uk.ctx.at.request.dom.application.overtime.primitivevalue.OvertimeAppPrimitiveTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayInfoOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.OverTimeContent;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.application.overtime.service.SelectWorkOutput;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkHours;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.FlexWorkAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeQuotaSetUse;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.AtWorkAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.workrecord.dailyrecordprocess.dailycreationwork.BreakTimeZoneSetting;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRootRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeUseSet;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWorkRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicateStateAtr;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicationStatusOfTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.RangeOfDayTimeZoneService;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class CommonAlgorithmOverTimeImpl implements ICommonAlgorithmOverTime {
	
	@Inject
	private OvertimeWorkFrameRepository overtimeWorkFrameRepository;
	
	@Inject
	private WorkingConditionRepository workingConditionRepository;
	
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private DivergenceTimeRootRepository divergenceTimeRoots;
	
	@Inject
	private OvertimeAppSetRepository overtimeAppSetRepository;
	
	@Inject
	private CommonOvertimeHoliday commonOvertimeholiday;
	
	@Inject
	private AppReflectOtHdWorkRepository appReflectOtHdWorkRepository;
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private CommonOvertimeHoliday commonOverTime;
	
	@Inject
	public RangeOfDayTimeZoneService rangeOfDayTimeZoneService;
	
	@Inject 
	private WorkTimeSettingService workTimeSettingService;
	
	@Inject 
	private OvertimeService overTimeService;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Override
	public QuotaOuput getOvertimeQuotaSetUse(
			String companyId,
			String employeeId,
			GeneralDate date,
			OvertimeAppAtr overTimeAtr,
			OvertimeAppSet overtimeAppSet) {
		QuotaOuput output = new QuotaOuput();
		// 社員の労働条件を取得する
		Optional<WorkingConditionItem> workingConditionItemOp = WorkingConditionService.findWorkConditionByEmployee(createRequireM1(), employeeId, date);
		if (!workingConditionItemOp.isPresent()) return new QuotaOuput(false, Collections.emptyList()); // need to QA if workingConditionItemOp is empty
		WorkingConditionItem workingConditionItem = workingConditionItemOp.get();
		// 取得したドメインモデル「労働条件項目」．労働制を確認する
		List<OvertimeWorkFrame> frames = overtimeWorkFrameRepository.getOvertimeWorkFrameByFrameByCom(companyId, NotUseAtr.USE.value);
		List<Integer> listFNo = Collections.emptyList();
		List<OvertimeQuotaSetUse> overtimeQuotaSet;
		if (workingConditionItem.getLaborSystem() == WorkingSystem.FLEX_TIME_WORK) { // 労働制 = フレックス時間勤務(LaborSystem = FlexTimeWork)
			// OUTPUT「利用する残業枠」を更新する
			output.setFlexTimeClf(true);
			// ドメインモデル「残業枠」を取得
			/**
			 * ⇒「残業申請設定」の条件は：「残業枠設定．残業申請区分」 = INPUT．「残業申請区分」
　　　　　　　　　　　　　　　　　　「残業枠設定．フレックス勤務者区分」 = フレックス時間勤務
			 */
			overtimeQuotaSet = overtimeAppSet.getOvertimeQuotaSet().stream()
					.filter(x -> x.getFlexWorkAtr() == FlexWorkAtr.FLEX_TIME && x.getOvertimeAppAtr() == overTimeAtr)
					.collect(Collectors.toList());
			
			
			
		} else {
			// OUTPUT「利用する残業枠」を更新する
			output.setFlexTimeClf(false);
			/**
			 * ⇒「残業申請設定」の条件は：「残業枠設定．残業申請区分」 = INPUT．「残業申請区分」
　　　　　　　　　　　　　　　　　　「残業枠設定．フレックス勤務者区分」 = フレックス時間勤務以外
			 */
			overtimeQuotaSet = overtimeAppSet.getOvertimeQuotaSet().stream()
					.filter(x -> x.getFlexWorkAtr() == FlexWorkAtr.OTHER && x.getOvertimeAppAtr() == overTimeAtr)
					.collect(Collectors.toList());
			
		}
		
		if (!CollectionUtil.isEmpty(overtimeQuotaSet)) {

			listFNo = overtimeQuotaSet.stream()
					.flatMap(x -> x.getTargetOvertimeLimit().stream().map(y -> y.v()))
					.distinct()
					.collect(Collectors.toList());
		}
		if (CollectionUtil.isEmpty(listFNo)) {
			frames = Collections.emptyList();
		} else {
			final List<Integer> nos = listFNo;
			frames = frames.stream()
							.filter(x -> nos.contains(x.getOvertimeWorkFrNo().v().intValue()))
							.collect(Collectors.toList());
		}
		
		// OUTPUT「利用する残業枠」を更新して返す
		output.setOverTimeQuotaList(frames);
		
		
		return output;
	}
	
	
	private WorkingConditionService.RequireM1 createRequireM1() {
		return new WorkingConditionService.RequireM1() {
			
			@Override
			public Optional<WorkingConditionItem> workingConditionItem(String historyId) {
				return workingConditionItemRepository.getByHistoryId(historyId);
			}
			
			@Override
			public Optional<WorkingCondition> workingCondition(String companyId, String employeeId, GeneralDate baseDate) {
				return workingConditionRepository.getBySidAndStandardDate(companyId, employeeId, baseDate);
			}
		};
	}


	@Override
	public List<WorkType> getWorkType(Optional<AppEmploymentSet> appEmploymentSettingOp) {
		List<WorkType> workTypes = new ArrayList<>();
		Boolean isC1 = false;
		Boolean isC2 = false;
		Boolean isC3 = false;
		if (appEmploymentSettingOp.isPresent()) {
			AppEmploymentSet appEmploymentSetting = appEmploymentSettingOp.get();
			isC1 = !appEmploymentSetting.getTargetWorkTypeByAppLst().isEmpty();
			if (!isC1) {
				isC2 = appEmploymentSetting.getTargetWorkTypeByAppLst().get(0).isDisplayWorkType();
				isC3 = !appEmploymentSetting.getTargetWorkTypeByAppLst().get(0).getWorkTypeLst().isEmpty();
			}
			// 「申請別対象勤務種類」をチェックする
			if (isC1 && isC2 && isC3) {
				// ドメインモデル「勤務種類」を取得して返す
				List<WorkType> listWorkType = workTypeRepository.findByCidAndWorkTypeCodes(AppContexts.user().companyId(), appEmploymentSetting.getTargetWorkTypeByAppLst().get(0).getWorkTypeLst());
				if (!workTypes.isEmpty()) {
					workTypes = listWorkType.stream().filter(x -> x.isDeprecated()).collect(Collectors.toList());
				}
			}
			
		} 
		
		if (!(isC1 && isC2 && isC3)) {
			// ドメインモデル「勤務種類」を取得して返す
			workTypes = workTypeRepository.findWorkType(AppContexts.user().companyId(), 0, allDayAtrs(), halfAtrs()).stream()
										  .collect(Collectors.toList());
		}
		// 取得した「勤務種類」をチェック
		if (workTypes.isEmpty()) throw new BusinessException("Msg_1567");
		return workTypes;
	}
	
	/**
	 * // １日の勤務＝以下に該当するもの
	 * 　出勤、休出、振出、連続勤務
	 * @return
	 */
	private List<Integer> allDayAtrs(){
		
		List<Integer> allDayAtrs = new ArrayList<>();
		//出勤
		allDayAtrs.add(0);
		//休出
		allDayAtrs.add(11);
		//振出
		allDayAtrs.add(7);
		// 連続勤務
		allDayAtrs.add(10);
		return allDayAtrs;
	}
	/**
	 * 午前 また 午後 in (休日, 振出, 年休, 出勤, 特別休暇, 欠勤, 代休, 時間消化休暇)
	 * @return
	 */
	private List<Integer> halfAtrs(){
		List<Integer> halfAtrs = new ArrayList<>();
		// 休日
		halfAtrs.add(1);
		// 振出
		halfAtrs.add(7);
		// 年休
		halfAtrs.add(2);
		// 出勤
		halfAtrs.add(0);
		//特別休暇
		halfAtrs.add(4);
		// 欠勤
		halfAtrs.add(5);
		// 代休
		halfAtrs.add(6);
		//時間消化休暇
		halfAtrs.add(9);
		return halfAtrs;
	}


	@Override
	public InfoBaseDateOutput getInfoBaseDate(
			String companyId,
			String employeeId,
			GeneralDate date,
			OvertimeAppAtr overTimeAtr,
			List<WorkTimeSetting> workTime,
			Optional<AppEmploymentSet> appEmploymentSettingOp,
			OvertimeAppSet overtimeAppSet) {
		InfoBaseDateOutput output = new InfoBaseDateOutput();
		// 指定社員の申請残業枠を取得する
		QuotaOuput quotaOuput = this.getOvertimeQuotaSetUse(companyId, employeeId, date, overTimeAtr, overtimeAppSet);
		output.setQuotaOutput(quotaOuput);
		// 07_勤務種類取得
		List<WorkType> worktypes = this.getWorkType(appEmploymentSettingOp);
		output.setWorktypes(worktypes);
		// INPUT．「就業時間帯の設定<List>」をチェックする
		if (workTime.isEmpty()) throw new BusinessException("Msg_1568");
		return output;
	}


	@Inject
	private DivergenceReasonInputMethodI divergenceReasonInputMethod;

	@Override
	public ReasonDissociationOutput getInfoNoBaseDate(
			String companyId,
			ApplicationType appType,
			Optional<OvertimeAppAtr> ovetTimeAtr,
			OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet) {
		ReasonDissociationOutput output = new ReasonDissociationOutput(); // emptyを返す
		// @「登録時の乖離時間チェック」を確認する
		if (overtimeLeaveAppCommonSet.getCheckDeviationRegister() == NotUseAtr.NOT_USE) // しない
			return output; // emptyを返す
		// する
		// INPUT．「申請種類」」をチェックする
		if (appType == ApplicationType.OVER_TIME_APPLICATION || appType == ApplicationType.HOLIDAY_WORK_APPLICATION) {// 残業申請
																														// OR
																														// 休出時間申請

			/*
			 * 
			 * INPUT．「申請種類」= 残業申請 AND INPUT．「残業申請区分」= 早出残業 ⇒ List<乖離時間NO> = 1 INPUT．「申請種類」=
			 * 残業申請 AND INPUT．「残業申請区分」= 通常残業 ⇒ List<乖離時間NO> = 2 INPUT．「申請種類」= 残業申請 AND
			 * INPUT．「残業申請区分」= 早出残業・通常残業 ⇒ List<乖離時間NO> = 1,2
			 */
			List<Integer> frames = new ArrayList<Integer>();
			if (appType == ApplicationType.HOLIDAY_WORK_APPLICATION) { // INPUT．「申請種類」= 休出時間申請 ⇒ List<乖離時間NO> = 3
				frames.add(3);
			} else if (appType == ApplicationType.OVER_TIME_APPLICATION) {
				if (ovetTimeAtr.isPresent()) {
					OvertimeAppAtr value = ovetTimeAtr.get();
					// 早出残業
					if (value == OvertimeAppAtr.EARLY_OVERTIME) {
						frames.add(1);
					} else if (value == OvertimeAppAtr.NORMAL_OVERTIME) { // 通常残業
						frames.add(2);
					} else { // 早出残業・通常残業
						frames.add(1);
						frames.add(2);
					}
				}
			}
			// [RQ694]乖離時間Listを取得する
			List<DivergenceTimeRoot> divergenceTimeRootList = divergenceTimeRoots.getList(frames);
			if (CollectionUtil.isEmpty(divergenceTimeRootList)) {
				return output;
			}
			output.setDivergenceTimeRoots(divergenceTimeRootList);
			// [RQ693]乖離理由の入力方法Listを取得する
			List<Integer> lstNo = divergenceTimeRootList.stream()
														   .filter(x -> x.getDivTimeUseSet() == DivergenceTimeUseSet.USE)
														   .map(y -> y.getDivergenceTimeNo())
														   .collect(Collectors.toList());
			List<DivergenceReasonInputMethod> divergenceReasonInputMethodListFilter = divergenceReasonInputMethod.getData(companyId, lstNo);
			
			if (divergenceReasonInputMethodListFilter.isEmpty()) {
				return output; // emptyを返す
			}
			output.setDivergenceReasonInputMethod(divergenceReasonInputMethodListFilter);

		} else {
			return output; // emptyを返す
		}
		// 取得した「乖離時間」と「乖離理由の入力方法」を返す
		return output;
	}


	@Override
	public InfoNoBaseDate getInfoNoBaseDate(
			String companyId,
			String employeeId,
			OvertimeAppAtr overtimeAppAtr) {
		InfoNoBaseDate output = new InfoNoBaseDate();
		// 残業申請設定を取得する
		Optional<OvertimeAppSet> overOptional = overtimeAppSetRepository.findSettingByCompanyId(companyId);
		
		OvertimeAppSet overtimeAppSet = overOptional.orElse(null);
		OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet = overtimeAppSet.getOvertimeLeaveAppCommonSet();
		// 乖離理由の表示区分を取得する
		ReasonDissociationOutput reasonDissociationOutput = this.getInfoNoBaseDate(companyId, ApplicationType.OVER_TIME_APPLICATION, Optional.ofNullable(overtimeAppAtr), overtimeLeaveAppCommonSet);
		// 01-02_時間外労働を取得
		Optional<AgreeOverTimeOutput> agreeOverTimeOutputOp = commonOvertimeholiday.getAgreementTime(companyId, employeeId, ApplicationType.OVER_TIME_APPLICATION);
		
		// ドメインモデル「残業休日出勤申請の反映」を取得する
		Optional<AppReflectOtHdWork> overTimeReflectOp = appReflectOtHdWorkRepository.findByCompanyId(companyId);
		// 取得した情報を「基準日に関係しない情報」にセットして返す
		output.setOverTimeAppSet(overtimeAppSet);
		output.setDivergenceReasonInputMethod(reasonDissociationOutput.getDivergenceReasonInputMethod());
		output.setDivergenceTimeRoot(reasonDissociationOutput.getDivergenceTimeRoots());
		if (agreeOverTimeOutputOp.isPresent()) {
			output.setAgreeOverTimeOutput(agreeOverTimeOutputOp.get());
		}
		if (overTimeReflectOp.isPresent()) {
			output.setOverTimeReflect(overTimeReflectOp.get());
		}
		
		return output;
	}


//	@Override
//	public InfoBaseDateOutput getInfoBaseDate(String companyId,
//			String employeeId,
//			GeneralDate date,
//			OvertimeAppAtr overTimeAtr,
//			List<WorkTimeSetting> workTime,
//			Optional<AppEmploymentSet> appEmploymentSetting) {
//		// TODO Auto-generated method stub
//		return null;
//	}


	@Override
	public InfoWithDateApplication getInfoAppDate(
			String companyId,
			Optional<GeneralDate> dateOp,
			Optional<Integer> startTimeSPR,
			Optional<Integer> endTimeSPR,
			List<WorkType> workTypeLst, 
			AppDispInfoStartupOutput appDispInfoStartupOutput,
			OvertimeAppSet overtimeAppSet) {
		InfoWithDateApplication output = new InfoWithDateApplication();
		String employeeId = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid();
		Optional<AchievementDetail> archievementDetail = Optional.empty();
		if (appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().isPresent()){
			if (!CollectionUtil.isEmpty(appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get())) {
				ActualContentDisplay actualContentDisplay = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0);
				archievementDetail = actualContentDisplay.getOpAchievementDetail();
			}
		}
		// 09_勤務種類就業時間帯の初期選択をセットする
		InitWkTypeWkTimeOutput initWkTypeWkTimeOutput = commonAlgorithm.initWorkTypeWorkTime(employeeId,
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate(),
				dateOp.orElse(null),
				workTypeLst,
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpWorkTimeLst().orElse(Collections.emptyList()),
				archievementDetail.orElse(null));
		// 16_勤務種類・就業時間帯を選択する
		SelectWorkOutput selectWorkOutput = overTimeService.selectWork(
				companyId,
				employeeId,
				dateOp,
				new WorkTypeCode(initWkTypeWkTimeOutput.getWorkTypeCD()),
				new WorkTimeCode(initWkTypeWkTimeOutput.getWorkTimeCD()),
				startTimeSPR,
				endTimeSPR,
				Optional.ofNullable(appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().map(x -> x.get(0)).orElse(null)),
				overtimeAppSet);
		// 取得情報を「申請日に関係する情報」にセットして返す
		output.setWorkTypeCD(Optional.ofNullable(initWkTypeWkTimeOutput.getWorkTypeCD()));
		output.setWorkTimeCD(Optional.ofNullable(initWkTypeWkTimeOutput.getWorkTimeCD()));
		output.setBreakTime(Optional.ofNullable(selectWorkOutput.getBreakTimeZoneSetting()));
		output.setWorkHours(Optional.ofNullable(selectWorkOutput.getWorkHours()));
		output.setApplicationTime(Optional.ofNullable(selectWorkOutput.getApplicationTime()));
		
		
		return output;
		
	}


	
	
	@Override
	public BreakTimeZoneSetting selectWorkTypeAndTime(
			String companyId,
			WorkTypeCode workTypeCode,
			WorkTimeCode workTimeCode,
			Optional<TimeWithDayAttr> startTimeOp,
			Optional<TimeWithDayAttr> endTimeOp,
			Optional<AchievementDetail> achievementDetail) {
		BreakTimeZoneSetting output = new BreakTimeZoneSetting();
		// INPUT．「実績詳細．打刻実績．休憩時間帯」をチェックする
		List<TimePlaceOutput> breakTimes = Collections.emptyList();
		if (achievementDetail.isPresent()) {
			breakTimes = achievementDetail.get().getStampRecordOutput().getBreakTime();		
		}
		
		if (breakTimes.isEmpty()) {
			// 休憩時間帯を取得する
			List<DeductionTime> timeZones = commonOverTime.getBreakTimes(
					companyId,
					workTypeCode.v(),
					workTimeCode.v(),
					startTimeOp, 
					endTimeOp);
			output.setTimeZones(timeZones);
		} else {
			// 「休憩時間帯設定」<List>を作成する
			output = this.createBreakTime(startTimeOp, endTimeOp, output);
		}
		
		return output;
		
	}


	@Override
	public BreakTimeZoneSetting createBreakTime(Optional<TimeWithDayAttr> startTimeOp, Optional<TimeWithDayAttr> endTimeOp,
			BreakTimeZoneSetting breakTimeZoneSetting) {
		// Input．開始時刻とInput．終了時刻をチェック
		if (!startTimeOp.isPresent() || !endTimeOp.isPresent()) { // 開始時刻　OR　終了時刻　が無い場合
			// OUTPUT．「休憩時間帯設定」　＝　INPUT．「休憩時間帯設定」
			return breakTimeZoneSetting;
		} else {
			List<DeductionTime> result = new ArrayList<>();
			for(DeductionTime deductionTime : breakTimeZoneSetting.getTimeZones()){
				// 状態区分　＝　「重複の判断処理」を実行
				TimeWithDayAttr startTime = startTimeOp.get();
				TimeWithDayAttr endTime = endTimeOp.get();
				TimeSpanForCalc timeSpanFirstTime = new TimeSpanForCalc(endTime, startTime);
				TimeSpanForCalc timeSpanSecondTime = new TimeSpanForCalc(deductionTime.getEnd(), deductionTime.getStart());
				// アルゴリズム「時刻入力期間重複チェック」を実行する
				DuplicateStateAtr duplicateStateAtr = this.rangeOfDayTimeZoneService
						.checkPeriodDuplication(timeSpanFirstTime, timeSpanSecondTime);
				// 重複状態区分チェック
				DuplicationStatusOfTimeZone duplicationStatusOfTimeZone = this.rangeOfDayTimeZoneService
						.checkStateAtr(duplicateStateAtr);
				// 取得した状態区分をチェック
				if(duplicationStatusOfTimeZone != DuplicationStatusOfTimeZone.NON_OVERLAPPING){
					result.add(deductionTime);
				}
			}
			breakTimeZoneSetting.setTimeZones(result);
		}
		return breakTimeZoneSetting;
	}

	@Override
	public WorkHours initAttendanceTime(
			String companyId,
			Optional<GeneralDate> dateOp,
			OverTimeContent overTimeContent,
			ApplicationDetailSetting applicationDetailSetting
			) {
		WorkHours output = new WorkHours();
		// INPUT．「申請日」と「＠時間入力利用区分」をチェックする
		if (!(dateOp.isPresent() && applicationDetailSetting.getTimeInputUse() == NotUseAtr.USE)) {
			return output;
		}
		// 勤務時間を取得する
		output = this.getWorkHours(companyId, overTimeContent, applicationDetailSetting.getAtworkTimeBeginDisp(), applicationDetailSetting);
		
		// INPUT．「申請内容．SPR連携時刻」を確認する
		if (!overTimeContent.getSPRTime().isPresent()) return new WorkHours();
		// OUTPUT「勤務時間」を更新して返す
		
		return output;
	}
	@Override
	public WorkHours getWorkHours(
			String companyId,
			OverTimeContent overTimeContent,
			AtWorkAtr atworkTimeBeginDisp,
			ApplicationDetailSetting applicationDetailSetting
			) {
		WorkHours workHours = new WorkHours();
		// 「出退勤時刻初期表示区分」をチェックする
		if (atworkTimeBeginDisp == AtWorkAtr.NOTDISPLAY) { // 表示しない
			// OUTPUT「勤務時間」を返す
			return workHours;
		}
		if (atworkTimeBeginDisp == AtWorkAtr.AT_START_WORK_OFF_ENDWORK) { // 出勤は始業時刻、退勤は終業時刻を初期表示する
			// 所定時間帯を取得する
			PredetermineTimeSetForCalc predetermineTimeSetForCalc = workTimeSettingService.getPredeterminedTimezone(
					companyId,
					overTimeContent.getWorkTimeCode().map(x -> x.v()).orElse(null),
					overTimeContent.getWorkTypeCode().map(x -> x.v()).orElse(null),
					null);
			// OUTPUT「勤務時間」をセットする
			if (!predetermineTimeSetForCalc.getTimezones().isEmpty()) {
				
				for (int i = 0; i < predetermineTimeSetForCalc.getTimezones().stream().count(); i++) {
					TimezoneUse item = predetermineTimeSetForCalc.getTimezones().get(i);
					Optional<TimeWithDayAttr> startTime = Optional.of(item.getStart());
					Optional<TimeWithDayAttr> endTime = Optional.of(item.getEnd());
					if (item.getWorkNo() == TimezoneUse.SHIFT_ONE) {
						workHours.setStartTimeOp1(startTime);
						workHours.setEndTimeOp1(endTime);
					} else if (item.getWorkNo() == TimezoneUse.SHIFT_TWO) {
						workHours.setStartTimeOp2(startTime);
						workHours.setEndTimeOp2(endTime);
					}
				}
			}
			return workHours;
		}
		if (atworkTimeBeginDisp == AtWorkAtr.AT_START_WORK_OFF_PERFORMANCE) { // 出勤は始業時刻、退勤は実績の退勤を初期表示する
			// 所定時間帯を取得する
			PredetermineTimeSetForCalc predetermineTimeSetForCalc = workTimeSettingService.getPredeterminedTimezone(
					companyId,
					overTimeContent.getWorkTimeCode().map(x -> x.v()).orElse(null),
					overTimeContent.getWorkTypeCode().map(x -> x.v()).orElse(null),
					null);
			// OUTPUT「勤務時間」をセットする
			if (!predetermineTimeSetForCalc.getTimezones().isEmpty()) {
				
				for (int i = 0; i < predetermineTimeSetForCalc.getTimezones().stream().count(); i++) {
					TimezoneUse item = predetermineTimeSetForCalc.getTimezones().get(i);
					Optional<TimeWithDayAttr> startTime = Optional.of(item.getStart());
					if (item.getWorkNo() == TimezoneUse.SHIFT_ONE) {
						workHours.setStartTimeOp1(startTime);
						if (overTimeContent.getActualTime().isPresent()) {
							workHours.setEndTimeOp1(overTimeContent.getActualTime().get().getEndTimeOp1());
						}
					} else if (item.getWorkNo() == TimezoneUse.SHIFT_TWO) {
						workHours.setStartTimeOp2(startTime);
						if (overTimeContent.getActualTime().isPresent()) {
							workHours.setEndTimeOp2(overTimeContent.getActualTime().get().getEndTimeOp2());
						}
					}
				}
			}
			
		}
		if (atworkTimeBeginDisp == AtWorkAtr.DISPLAY) { // 実績から出退勤を初期表示する
			// OUTPUT「勤務時間」をセットする
			if (overTimeContent.getActualTime().isPresent()) {
				workHours = overTimeContent.getActualTime().get();				
			}
		}
		// 「退勤時刻がない時システム時刻を表示するか」をチェックする
		if (!overTimeContent.getSPRTime().isPresent()) {
			return workHours;
		}
		// OUTPUT「勤務時間．終了時刻1」をチェックする
		if (workHours.getEndTimeOp1().isPresent()) {
			return workHours;
		}
		// NULLの場合
		// システム時刻をOUTPUT「勤務時間」に更新する
		workHours.setEndTimeOp1(overTimeContent.getSPRTime().get().getEndTimeOp1());
		// OUTPUT「勤務時間」を返す
		return workHours;
	}


	@Override
	public Boolean checkOverTime(List<OvertimeApplicationSetting> applicationTime) {
		Integer timeTotal = 0;
		// INPUT「申請時間詳細」<List>をチェックする
		if (!CollectionUtil.isEmpty(applicationTime)) {
			timeTotal = applicationTime.stream()
							.filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME)
							.mapToInt(x -> x.getApplicationTime().v())
							.sum();
		} else {
			throw new BusinessException("Msg_1654");			
		}
		
		if (timeTotal <= 0) {
			throw new BusinessException("Msg_1654");	
		}
		
		return false;
	}


	@Override
	public List<ConfirmMsgOutput> checkExcess(AppOverTime appOverTime, DisplayInfoOverTime displayInfoOverTime) {
		List<ConfirmMsgOutput> output = new ArrayList<ConfirmMsgOutput>();
		// INPUT．「残業申請」をチェックする
		if (appOverTime.getPrePostAtr() != PrePostAtr.POSTERIOR) {
			return Collections.emptyList();
		}
		// INPUT．「残業申請の表示情報」をチェックする
		/**
		 * 【チェック内容】
			INPUT．「残業申請の表示情報．基準日に関係しない情報．残業申請設定．申請詳細設定．時刻計算利用区分」＝　利用しない
		*/
		Boolean c1 = displayInfoOverTime.getInfoNoBaseDate().getOverTimeAppSet().getApplicationDetailSetting().getTimeCalUse() == NotUseAtr.USE;
		/*	
		 * OR
			INPUT．「残業申請の表示情報．計算結果．残業時間帯修正フラグ」　＝　1
		 */
		Boolean c2 = displayInfoOverTime.getCalculationResultOp().map(x -> x.getOverTimeZoneFlag()).orElse(0) == 1;
		OverStateOutput overStateOutput = null;
		if (c1 || c2) {
			// 事前申請・実績の時間超過をチェックする
			overStateOutput = displayInfoOverTime.getInfoNoBaseDate()
					.getOverTimeAppSet()
					.getOvertimeLeaveAppCommonSet()
					.checkPreApplication(
						appOverTime.getPrePostAtr(),
						displayInfoOverTime.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpPreAppContentDisplayLst().map(x -> x.get(0).getApOptional().map(y -> y.getApplicationTime())).orElse(Optional.empty()),
						Optional.of(appOverTime.getApplicationTime()),
						displayInfoOverTime.getInfoWithDateApplicationOp().map(x -> x.getApplicationTime()).orElse(Optional.empty()));
			
		}
		// 取得した「事前申請・実績の超過状態．事前申請なし」をチェックする
		if (overStateOutput != null) {
			if (overStateOutput.getIsExistApp()) {
				// メッセージ（Msg_1508）をOUTPUT「確認メッセージリスト」に追加する
				output.add(new ConfirmMsgOutput("Msg_1580", Collections.emptyList()));
			} else { // false
				// 取得した「事前申請・実績の超過状態．事前超過」をチェックする
				if (overStateOutput.getAdvanceExcess().isAdvanceExcess()) {
					// handle msg
					output.add(new ConfirmMsgOutput("Msg_424", getContentMsg(displayInfoOverTime, overStateOutput.getAdvanceExcess())));
				}
				
				
			}
			List<String> contentMsgs = new ArrayList<String>();
			contentMsgs.add(appOverTime.getEmployeeID());
			contentMsgs.add(appOverTime.getApplication().getAppDate().getApplicationDate().toString("yyyy/mm/dd"));
			// 取得した「事前申請・実績の超過状態．実績状態」をチェックする
			if (overStateOutput.getAchivementStatus() == ExcessState.EXCESS_ERROR) {
				
				// エラーメッセージ（Msg_1746）を表示する
				throw new BusinessException("1746", contentMsgs.toArray(new String[contentMsgs.size()])); // note handle content's message late
			} else if (overStateOutput.getAchivementStatus() == ExcessState.EXCESS_ALARM) {
				// メッセージ（Msg_1745）をOUTPUT「確認メッセージリスト」に追加する
				output.add(new ConfirmMsgOutput("Msg_1745", contentMsgs));
				// OUTPUT「確認メッセージリスト」を返す
				
			} else {
				// 取得した「事前申請・実績の超過状態．実績超過」をチェックする
				if (overStateOutput.getAchivementExcess().isAdvanceExcessError()) {
					List<String> contens1748 = getContentMsg(displayInfoOverTime, overStateOutput.getAchivementExcess());			
					// エラーメッセージ（Msg_1748）を表示する
					throw new BusinessException("1748", contens1748.toArray(new String[contentMsgs.size()])); // note handle content's message late
				} else {
					// 取得した「事前申請・実績の超過状態．実績超過」をチェックする
					if (overStateOutput.getAchivementExcess().isAdvanceExcess()) {
						// メッセージ（Msg_1747）をOUTPUT「確認メッセージリスト」に追加する
						output.add(new ConfirmMsgOutput("Msg_1747", getContentMsg(displayInfoOverTime, overStateOutput.getAchivementExcess())));
						
					}
					// OUTPUT「確認メッセージリスト」を返す
				}
			}
			
		}
		
		return output;
	}

	public List<String> getContentMsg(DisplayInfoOverTime displayInfoOverTime, OutDateApplication outDateApplication) {
		List<String> contentMsgs = new ArrayList<String>();
		// ・申請時間の超過状態．申請時間．type = 残業時間　に超過アラームがある
		List<Integer> lstFrameOverTime = outDateApplication.getExcessStateDetail()
					 .stream()
					 .filter(x -> x.getType() == AttendanceType_Update.NORMALOVERTIME
							 && x.getExcessState() == ExcessState.EXCESS_ALARM)
					 .map(y -> y.getFrame().v())
					 .collect(Collectors.toList());
		
		// get name list of over times
		List<String> lstNameOverTime = displayInfoOverTime.getInfoBaseDateOutput()
							.getQuotaOutput()
							.getOverTimeQuotaList()
							.stream()
							.filter(x -> lstFrameOverTime.contains(x.getOvertimeWorkFrNo().v().intValue()))
							.map(y -> y.getOvertimeWorkFrName().v())
							.collect(Collectors.toList());
		contentMsgs.addAll(lstNameOverTime);
		
		// ・申請時間の超過状態．申請時間．type = 休出時間　に超過アラームがある
		List<Integer> lstFrameHoliday = outDateApplication.getExcessStateDetail()
				 .stream()
				 .filter(x -> x.getType() == AttendanceType_Update.BREAKTIME
						 && x.getExcessState() == ExcessState.EXCESS_ALARM)
				 .map(y -> y.getFrame().v())
				 .collect(Collectors.toList());
		
		List<String> lstNameHoliday = displayInfoOverTime
				.getWorkdayoffFrames()
				.stream()
				.filter(x -> lstFrameHoliday.contains(x.getWorkdayoffFrNo().v().intValue()))
				.map(y -> y.getWorkdayoffFrName().v())
				.collect(Collectors.toList());
		
		contentMsgs.addAll(lstNameHoliday);
		
		// 申請時間の超過状態．休出深夜時間．法定区分 = 法定内休出　に超過アラームがある
		// 「#KAF005_341」を{1}に追加する
		outDateApplication.getExcessStateMidnight()
					 .stream()
					 .forEach(x -> {
						 if (x.getExcessState() == ExcessState.EXCESS_ALARM) {
							 if (x.getLegalCfl() == StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork) {
								 contentMsgs.add(I18NText.getText("#KAF005_341"));
							 } else if (x.getLegalCfl() == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork) {
								 contentMsgs.add(I18NText.getText("#KAF005_342"));
							 } else if (x.getLegalCfl() == StaturoryAtrOfHolidayWork.PublicHolidayWork) {
								 contentMsgs.add(I18NText.getText("#KAF005_343"));
							 }										 
						 }
					 });
		if (outDateApplication.getFlex() == ExcessState.EXCESS_ALARM) {
			contentMsgs.add(I18NText.getText("#KAF005_63"));
		}
		
		if (outDateApplication.getOverTimeLate() == ExcessState.EXCESS_ALARM) {
			contentMsgs.add(I18NText.getText("#KAF005_65"));
		}
		return contentMsgs;
	}
	@Override
	public AppOverTime check36Limit(String companyId, AppOverTime appOverTime, Boolean isProxy, Integer mode) {
		// #112509
		List<OverTimeInput> overTimeInput = new ArrayList<OverTimeInput>();
		if (!CollectionUtil.isEmpty(appOverTime.getApplicationTime().getApplicationTime())) {
			List<OvertimeApplicationSetting> applicationTimelist = appOverTime.getApplicationTime().getApplicationTime();
			
			List<OverTimeInput> list = applicationTimelist.stream()
					.map(x -> new OverTimeInput(
							companyId,
							appOverTime.getAppID(),
							x.getAttendanceType().value,
							x.getFrameNo().v(),
							appOverTime.getWorkHoursOp().map(y -> y.get(0).getTimeZone().getStartTime().v()).orElse(null),
							appOverTime.getWorkHoursOp().map(y -> y.get(0).getTimeZone().getEndTime().v()).orElse(null),
							x.getApplicationTime().v(),
							0))
					.collect(Collectors.toList());
			overTimeInput.addAll(list);
		}
		if (appOverTime.getApplicationTime().getFlexOverTime().isPresent()) {
			OverTimeInput item = new OverTimeInput(
					companyId,
					appOverTime.getAppID(),
					AttendanceType_Update.NORMALOVERTIME.value,
					11,
					appOverTime.getWorkHoursOp().map(y -> y.get(0).getTimeZone().getStartTime().v()).orElse(null),
					appOverTime.getWorkHoursOp().map(y -> y.get(0).getTimeZone().getEndTime().v()).orElse(null),
					appOverTime.getApplicationTime().getFlexOverTime().get().v(),
					0);
			overTimeInput.add(item);
		}
		
		if (appOverTime.getApplicationTime().getOverTimeShiftNight().isPresent()) {
			OverTimeInput item = new OverTimeInput(
					companyId,
					appOverTime.getAppID(),
					AttendanceType_Update.NORMALOVERTIME.value,
					12,
					appOverTime.getWorkHoursOp().map(y -> y.get(0).getTimeZone().getStartTime().v()).orElse(null),
					appOverTime.getWorkHoursOp().map(y -> y.get(0).getTimeZone().getEndTime().v()).orElse(null),
					appOverTime.getApplicationTime().getOverTimeShiftNight().get().getOverTimeMidNight().v(),
					0);
			overTimeInput.add(item);
			List<HolidayMidNightTime> midNightHolidayTimes = appOverTime.getApplicationTime().getOverTimeShiftNight().get().getMidNightHolidayTimes();
			if (!CollectionUtil.isEmpty(midNightHolidayTimes)) {
				List<OverTimeInput> list = midNightHolidayTimes.stream()
						.map(x -> new OverTimeInput(
								companyId,
								appOverTime.getAppID(),
								AttendanceType_Update.BREAKTIME.value,
								this.convertFramNo(x.getLegalClf()),
								appOverTime.getWorkHoursOp().map(y -> y.get(0).getTimeZone().getStartTime().v()).orElse(null),
								appOverTime.getWorkHoursOp().map(y -> y.get(0).getTimeZone().getEndTime().v()).orElse(null),
								x.getAttendanceTime().v(),
								0))
						.collect(Collectors.toList());
				overTimeInput.addAll(list);
			}
		}
		Optional<AppOvertimeDetail> detailOverTimeOp = Optional.empty();
		if (mode == 0) { // 新規モードの場合
			// 03-03_３６上限チェック（月間）
			
			
			detailOverTimeOp = commonOvertimeholiday.registerOvertimeCheck36TimeLimit(
					 companyId,
					 appOverTime.getEmployeeID(),
					 appOverTime.getAppDate().getApplicationDate(),
					 overTimeInput);
		} else { // 詳細・照会モード
			// 05_３６上限チェック(詳細)
			detailOverTimeOp = commonOvertimeholiday.updateOvertimeCheck36TimeLimit(
					companyId,
					appOverTime.getEmployeeID(),
					appOverTime.getEnteredPersonID(),
					appOverTime.getEmployeeID(),
					appOverTime.getAppDate().getApplicationDate(),
					overTimeInput);
		}
		// INPUT「残業申請」を更新して返す
		appOverTime.setDetailOverTimeOp(detailOverTimeOp);
		
		return appOverTime;
	}
	// get 
	public Integer convertFramNo(StaturoryAtrOfHolidayWork legalClf) {
		if (legalClf == StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork) return 11;
		if (legalClf == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork) return 12;
		return 13;
	}

	@Override
	public void commonAlgorithmAB(String companyId,
			DisplayInfoOverTime displayInfoOverTime,
			AppOverTime appOverTime,
			Integer mode) {
		if (appOverTime == null) return;
		// 画面のモードをチェックする
		if (mode == 0) { // 新規モードの場合
			List<GeneralDate> dates = new ArrayList<GeneralDate>();
			dates.add(appOverTime.getAppDate().getApplicationDate());
			List<String> workTypeLst = new ArrayList<String>();
			workTypeLst.add(appOverTime.getWorkInfoOp().map(x -> x.getWorkTypeCode().v()).orElse(null));
			// 申請の矛盾チェック
			commonAlgorithm.appConflictCheck(
					companyId,
					displayInfoOverTime.getAppDispInfoStartup().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0),
					dates,
					workTypeLst,
					displayInfoOverTime.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().orElse(Collections.emptyList()));
		}
		
	}


	@Override
	public CheckBeforeOutput checkBeforeOverTime(
			Boolean require,
			String companyId,
			AppOverTime appOverTime,
			DisplayInfoOverTime displayInfoOverTime,
			Integer mode) {
		CheckBeforeOutput output = new CheckBeforeOutput();
		// 未計算チェックする
		commonOvertimeholiday.calculateButtonCheck(
				displayInfoOverTime.getCalculationResultOp().map(CalculationResult::getFlag).orElse(0),
				EnumAdaptor.valueOf(displayInfoOverTime.getInfoNoBaseDate().getOverTimeAppSet().getApplicationDetailSetting().getTimeCalUse().value, UseAtr.class));
		// 勤務種類、就業時間帯のマスタ未登録チェックする
		otherCommonAlgorithm.checkWorkingInfo(
				companyId,
				appOverTime.getWorkInfoOp().map(x -> x.getWorkTypeCode().v()).orElse(null),
				appOverTime.getWorkInfoOp().map(x -> x.getWorkTimeCode().v()).orElse(null));
		// 申請する残業時間をチェックする
		this.checkOverTime(appOverTime.getApplicationTime().getApplicationTime());
		// 事前申請・実績超過チェックする
		List<ConfirmMsgOutput> confirmMsgOutputs = this.checkExcess(
				appOverTime,
				displayInfoOverTime);
		// 事前申請が必須か確認する
		displayInfoOverTime.getInfoNoBaseDate().getOverTimeAppSet().getApplicationDetailSetting().checkAdvanceApp(
				ApplicationType.OVER_TIME_APPLICATION,
				appOverTime.getPrePostAtr(),
				displayInfoOverTime.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpPreAppContentDisplayLst().map(x -> x.get(0).getApOptional()).orElse(Optional.empty()),
				Optional.empty());
		// 申請時の乖離時間をチェックする not done
		overTimeService.checkDivergenceTime(require,
				ApplicationType.OVER_TIME_APPLICATION,
				Optional.of(appOverTime),
				Optional.empty(),
				displayInfoOverTime.getInfoNoBaseDate().getOverTimeAppSet().getOvertimeLeaveAppCommonSet());
		// ３６上限チェック not done
		AppOverTime appOverTime36 = null;
		
		// 申請日の矛盾チェック
		this.commonAlgorithmAB(
				companyId,
				displayInfoOverTime,
				appOverTime36,
				mode);
		// 取得した「確認メッセージリスト」と「残業申請」を返す
		// output.setAppOverTime(appOverTime36);
		// ３６上限チェック  is not done , so appOverTime = input appOverTime
		output.setAppOverTime(appOverTime);
		
		output.setConfirmMsgOutputs(confirmMsgOutputs);
		return output;
	}
	


	

}
