package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorCheck;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.commonalgorithm.ICommonAlgorithmHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.CalculatedFlag;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.CheckBeforeOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdSelectWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkCalculationResult;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverStateOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.ICommonAlgorithmOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.InfoNoBaseDate;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.QuotaOuput;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.ReasonDissociationOutput;
import nts.uk.ctx.at.request.dom.application.overtime.service.OverTimeContent;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkContent;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkHours;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.CalcStampMiss;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.OverrideSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.workrecord.dailyrecordprocess.dailycreationwork.BreakTimeZoneSetting;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Stateless
public class HolidayServiceImpl implements HolidayService {
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private CommonOvertimeHoliday commonOverTimeHoliday;
	
	@Inject
	private ICommonAlgorithmHolidayWork commonHolidayWorkAlgorithm;
	
	@Inject
	private ICommonAlgorithmOverTime commonOverTimeAlgorithm;
	
	@Inject
	private WorkdayoffFrameRepository workdayoffFrameRepository;
	
	@Inject
	private PreActualColorCheck preActualColorCheck;
	
	@Inject
	private NewBeforeRegister processBeforeRegister;

	@Override
	public AppHdWorkDispInfoOutput startA(String companyId, Optional<List<String>> empList,
			Optional<List<GeneralDate>> dateList, AppDispInfoStartupOutput appDispInfoStartupOutput) {
		AppHdWorkDispInfoOutput appHdWorkDispInfoOutput = new AppHdWorkDispInfoOutput();
		appHdWorkDispInfoOutput.setAppDispInfoStartupOutput(appDispInfoStartupOutput);
		
		//	休出申請の設定を取得する
		HolidayWorkAppSet holidayWorkSetting = commonHolidayWorkAlgorithm.getHolidayWorkSetting(companyId);
		AppReflectOtHdWork hdWorkOvertimeReflect = commonHolidayWorkAlgorithm.getHdWorkOvertimeReflect(companyId);
		appHdWorkDispInfoOutput.setHolidayWorkAppSet(holidayWorkSetting);
		appHdWorkDispInfoOutput.setHdWorkOvertimeReflect(hdWorkOvertimeReflect);
		
		//01-02_時間外労働を取得
		String employeeId = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid();
		Optional<AgreeOverTimeOutput> agreeOvertimeOutput = commonOverTimeHoliday.getAgreementTime(companyId, employeeId, ApplicationType.HOLIDAY_WORK_APPLICATION);
		appHdWorkDispInfoOutput.setOtWorkHoursForApplication(agreeOvertimeOutput.orElse(null));
		
		//1-1.休日出勤申請（新規）起動時初期データを取得する
		String applicantEmployeeId = empList.isPresent() ? empList.get().get(0) : "";
		Optional<GeneralDate> applicationDate = dateList.isPresent() ? Optional.of(dateList.get().get(0)) : Optional.empty();
		GeneralDate baseDate = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate();
		PrePostInitAtr prePostAtr = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getPrePostAtr();
		AppEmploymentSet employmentSet = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpEmploymentSet().orElse(null);
		List<WorkTimeSetting> workTimeList = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpWorkTimeLst().orElse(new ArrayList<>());
		List<ActualContentDisplay> actualContentDisplayList = 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().orElse(new ArrayList<>());
		  
		HdWorkDispInfoWithDateOutput hdWorkDispInfoWithDateOutput = commonHolidayWorkAlgorithm.getHdWorkDispInfoWithDateOutput(companyId, applicantEmployeeId, 
				applicationDate, baseDate, prePostAtr, employmentSet, workTimeList, holidayWorkSetting, hdWorkOvertimeReflect, actualContentDisplayList);
		
		appHdWorkDispInfoOutput.setHdWorkDispInfoWithDateOutput(hdWorkDispInfoWithDateOutput);
		
		//01-03_休出時間枠を取得
		List<WorkdayoffFrame> workdayoffFrameList = workdayoffFrameRepository.getAllWorkdayoffFrame(companyId);
		appHdWorkDispInfoOutput.setWorkdayoffFrameList(workdayoffFrameList);
		
		//	指定社員の申請残業枠を取得する 
		InfoNoBaseDate infoNoBaseDate= commonOverTimeAlgorithm.getInfoNoBaseDate(companyId,
				employeeId,
				OvertimeAppAtr.EARLY_NORMAL_OVERTIME);
		QuotaOuput quotaOutput = commonOverTimeAlgorithm.getOvertimeQuotaSetUse(companyId, employeeId, baseDate, 
				OvertimeAppAtr.EARLY_NORMAL_OVERTIME, infoNoBaseDate.getOverTimeAppSet());
		appHdWorkDispInfoOutput.setDispFlexTime(NotUseAtr.valueOf(quotaOutput.getFlexTimeClf() ? 1 : 0));
		appHdWorkDispInfoOutput.setOvertimeFrameList(quotaOutput.getOverTimeQuotaList());
		
		//	乖離理由の表示区分を取得する
		ReasonDissociationOutput reasonDissociationOutput = commonOverTimeAlgorithm.getInfoNoBaseDate(companyId, ApplicationType.HOLIDAY_WORK_APPLICATION, 
				null, holidayWorkSetting.getOvertimeLeaveAppCommonSet());
		if(!reasonDissociationOutput.getDivergenceReasonInputMethod().isEmpty()) {
			appHdWorkDispInfoOutput.setUseInputDivergenceReason(reasonDissociationOutput.getDivergenceReasonInputMethod().get(0).isDivergenceReasonInputed());
			appHdWorkDispInfoOutput.setUseComboDivergenceReason(reasonDissociationOutput.getDivergenceReasonInputMethod().get(0).isDivergenceReasonSelected());
			appHdWorkDispInfoOutput.setComboDivergenceReason(Optional.of(reasonDissociationOutput.getDivergenceReasonInputMethod().get(0).getReasons().get(0)));
		}	
		
		return appHdWorkDispInfoOutput;
	}

