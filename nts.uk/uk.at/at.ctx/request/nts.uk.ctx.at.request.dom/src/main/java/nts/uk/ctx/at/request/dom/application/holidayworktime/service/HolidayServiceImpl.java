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
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.commonalgorithm.ICommonAlgorithmHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.CalculatedFlag;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkCalculationResult;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverStateOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.ICommonAlgorithmOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.InfoNoBaseDate;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.QuotaOuput;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.ReasonDissociationOutput;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkContent;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

@Stateless
public class HolidayServiceImpl implements HolidayService {
	
	@Inject
	private CommonOvertimeHoliday commonOverTimeHoliday;
	
	@Inject
	private ICommonAlgorithmHolidayWork commonHolidayWorkAlgorithm;
	
	@Inject
	private ICommonAlgorithmOverTime commonOverTimeAlgorithm;
	
	@Inject
	private WorkdayoffFrameRepository workdayoffFrameRepository;

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
		appHdWorkDispInfoOutput.setWorkdayoffFrame(workdayoffFrameList.get(0));
		
		//	指定社員の申請残業枠を取得する 
		InfoNoBaseDate infoNoBaseDate= commonOverTimeAlgorithm.getInfoNoBaseDate(companyId,
				employeeId,
				OvertimeAppAtr.EARLY_NORMAL_OVERTIME);
		QuotaOuput quotaOutput = commonOverTimeAlgorithm.getOvertimeQuotaSetUse(companyId, employeeId, applicationDate.orElse(null), 
				OvertimeAppAtr.EARLY_NORMAL_OVERTIME, infoNoBaseDate.getOverTimeAppSet());
		appHdWorkDispInfoOutput.setDispFlexTime(NotUseAtr.valueOf(quotaOutput.getFlexTimeClf() ? 1 : 0));
		appHdWorkDispInfoOutput.setOvertimeFrame(!quotaOutput.getOverTimeQuotaList().isEmpty() ? quotaOutput.getOverTimeQuotaList().get(0) : null);
		
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
		OverStateOutput overStateOutput = (new OvertimeLeaveAppCommonSet()).checkPreApplication(EnumAdaptor.valueOf(prePostAtr.value, PrePostAtr.class), 
				Optional.of(preApplicationTime), applicationTimes.isEmpty() ? Optional.empty() : Optional.of(applicationTimes.get(0)), Optional.of(actualApplicationTime));
		
		
		return new HolidayWorkCalculationResult(overStateOutput, !applicationTimes.isEmpty() ? applicationTimes.get(0) : null, CalculatedFlag.CALCULATED);
	}

}
