package nts.uk.ctx.at.request.app.find.application.holidaywork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHdWorkDispInfoDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHolidayWorkParamPC;
import nts.uk.ctx.at.request.dom.application.common.service.other.PreAppContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayService;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkCalculationResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkContent;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;

/**
 * Refactor5
 * @author huylq
 *
 */
@Stateless
public class AppHolidayWorkFinder {
	
	public static final String PATTERN_DATE = "yyyy/MM/dd";
	
	@Inject
	private HolidayService holidayWorkService;
	
	public AppHdWorkDispInfoDto getStartNew(AppHolidayWorkParamPC param) {
		String companyId = AppContexts.user().companyId();
		
		Optional<List<String>> empList = Optional.empty();
//		if(param.getEmpList() != null && !param.getEmpList().isEmpty()) {
//			empList = Optional.of(param.getEmpList());
//		}
		if(param.getAppDispInfoStartupOutput() != null && param.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput() != null) {
			List<String> empListParam = new ArrayList<String>();
			empListParam.add(param.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid());
			empList = Optional.of(empListParam);
		}
		
		Optional<List<GeneralDate>> dateListOptional = Optional.empty();
		if (param.getDateList() != null && !param.getDateList().isEmpty()) {
			List<GeneralDate> dateList = new ArrayList<GeneralDate>();
			param.getDateList().stream().filter(date -> StringUtils.isNotBlank(date)).map(date -> dateList.add(GeneralDate.fromString(date, PATTERN_DATE)));
			if(!dateList.isEmpty()) {
				dateListOptional = Optional.of(dateList);
			}
		}
		
		//1.休出申請（新規）起動処理
		AppDispInfoStartupOutput appDispInfoStartupOutput = param.getAppDispInfoStartupOutput().toDomain();
		AppHdWorkDispInfoOutput appHdWorkDispInfoOutput = holidayWorkService.startA(companyId, empList, dateListOptional, appDispInfoStartupOutput);
		
		//	計算（従業員）
		List<PreAppContentDisplay> opPreAppContentDisplayLst = 
				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpPreAppContentDisplayLst().orElse(new ArrayList<>());
		Optional<AppOverTime> apOptional  = !opPreAppContentDisplayLst.isEmpty() ? opPreAppContentDisplayLst.get(0).getApOptional() : Optional.empty();
		
		WorkContent workContent = new WorkContent();
		HdWorkDispInfoWithDateOutput hdWorkDispInfoWithDateOutput = appHdWorkDispInfoOutput.getHdWorkDispInfoWithDateOutput();
		workContent.setWorkTypeCode(hdWorkDispInfoWithDateOutput.getInitWorkType().isPresent() ? Optional.of(hdWorkDispInfoWithDateOutput.getInitWorkType().get().v()) : Optional.empty());
		workContent.setWorkTimeCode(hdWorkDispInfoWithDateOutput.getInitWorkTime().isPresent() ? Optional.of(hdWorkDispInfoWithDateOutput.getInitWorkTime().get().v()) : Optional.empty());
		TimeZone timeZoneNo1 = new TimeZone(hdWorkDispInfoWithDateOutput.getWorkHours().getStartTimeOp1().orElse(null), 
				hdWorkDispInfoWithDateOutput.getWorkHours().getEndTimeOp1().orElse(null));
		TimeZone timeZoneNo2 = new TimeZone(hdWorkDispInfoWithDateOutput.getWorkHours().getStartTimeOp2().orElse(null), 
				hdWorkDispInfoWithDateOutput.getWorkHours().getEndTimeOp2().orElse(null));
		List<TimeZone> timeZones = new ArrayList<TimeZone>();
		timeZones.add(timeZoneNo1);
		timeZones.add(timeZoneNo2);
		workContent.setTimeZones(timeZones);
		
		List<BreakTimeSheet> breakTimes = new ArrayList<BreakTimeSheet>();
		if(hdWorkDispInfoWithDateOutput.getBreakTimeZoneSettingList().isPresent()) {
			List<DeductionTime> timeZoneList = hdWorkDispInfoWithDateOutput.getBreakTimeZoneSettingList().get().getTimeZones();
			for(int i = 1; i < timeZoneList.size()+1 && i <= 10; i++) {
				TimeZone timeZone = timeZoneList.get(i-1);
				BreakTimeSheet breakTimeSheet = new BreakTimeSheet(new BreakFrameNo(i), timeZone.getStart(), timeZone.getEnd());
				breakTimes.add(breakTimeSheet);
			}
		}
		workContent.setBreakTimes(breakTimes);
			
		HolidayWorkCalculationResult calculationResult = holidayWorkService.calculate(companyId, appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(), 
				!param.getDateList().isEmpty() ? Optional.of(GeneralDate.fromString(param.getDateList().get(0), PATTERN_DATE)) : Optional.empty(), 
						appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getPrePostAtr(), appHdWorkDispInfoOutput.getHolidayWorkAppSet().getOvertimeLeaveAppCommonSet(), 
				apOptional.isPresent() ? apOptional.get().getApplicationTime() : null, 
						appHdWorkDispInfoOutput.getHdWorkDispInfoWithDateOutput().getActualApplicationTime().orElse(null), workContent);
		appHdWorkDispInfoOutput.setCalculationResult(Optional.ofNullable(calculationResult));
		
		return AppHdWorkDispInfoDto.fromDomain(appHdWorkDispInfoOutput);
	}

}