	@Override
	public HolidayWorkCalculationResult calculate(String companyId, String employeeId, Optional<GeneralDate> date,
			PrePostInitAtr prePostAtr, OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet,
			ApplicationTime preApplicationTime, ApplicationTime actualApplicationTime, WorkContent workContent) {
		//INPUTをチェックする
		if(!date.isPresent() || !workContent.getWorkTypeCode().isPresent() || !workContent.getWorkTimeCode().isPresent() || workContent.getTimeZones().isEmpty()) {
			return null;
		}
		//06_計算処理
		List<ApplicationTime> applicationTimes = commonOverTimeHoliday.calculator(companyId, employeeId, date.get() , workContent.getWorkTypeCode(), workContent.getWorkTimeCode(), 
				workContent.getTimeZones(), workContent.getBreakTimes());
		//	事前申請・実績の時間超過をチェックする
		OverStateOutput overStateOutput = overtimeLeaveAppCommonSet.checkPreApplication(EnumAdaptor.valueOf(prePostAtr.value, PrePostAtr.class), 
				Optional.of(preApplicationTime), applicationTimes.isEmpty() ? Optional.empty() : Optional.of(applicationTimes.get(0)), Optional.of(actualApplicationTime));
		
		
		return new HolidayWorkCalculationResult(overStateOutput, !applicationTimes.isEmpty() ? applicationTimes.get(0) : null, CalculatedFlag.CALCULATED);
	}

	@Override
	public AppHdWorkDispInfoOutput changeAppDate(String companyId, List<GeneralDate> dateList, ApplicationType applicationType,
			AppHdWorkDispInfoOutput appHdWorkDispInfoOutput) {
		AppDispInfoStartupOutput appDispInfoStartupOutput = appHdWorkDispInfoOutput.getAppDispInfoStartupOutput();
		
		//	申請日を変更する
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = commonAlgorithm.changeAppDateProcess(companyId, dateList, applicationType, appDispInfoStartupOutput.getAppDispInfoNoDateOutput(), 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput(), Optional.empty());
		appDispInfoStartupOutput.setAppDispInfoWithDateOutput(appDispInfoWithDateOutput);
		appHdWorkDispInfoOutput.setAppDispInfoStartupOutput(appDispInfoStartupOutput);
		
		//	1-1.休日出勤申請（新規）起動時初期データを取得する
		String employeeId = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid();
		
		HdWorkDispInfoWithDateOutput hdWorkDispInfoWithDateOutput = commonHolidayWorkAlgorithm.getHdWorkDispInfoWithDateOutput(companyId, employeeId, 
				Optional.of(dateList.get(0)), appDispInfoWithDateOutput.getBaseDate(), 
				appDispInfoWithDateOutput.getPrePostAtr(), appDispInfoWithDateOutput.getOpEmploymentSet().orElse(null), 
				appDispInfoWithDateOutput.getOpWorkTimeLst().orElse(Collections.emptyList()), 
				appHdWorkDispInfoOutput.getHolidayWorkAppSet(), appHdWorkDispInfoOutput.getHdWorkOvertimeReflect(), 
				appDispInfoWithDateOutput.getOpActualContentDisplayLst().orElse(Collections.emptyList()));
		appHdWorkDispInfoOutput.setHdWorkDispInfoWithDateOutput(hdWorkDispInfoWithDateOutput);
		
		// 	計算（従業員）
		WorkContent workContent = commonHolidayWorkAlgorithm.getWorkContent(hdWorkDispInfoWithDateOutput);
		
		HolidayWorkCalculationResult calculationResult = this.calculate(companyId, employeeId, Optional.ofNullable(dateList.get(0)), 
				appDispInfoWithDateOutput.getPrePostAtr(), appHdWorkDispInfoOutput.getHolidayWorkAppSet().getOvertimeLeaveAppCommonSet(), 
				appDispInfoWithDateOutput.getOpPreAppContentDisplayLst().orElse(Collections.emptyList()).get(0).getAppHolidayWork().orElse(null).getApplicationTime(), 
				hdWorkDispInfoWithDateOutput.getActualApplicationTime().orElse(null), workContent);
		appHdWorkDispInfoOutput.setCalculationResult(Optional.ofNullable(calculationResult));
		
		return appHdWorkDispInfoOutput;
	}

