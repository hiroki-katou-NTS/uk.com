package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeStatusAdapter;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorCheck;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime_Old;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.CalculationResult;
import nts.uk.ctx.at.request.dom.application.overtime.OverStateOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.CheckBeforeOutput;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.ICommonAlgorithmOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.InfoBaseDateOutput;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.InfoNoBaseDate;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.InfoWithDateApplication;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;
import nts.uk.ctx.at.request.dom.workrecord.dailyrecordprocess.dailycreationwork.BreakTimeZoneSetting;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class OvertimeServiceImpl implements OvertimeService {
	

	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	@Inject
	private OvertimeRepository overTimeRepository;
	@Inject
	ApplicationApprovalService appRepository;
	
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	@Inject
	private CollectAchievement collectAchievement;
	
	@Inject
	private AgreementTimeStatusAdapter agreementTimeStatusAdapter;
	
	@Inject 
	private CommonOvertimeHoliday commonOvertimeHoliday;
	
	@Inject
	private WorkdayoffFrameRepository workdayoffFrameRepository;
	
	@Inject
	private ICommonAlgorithmOverTime commonAlgorithmOverTime;
	
	@Inject
	private PreActualColorCheck preActualColorCheck;
	
	@Inject
	private NewBeforeRegister newBeforeRegister;
	
	@Override
	public int checkOvertimeAtr(String url) {
		if(url == null){
			return OverTimeAtr.ALL.value;
		}
		switch(url){
		case "0":
			return OverTimeAtr.PREOVERTIME.value;
		case "1":
			return OverTimeAtr.REGULAROVERTIME.value;
		case "2":
			return OverTimeAtr.ALL.value;
		default:
			return OverTimeAtr.ALL.value;
			
		}
//			if(url.equals("0")){
//				return OverTimeAtr.PREOVERTIME.value;
//			}else if(url.equals("1")){
//				return OverTimeAtr.REGULAROVERTIME.value;
//			}else if(url.equals("2")){
//				return OverTimeAtr.ALL.value;
//			}
//		return 2;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService#getWorkType(java.lang.String, java.lang.String, java.util.Optional, java.util.Optional)
	 */
//	@Override
//	public List<WorkTypeOvertime> getWorkType(String companyID, String employeeID,
//			ApprovalFunctionSetting approvalFunctionSetting,Optional<AppEmploymentSetting> appEmploymentSettings) {
//		List<WorkTypeOvertime> result = new ArrayList<>();
//		// 時刻計算利用チェック
//		// アルゴリズム「社員所属雇用履歴を取得」を実行する 
//		SEmpHistImport sEmpHistImport = employeeAdapter.getEmpHist(companyID, employeeID, GeneralDate.today());
//		
//		if (sEmpHistImport != null 
//				&& appEmploymentSettings.isPresent()) {
//			//ドメインモデル「申請別対象勤務種類」.勤務種類リストを表示する(hien thi list(申請別対象勤務種類))
//			List<AppEmployWorkType> lstEmploymentWorkType = CollectionUtil.isEmpty(appEmploymentSettings.get().getListWTOAH()) ? null : 
//				CollectionUtil.isEmpty(appEmploymentSettings.get().getListWTOAH().get(0).getWorkTypeList()) ? null :
//					appEmploymentSettings.get().getListWTOAH().get(0).getWorkTypeList()
//					.stream().map(x -> new AppEmployWorkType(companyID, employeeID, appEmploymentSettings.get().getListWTOAH().get(0).getAppType(),
//							appEmploymentSettings.get().getListWTOAH().get(0).getAppType().value == 10 ? appEmploymentSettings.get().getListWTOAH().get(0).getSwingOutAtr().get().value : appEmploymentSettings.get().getListWTOAH().get(0).getAppType().value == 1 ? appEmploymentSettings.get().getListWTOAH().get(0).getHolidayAppType().get().value : 9, x))
//					.collect(Collectors.toList());;
//			if(!CollectionUtil.isEmpty(lstEmploymentWorkType)) {
//				Collections.sort(lstEmploymentWorkType, Comparator.comparing(AppEmployWorkType :: getWorkTypeCode));
//				List<String> workTypeCodes = new ArrayList<>();
//				lstEmploymentWorkType.forEach(x -> {workTypeCodes.add(x.getWorkTypeCode());});			
//				result = this.workTypeRepository.findNotDeprecatedByListCode(companyID, workTypeCodes).stream()
//						.map(x -> new WorkTypeOvertime(x.getWorkTypeCode().v(), x.getName().v())).collect(Collectors.toList());
//				if(CollectionUtil.isEmpty(result)) {
//					throw new BusinessException("Msg_1567");
//				}
//				return result;
//			}
//		}
//		List<Integer> allDayAtrs = allDayAtrs();
//		List<Integer> halfAtrs = halfAtrs();
//		result = workTypeRepository.findWorkType(companyID, 0, allDayAtrs, halfAtrs).stream()
//				.map(x -> new WorkTypeOvertime(x.getWorkTypeCode().v(), x.getName().v())).collect(Collectors.toList());
//		if(CollectionUtil.isEmpty(result)) {
//			throw new BusinessException("Msg_1567");
//		}
//		return result;
//	}
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

//	@Override
//	public List<SiftType> getSiftType(String companyID, String employeeID,
//			ApprovalFunctionSetting approvalFunctionSetting,GeneralDate baseDate) {
//		List<SiftType> result = new ArrayList<>();
//		// 1.職場別就業時間帯を取得
//		List<String> listWorkTimeCodes = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, employeeID,baseDate)
//				.stream().map(x -> x.getWorktimeCode().v()).collect(Collectors.toList());
//		
//		if(!CollectionUtil.isEmpty(listWorkTimeCodes)){
//			List<WorkTimeSetting> workTimes =  workTimeRepository.findByCodes(companyID,listWorkTimeCodes);
//			for(WorkTimeSetting workTime : workTimes){
//				SiftType siftType = new SiftType();
//				siftType.setSiftCode(workTime.getWorktimeCode().toString());
//				siftType.setSiftName(workTime.getWorkTimeDisplayName().getWorkTimeName().toString());
//				result.add(siftType);
//			}
//			return result;
//		}
//		return Collections.emptyList();
//	}

	/**
	 * 登録処理を実行
	 */
	@Override
	public void CreateOvertime(AppOverTime_Old domain, Application newApp){
		//Register application
		// error EA refactor 4
		/*appRepository.insert(newApp);*/
		//Register overtime
		overTimeRepository.Add(domain);
	}

	@Override
	/** 09_勤務種類就業時間帯の初期選択をセットする */
	public WorkTypeAndSiftType getWorkTypeAndSiftTypeByPersonCon(String companyID,String employeeID, GeneralDate baseDate,
			List<WorkTypeOvertime> workTypes, List<SiftType> siftTypes) {
		WorkTypeAndSiftType workTypeAndSiftType = new WorkTypeAndSiftType();
		if (baseDate != null) {
			//申請日の入力があり
			//勤務種類と就業時間帯を取得できた
			workTypeAndSiftType = getDataDateExists(companyID, employeeID, baseDate);
            if (StringUtil.isNullOrEmpty(workTypeAndSiftType.getWorkType().getWorkTypeCode(), true)
                    || StringUtil.isNullOrEmpty(workTypeAndSiftType.getSiftType().getSiftCode(), true)) {
				//取得できなかった
				workTypeAndSiftType = getDataNoDateExists(companyID, employeeID, workTypes, siftTypes);
			}
		} else {
			//申請日の入力がない
			workTypeAndSiftType = getDataNoDateExists(companyID, employeeID, workTypes, siftTypes);
		}
		
		if (workTypeAndSiftType.getWorkType() != null && workTypeAndSiftType.getSiftType() != null) {
			// 12.マスタ勤務種類、就業時間帯データをチェック
			CheckWorkingInfoResult checkResult = otherCommonAlgorithm.checkWorkingInfo(companyID,
					workTypeAndSiftType.getWorkType().getWorkTypeCode(),
					workTypeAndSiftType.getSiftType().getSiftCode());
			boolean wkTypeError = checkResult.isWkTypeError();
			boolean wkTimeError = checkResult.isWkTimeError();
			if (wkTypeError) {
				// 先頭の勤務種類を選択する
				workTypeAndSiftType.setWorkType(workTypes.get(0));
			}

			if (wkTimeError) {
				// 先頭の就業時間帯を選択する
				workTypeAndSiftType.setSiftType(siftTypes.get(0));
			}
		}
		return workTypeAndSiftType;
	}
	
	private WorkTypeAndSiftType getDataNoDateExists(String companyID, String employeeID,
			List<WorkTypeOvertime> workTypes, List<SiftType> siftTypes) {
		WorkTypeAndSiftType workTypeAndSiftType = new WorkTypeAndSiftType();
		WorkTypeOvertime workTypeOvertime = new  WorkTypeOvertime();
		SiftType siftType = new SiftType();
		GeneralDate baseDate = GeneralDate.today();
		//ドメインモデル「個人労働条件」を取得する(lay dieu kien lao dong ca nhan(個人労働条件))
		Optional<WorkingConditionItem> personalLablorCodition = workingConditionItemRepository.getBySidAndStandardDate(employeeID,baseDate);
		
		if(!personalLablorCodition.isPresent() || personalLablorCodition.get().getWorkCategory().getWeekdayTime() == null){
			//先頭の勤務種類を選択する
			if(!CollectionUtil.isEmpty(workTypes)){
				workTypeAndSiftType.setWorkType(workTypes.get(0));
			}
			//先頭の就業時間帯を選択する
			if(!CollectionUtil.isEmpty(siftTypes)){
				workTypeAndSiftType.setSiftType(siftTypes.get(0));
			}
		}else{
			
			String wktypeCd = personalLablorCodition.get().getWorkCategory().getWeekdayTime().getWorkTypeCode().get()
					.v().toString();
			if(workTypes.stream().map(x -> x.getWorkTypeCode()).collect(Collectors.toList()).contains(wktypeCd)){
				workTypeOvertime = workTypes.stream().filter(x -> x.getWorkTypeCode().equals(wktypeCd)).findAny().get();
				//ドメインモデル「個人勤務日区分別勤務」．平日時．勤務種類コードを選択する
				workTypeAndSiftType.setWorkType(workTypeOvertime);
			} else {
				//先頭の勤務種類を選択する
				if(!CollectionUtil.isEmpty(workTypes)){
					workTypeAndSiftType.setWorkType(workTypes.get(0));
				}
			}
			
			//ドメインモデル「個人勤務日区分別勤務」．平日時．就業時間帯コードを選択する
			String wkTimeCd = personalLablorCodition.get().getWorkCategory().getWeekdayTime().getWorkTimeCode().get()
					.v().toString();
			if(siftTypes.stream().map(x -> x.getSiftCode()).collect(Collectors.toList()).contains(wkTimeCd)){
				siftType = siftTypes.stream().filter(x -> x.getSiftCode().equals(wkTimeCd)).findAny().get();
				workTypeAndSiftType.setSiftType(siftType);
			} else {
				if(!CollectionUtil.isEmpty(siftTypes)){
					workTypeAndSiftType.setSiftType(siftTypes.get(0));
				}
			}
		}
		return workTypeAndSiftType;
	}

	private WorkTypeAndSiftType getDataDateExists(String companyID, String employeeID, GeneralDate baseDate) {
		WorkTypeAndSiftType workTypeAndSiftType = new WorkTypeAndSiftType();
		//実績の取得
		/*AchievementOutput achievementOutput = collectAchievement.getAchievement(companyID, employeeID, baseDate);
			workTypeAndSiftType.setWorkType(new WorkTypeOvertime(achievementOutput.getWorkType().getWorkTypeCode(), achievementOutput.getWorkType().getName()));
			workTypeAndSiftType.setSiftType(new SiftType(achievementOutput.getWorkTime().getWorkTimeCD(), achievementOutput.getWorkTime().getWorkTimeName()));
			return workTypeAndSiftType;*/
		return null;
	}

	@Override
	public AgreementTimeStatusOfMonthly getTime36Detail(AppOvertimeDetail appOvertimeDetail) {
		if(appOvertimeDetail.getTime36Agree().getAgreeMonth().getLimitErrorTime().v() <= 0){
			return null;
		}
		/** TODO: 36協定時間対応により、コメントアウトされた */
		return null;
//		return agreementTimeStatusAdapter.checkAgreementTimeStatus(
//				new AttendanceTimeMonth(appOvertimeDetail.getTime36Agree().getApplicationTime().v()+appOvertimeDetail.getTime36Agree().getAgreeMonth().getActualTime().v()), 
//				appOvertimeDetail.getTime36Agree().getAgreeMonth().getLimitAlarmTime(), 
//				appOvertimeDetail.getTime36Agree().getAgreeMonth().getLimitErrorTime(), 
//				appOvertimeDetail.getTime36Agree().getAgreeMonth().getExceptionLimitAlarmTime(), 
//				appOvertimeDetail.getTime36Agree().getAgreeMonth().getExceptionLimitErrorTime());
	}

	@Override
	public DisplayInfoOverTime calculate(String companyId,
			String employeeId,
			Optional<GeneralDate> dateOp,
			PrePostAtr prePostInitAtr,
			OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet,
			ApplicationTime advanceApplicationTime,
			ApplicationTime achieveApplicationTime,
			WorkContent workContent) {
		DisplayInfoOverTime output = new DisplayInfoOverTime();
		// 計算処理
		CaculationOutput caculationOutput = this.getCalculation(
				companyId,
				employeeId,
				dateOp,
				prePostInitAtr,
				overtimeLeaveAppCommonSet,
				advanceApplicationTime,
				achieveApplicationTime,
				workContent);
		// 取得した「休出枠<List>」を「残業申請の表示情報」にセットする
		output.setCalculationResultOp(Optional.of(caculationOutput.getCalculationResult()));
		output.setWorkdayoffFrames(caculationOutput.getWorkdayoffFrames());
		// 残業時間帯の値と背景色をセット
		return output;
	}

	@Override
	public CaculationOutput getCalculation(String companyId,
			String employeeId,
			Optional<GeneralDate> dateOp,
			PrePostAtr prePostInitAtr,
			OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet,
			ApplicationTime advanceApplicationTime,
			ApplicationTime achieveApplicationTime,
			WorkContent workContent) {
		CaculationOutput ouput = new CaculationOutput();
		// INPUTをチェックする
		if (	!dateOp.isPresent() 
				|| StringUtils.isBlank(workContent.getWorkTypeCode()) 
				|| StringUtils.isBlank(workContent.getWorkTimeCode()) 
				|| workContent.getTimeZones().isEmpty()) return null;
		// 06_計算処理
		List<ApplicationTime> applicationTimes = commonOvertimeHoliday.calculator(companyId, employeeId, dateOp.orElse(null), workContent.getWorkTypeCode(), workContent.getWorkTimeCode(), workContent.getTimeZones(), workContent.getBreakTimes());
		
		// 事前申請・実績の時間超過をチェックする
		OverStateOutput overStateOutput = overtimeLeaveAppCommonSet.checkPreApplication(prePostInitAtr, Optional.ofNullable(advanceApplicationTime), applicationTimes.isEmpty() ? Optional.empty() : Optional.of(applicationTimes.get(0)), Optional.ofNullable(achieveApplicationTime));
		// 【チェック内容】
		// 取得したList「申請時間．type」 = 休出時間　がある場合
		applicationTimes.get(0).getApplicationTime().forEach(item -> {
			// 休出時間をチェックする
			if (item.getAttendanceType() == AttendanceType_Update.BREAKTIME) {
				List<WorkdayoffFrame> workdayoffFrames = workdayoffFrameRepository.findByUseAtr(companyId, NotUseAtr.NOT_USE.value);
				ouput.setWorkdayoffFrames(workdayoffFrames);
			}
		});
		// OUTPUT「計算結果」をセットして取得した「休出枠」と一緒に返す
		CalculationResult calculationResult = new CalculationResult();
		calculationResult.setFlag(0);
		calculationResult.setOverTimeZoneFlag(0);
		calculationResult.setOverStateOutput(overStateOutput);
		calculationResult.setApplicationTimes(applicationTimes);
		
		return ouput;
	}

	@Override
	public DisplayInfoOverTime getInitData(String companyId,
			Optional<GeneralDate> dateOp,
			OvertimeAppAtr overtimeAppAtr,
			AppDispInfoStartupOutput appDispInfoStartupOutput,
			Optional<Integer> startTimeSPR,
			Optional<Integer> endTimeSPR,
			Boolean isProxy) {
		// get employeeId
		String employeeId = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid();
		DisplayInfoOverTime output = new DisplayInfoOverTime();
		// 基準日に関係ない情報を取得する
		InfoNoBaseDate infoNoBaseDate= commonAlgorithmOverTime.getInfoNoBaseDate(companyId,
				employeeId,
				overtimeAppAtr);
		// 基準日に関する情報を取得する
		InfoBaseDateOutput infoBaseDateOutput = commonAlgorithmOverTime.getInfoBaseDate(companyId,
				employeeId,
				dateOp.orElse(null),
				overtimeAppAtr,
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpWorkTimeLst().orElse(Collections.emptyList()),
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpEmploymentSet());
		// 申請日に関する情報を取得する
		InfoWithDateApplication infoWithDateApplication = commonAlgorithmOverTime.getInfoAppDate(
				companyId,
				dateOp,
				startTimeSPR,
				endTimeSPR,
				infoBaseDateOutput.getWorktypes(),
				appDispInfoStartupOutput,
				infoNoBaseDate.getOverTimeAppSet());
		// 取得した情報をOUTPUT「勤務変更申請の表示情報」にセットしてを返す
		output.setAppDispInfoStartup(appDispInfoStartupOutput);
		output.setInfoBaseDateOutput(infoBaseDateOutput);
		output.setInfoNoBaseDate(infoNoBaseDate);
		output.setInfoWithDateApplicationOp(Optional.of(infoWithDateApplication));
		CalculationResult calculationResult = new CalculationResult();
		calculationResult.setFlag(1);
		calculationResult.setOverTimeZoneFlag(1);
		output.setCalculationResultOp(Optional.of(calculationResult));
		output.setIsProxy(isProxy);
		output.setOvertimeAppAtr(overtimeAppAtr);
		return output;
	}



	@Override
	public SelectWorkOutput selectWork(
			String companyId,
			Optional<GeneralDate> dateOp,
			WorkTypeCode workTypeCode,
			WorkTimeCode workTimeCode,
			Optional<Integer> startTimeSPR,
			Optional<Integer> endTimeSPR,
			ActualContentDisplay actualContentDisplay,
			OvertimeAppSet overtimeAppSet) {
		SelectWorkOutput output = new SelectWorkOutput();
		OverTimeContent overTimeContent = new OverTimeContent();
		Optional<WorkTypeCode> workTypeCodeOp = Optional.of(workTypeCode);
		Optional<WorkTimeCode> workTimeCodeOp = Optional.of(workTimeCode);
		WorkHours workHoursSPR = new WorkHours();
		if (startTimeSPR.isPresent()) {
			workHoursSPR.setStartTimeOp1(Optional.of(new TimeWithDayAttr(endTimeSPR.get())));
		}
		if (endTimeSPR.isPresent()) {
			workHoursSPR.setEndTimeOp1(Optional.of(new TimeWithDayAttr(startTimeSPR.get())));
		}
		WorkHours workHoursActual = new WorkHours();
		if (actualContentDisplay.getOpAchievementDetail().isPresent()) {
			AchievementDetail achievementDetail = actualContentDisplay.getOpAchievementDetail().get();
			if (achievementDetail.getOpWorkTime().isPresent()) {
				workHoursActual.setStartTimeOp1(Optional.of(new TimeWithDayAttr(achievementDetail.getOpWorkTime().get())));
			}
			if (achievementDetail.getOpLeaveTime().isPresent()) {
				workHoursActual.setEndTimeOp1(Optional.of(new TimeWithDayAttr(achievementDetail.getOpLeaveTime().get())));
			}
			if (achievementDetail.getOpWorkTime2().isPresent()) {
				workHoursActual.setStartTimeOp2(Optional.of(new TimeWithDayAttr(achievementDetail.getOpWorkTime2().get())));
			}
			if (achievementDetail.getOpDepartureTime2().isPresent()) {
				workHoursActual.setEndTimeOp2(Optional.of(new TimeWithDayAttr(achievementDetail.getOpDepartureTime2().get())));
			}
			
		}
		overTimeContent.setWorkTypeCode(workTypeCodeOp);
		overTimeContent.setWorkTimeCode(workTimeCodeOp);
		overTimeContent.setSPRTime(Optional.of(workHoursSPR));
		overTimeContent.setActualTime(Optional.of(workHoursActual));
		// 初期表示する出退勤時刻を取得する
		WorkHours workHours = commonAlgorithmOverTime.initAttendanceTime(
				companyId,
				dateOp,
				overTimeContent,
				overtimeAppSet.getApplicationDetailSetting());
		
		// 休憩時間帯を取得する
		BreakTimeZoneSetting breakTimes = commonAlgorithmOverTime.selectWorkTypeAndTime(
				companyId,
				workTypeCode,
				workTimeCode,
				startTimeSPR.map(x -> Optional.of(new TimeWithDayAttr(x))).orElse(Optional.empty()),
				endTimeSPR.map(x -> Optional.of(new TimeWithDayAttr(x))).orElse(Optional.empty()),
				actualContentDisplay.getOpAchievementDetail().orElse(null));
		// 07-02_実績取得・状態チェック
		ApplicationTime applicationTime = preActualColorCheck.checkStatus(
				companyId,
				null, // QA
				dateOp.orElse(null),
				ApplicationType.OVER_TIME_APPLICATION,
				workTypeCode,
				workTimeCode,
				overtimeAppSet.getOvertimeLeaveAppCommonSet().getOverrideSet(),
				Optional.empty(),
				breakTimes.getTimeZones(),
				actualContentDisplay);
		output.setWorkHours(workHours);
		output.setBreakTimeZoneSetting(breakTimes);
		output.setApplicationTime(applicationTime);
		return output;
	}

	@Override
	public OverTimeOutput getStart() {
		// TODO Auto-generated method stub
		return null;
	}
	// pending
	@Override
	public void checkDivergenceTime(
			Boolean require,
			ApplicationType appType,
			Optional<AppOverTime> appOverOptional,
			Optional<AppOverTime> appHolidayOptional,
			OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet) {
		// 「@登録時の乖離時間チェック」をチェックする
		if (overtimeLeaveAppCommonSet.getCheckDeviationRegister() == nts.uk.shr.com.enumcommon.NotUseAtr.NOT_USE) return;
		// 日別実績への申請反映結果を取得 not done
		
		// 取得した「日別勤怠(Work)」から「日別勤怠(Work)．エラー一覧」を取得する
		
		
		
	}

	@Override
	public CheckBeforeOutput checkErrorRegister(
			Boolean require,
			String companyId,
			DisplayInfoOverTime displayInfoOverTime,
			AppOverTime appOverTime) {
		CheckBeforeOutput output = new CheckBeforeOutput();
		// 2-1.新規画面登録前の処理
		List<ConfirmMsgOutput> confirmMsgOutputs = newBeforeRegister.processBeforeRegister_New(
				companyId,
				EmploymentRootAtr.COMMON, // QA 112515 done
				displayInfoOverTime.getIsProxy(),
				appOverTime,
				appOverTime.getOverTimeClf(),
				displayInfoOverTime.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpErrorFlag().orElse(null),
				Collections.emptyList(), 
				displayInfoOverTime.getAppDispInfoStartup());
		// 残業申請の個別登録前チェッ処理
		output = commonAlgorithmOverTime.checkBeforeOverTime(
				require,
				companyId,
				appOverTime,
				displayInfoOverTime,
				0); // QA input 112515 done
		// 取得した「確認メッセージリスト」と「残業申請」を返す
		output.getConfirmMsgOutputs().addAll(confirmMsgOutputs);
		
		return output;
	}
}
