package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.recruitment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonReflectParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.absenceleave.AbsenceLeaveReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime.HolidayWorkReflectProcess;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime.PreHolidayWorktimeReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.ScheStartEndTimeReflectOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.StartEndTimeOffReflect;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.StartEndTimeOutput;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectPara;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.shared.dom.worktype.service.AttendanceOfficeAtr;
import nts.uk.ctx.at.shared.dom.worktype.service.WorkTypeIsClosedService;

@Stateless
public class RecruitmentRelectRecordServiceImpl implements RecruitmentRelectRecordService {
	@Inject
	private HolidayWorkReflectProcess holidayProcess;
	@Inject
	private WorkUpdateService workUpdate;
	@Inject
	private StartEndTimeOffReflect startEndTimeOffReflect;
	@Inject
	private PreHolidayWorktimeReflectService holidayWorktimeService;
	@Inject
	private WorkTypeIsClosedService workTypeService;
	@Override
	public boolean recruitmentReflect(CommonReflectParameter param, boolean isPre) {
		try {
			
			IntegrationOfDaily daily = holidayWorktimeService.createIntegrationOfDailyStart(param.getEmployeeId(), param.getBaseDate(), param.getWorkTimeCode(), param.getWorkTypeCode(), param.getStartTime(), param.getEndTime());
			
			//予定勤種就時の反映
			//予定開始終了の反映
			this.reflectScheWorkTimeType(param, isPre);
			//勤種・就時の反映
			ReflectParameter reflectData = new ReflectParameter(param.getEmployeeId(), param.getBaseDate(), param.getWorkTimeCode(), param.getWorkTypeCode());		
			workUpdate.updateWorkTimeType(reflectData, false);
			//開始終了時刻の反映
			daily = this.reflectRecordStartEndTime(param, daily);
			//休出時間振替時間をクリアする
			this.clearRecruitmenFrameTime(param.getEmployeeId(), param.getBaseDate(), daily);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void reflectScheWorkTimeType(CommonReflectParameter param, boolean isPre) {
		//予定を反映できるかチェックする
		if(!holidayProcess.checkScheWorkTimeReflect(param.getEmployeeId(), param.getBaseDate(), 
				param.getWorkTimeCode(), param.isScheTimeReflectAtr(), isPre, param.getScheAndRecordSameChangeFlg())) {
			return;
		}
		//予定勤種・就時の反映
		ReflectParameter reflectData = new ReflectParameter(param.getEmployeeId(), param.getBaseDate(), param.getWorkTimeCode(), param.getWorkTypeCode());		
		workUpdate.updateWorkTimeType(reflectData, true);
		//予定開始終了の反映
		//予定開始時刻の反映
		//予定終了時刻の反映
		TimeReflectPara timeRelect = new TimeReflectPara(param.getEmployeeId(), param.getBaseDate(), param.getStartTime(), param.getEndTime(), 1, true, true);		
		workUpdate.updateScheStartEndTime(timeRelect);
	}

	@Override
	public IntegrationOfDaily reflectRecordStartEndTime(CommonReflectParameter param, IntegrationOfDaily daily) {
		ScheStartEndTimeReflectOutput startEndTimeData = new ScheStartEndTimeReflectOutput(param.getStartTime(), param.getEndTime(), true, null, null, false);
		StartEndTimeOutput justLateEarly = startEndTimeOffReflect.justLateEarly(param.getWorkTimeCode(), startEndTimeData);
		//開始時刻を反映できるかチェックする
		if(this.checkReflectRecordStartEndTime(param.getWorkTypeCode(), 1, true, daily.getAttendanceLeave())) {
			//開始時刻の反映
			TimeReflectPara startTimeData = new TimeReflectPara(param.getEmployeeId(), param.getBaseDate(), justLateEarly.getStart1(), 0, 1, true, false);
			workUpdate.updateRecordStartEndTimeReflect(startTimeData);
		}
		if(this.checkReflectRecordStartEndTime(param.getWorkTypeCode(), 1, false, daily.getAttendanceLeave())) {
			//終了時刻の反映
			TimeReflectPara startTimeData = new TimeReflectPara(param.getEmployeeId(), param.getBaseDate(), 0, justLateEarly.getEnd1(), 1, false, true);
			workUpdate.updateRecordStartEndTimeReflect(startTimeData);
		}
		return daily;
	}

	@Override
	public void clearRecruitmenFrameTime(String employeeId, GeneralDate baseDate, IntegrationOfDaily daily) {
		//休出時間の反映
		Map<Integer, Integer> worktimeFrame = new HashMap<>();
		worktimeFrame.put(1, 0);
		worktimeFrame.put(2, 0);
		worktimeFrame.put(3, 0);
		worktimeFrame.put(4, 0);
		worktimeFrame.put(5, 0);
		worktimeFrame.put(6, 0);
		worktimeFrame.put(7, 0);
		worktimeFrame.put(8, 0);
		worktimeFrame.put(9, 0);
		worktimeFrame.put(10, 0);
		
		workUpdate.updateWorkTimeFrame(employeeId, baseDate, worktimeFrame, false, daily);
		//振替時間(休出)の反映
		Map<Integer, Integer> tranferTimeFrame = new HashMap<>();
		tranferTimeFrame.put(1, 0);
		tranferTimeFrame.put(2, 0);
		tranferTimeFrame.put(3, 0);
		tranferTimeFrame.put(4, 0);
		tranferTimeFrame.put(5, 0);
		tranferTimeFrame.put(6, 0);
		tranferTimeFrame.put(7, 0);
		tranferTimeFrame.put(8, 0);
		tranferTimeFrame.put(9, 0);
		tranferTimeFrame.put(10, 0);
		workUpdate.updateTransferTimeFrame(employeeId, baseDate, tranferTimeFrame);
	}

	@Override
	public boolean checkReflectRecordStartEndTime(String workTypeCode, Integer frameNo,
			boolean isAttendence, Optional<TimeLeavingOfDailyPerformance> optTimeLeaving) {
		//出勤時刻を取得する
		//打刻元情報を取得する		
		if(!optTimeLeaving.isPresent()) {
			return true;
		}
		TimeLeavingOfDailyPerformance timeLeaving = optTimeLeaving.get();
		List<TimeLeavingWork> leavingStamp = timeLeaving.getTimeLeavingWorks();
		if(leavingStamp.isEmpty()) {
			return true;
		}
		List<TimeLeavingWork> lstLeavingStamp1 = leavingStamp.stream()
				.filter(x -> x.getWorkNo().v() == frameNo).collect(Collectors.toList());
		if(lstLeavingStamp1.isEmpty()) {
			return true;
		}
		TimeLeavingWork leavingStamp1 = lstLeavingStamp1.get(0);
		Optional<TimeActualStamp> optTimeActual = null;
		if(isAttendence) {
			//出勤
			optTimeActual = leavingStamp1.getAttendanceStamp();
			//打刻自動セット区分を取得する
			if(!workTypeService.checkStampAutoSet(workTypeCode, AttendanceOfficeAtr.ATTENDANCE)) {
				return false;
			}
		} else {
			//退勤
			optTimeActual = leavingStamp1.getLeaveStamp();
			//打刻自動セット区分を取得する
			if(!workTypeService.checkStampAutoSet(workTypeCode, AttendanceOfficeAtr.OFFICEWORK)) {
				return false;
			}
		}
		if(!optTimeActual.isPresent()) {
			return true;
		}	
		TimeActualStamp attendanceStamp = optTimeActual.get();
		Optional<WorkStamp> optActualStamp = attendanceStamp.getActualStamp();
		if(!optActualStamp.isPresent()) {
			return true;
		}
		WorkStamp actualStamp = optActualStamp.get();
		//取得した出勤時刻に値がない　OR
		//取得した打刻元情報が「打刻自動セット(個人情報)、直行直帰申請」
		if(actualStamp.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION
				|| actualStamp.getStampSourceInfo() == StampSourceInfo.STAMP_AUTO_SET_PERSONAL_INFO) {
			return true;
		}
		return false;
	}

}