	@Override
	public HdSelectWorkDispInfoOutput selectWork(String companyId, GeneralDate date, WorkTypeCode workType,
			WorkTimeCode workTime, List<ActualContentDisplay> actualContentDisplayList,
			AppDispInfoStartupOutput appDispInfoStartupOutput, HolidayWorkAppSet holidayWorkAppSet) {
		HdSelectWorkDispInfoOutput hdSelectWorkDispInfoOutput = new HdSelectWorkDispInfoOutput();
		
		//	初期表示する出退勤時刻を取得する
		OverTimeContent overTimeContent = commonHolidayWorkAlgorithm.getOverTimeContent(Optional.of(workType), Optional.of(workTime), 
				actualContentDisplayList);
		WorkHours workHours = commonOverTimeAlgorithm.initAttendanceTime(companyId, Optional.of(date), overTimeContent, 
				holidayWorkAppSet.getApplicationDetailSetting());
		hdSelectWorkDispInfoOutput.setWorkHours(workHours);
		
		//	休憩時間帯を取得する
		Optional<AchievementDetail> achievementDetail = !actualContentDisplayList.isEmpty() ? 
				actualContentDisplayList.get(0).getOpAchievementDetail() : Optional.empty();
		BreakTimeZoneSetting breakTimeZoneSettingList = commonOverTimeAlgorithm.selectWorkTypeAndTime(companyId, workType, 
				workTime, workHours.getStartTimeOp1(), workHours.getEndTimeOp1(), achievementDetail);
		hdSelectWorkDispInfoOutput.setBreakTimeZoneSettingList(Optional.of(breakTimeZoneSettingList));
		
		//07-02_実績取得・状態チェック
		ApplicationTime actualApplicationTime = new ApplicationTime();
		if(appDispInfoStartupOutput.getAppDetailScreenInfo().isPresent() 
				&& (appDispInfoStartupOutput.getAppDetailScreenInfo().get().getUser().equals(User.APPROVER) 
						|| appDispInfoStartupOutput.getAppDetailScreenInfo().get().getUser().equals(User.OTHER))) {
			actualApplicationTime = preActualColorCheck.checkStatus(companyId, appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(), 
					date, ApplicationType.HOLIDAY_WORK_APPLICATION, workType, workTime, OverrideSet.TIME_OUT_PRIORITY, 
					Optional.of(CalcStampMiss.CAN_NOT_REGIS), breakTimeZoneSettingList.getTimeZones(), 
					!actualContentDisplayList.isEmpty() ? Optional.of(actualContentDisplayList.get(0)): Optional.empty());
		}
		if(!appDispInfoStartupOutput.getAppDetailScreenInfo().isPresent() 
				|| (appDispInfoStartupOutput.getAppDetailScreenInfo().get().getUser().equals(User.APPLICANT) 
						|| appDispInfoStartupOutput.getAppDetailScreenInfo().get().getUser().equals(User.APPLICANT_APPROVER))) {
			actualApplicationTime = preActualColorCheck.checkStatus(companyId, appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(), 
					date, ApplicationType.HOLIDAY_WORK_APPLICATION, workType, workTime, holidayWorkAppSet.getOvertimeLeaveAppCommonSet().getOverrideSet(), 
					Optional.of(holidayWorkAppSet.getCalcStampMiss()), breakTimeZoneSettingList.getTimeZones(), 
					!actualContentDisplayList.isEmpty() ? Optional.of(actualContentDisplayList.get(0)): Optional.empty());
		}
		hdSelectWorkDispInfoOutput.setActualApplicationTime(actualApplicationTime);
		
		return hdSelectWorkDispInfoOutput;
	}

	@Override
	public CheckBeforeOutput checkBeforeRegister(boolean require, String companyId, AppHdWorkDispInfoOutput appHdWorkDispInfoOutput,
			AppHolidayWork appHolidayWork, boolean isProxy) {
		CheckBeforeOutput checkBeforeOutput = new CheckBeforeOutput();
		
		//2-1.新規画面登録前の処理
		List<ConfirmMsgOutput> confirmMsgOutputs = processBeforeRegister.processBeforeRegister_New(companyId, 
				EmploymentRootAtr.APPLICATION,
				isProxy, 
				appHolidayWork.getApplication(), 
				null,
				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpErrorFlag().orElse(null),
				Collections.emptyList(), 
				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput());
		
		//3.個別エラーチェック
		checkBeforeOutput = commonHolidayWorkAlgorithm.individualErrorCheck(require, companyId, appHdWorkDispInfoOutput, appHolidayWork, 0);
		
		checkBeforeOutput.getConfirmMsgOutputs().addAll(confirmMsgOutputs);
		return checkBeforeOutput;
	}

}
