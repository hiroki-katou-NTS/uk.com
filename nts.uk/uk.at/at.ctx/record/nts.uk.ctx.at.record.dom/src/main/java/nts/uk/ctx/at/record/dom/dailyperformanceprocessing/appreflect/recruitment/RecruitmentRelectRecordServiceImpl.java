package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.recruitment;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonReflectParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.absenceleave.AbsenceLeaveReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime.HolidayWorkReflectProcess;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.ScheStartEndTimeReflectOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.StartEndTimeOffReflect;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.StartEndTimeOutput;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectPara;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;

@Stateless
public class RecruitmentRelectRecordServiceImpl implements RecruitmentRelectRecordService {
	@Inject
	private HolidayWorkReflectProcess holidayProcess;
	@Inject
	private WorkUpdateService workUpdate;
	@Inject
	private StartEndTimeOffReflect startEndTimeOffReflect;
	@Inject
	private AbsenceLeaveReflectService absenceLeaveService;
	@Inject
	private PreOvertimeReflectService overTimeService;
	@Override
	public boolean recruitmentReflect(CommonReflectParameter param, boolean isPre) {
		try {
			//予定勤種就時の反映
			//予定開始終了の反映
			this.reflectScheWorkTimeType(param, isPre);
			//勤種・就時の反映
			ReflectParameter reflectData = new ReflectParameter(param.getEmployeeId(), param.getBaseDate(), param.getWorkTimeCode(), param.getWorkTypeCode());		
			workUpdate.updateWorkTimeType(reflectData, false);
			//開始終了時刻の反映
			this.reflectRecordStartEndTime(param);
			//休出時間振替時間をクリアする
			this.clearRecruitmenFrameTime(param.getEmployeeId(), param.getBaseDate());
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
	public void reflectRecordStartEndTime(CommonReflectParameter param) {
		ScheStartEndTimeReflectOutput startEndTimeData = new ScheStartEndTimeReflectOutput(param.getStartTime(), param.getEndTime(), true, null, null, false);
		StartEndTimeOutput justLateEarly = startEndTimeOffReflect.justLateEarly(param.getWorkTimeCode(), startEndTimeData);
		//開始時刻を反映できるかチェックする
		if(absenceLeaveService.checkReflectRecordStartEndTime(param.getEmployeeId(), param.getBaseDate(), 1, true)) {
			//開始時刻の反映
			TimeReflectPara startTimeData = new TimeReflectPara(param.getEmployeeId(), param.getBaseDate(), justLateEarly.getStart1(), 0, 1, true, false);
			workUpdate.updateRecordStartEndTimeReflect(startTimeData);
		}
		if(absenceLeaveService.checkReflectRecordStartEndTime(param.getEmployeeId(), param.getBaseDate(), 1, false)) {
			//終了時刻の反映
			TimeReflectPara startTimeData = new TimeReflectPara(param.getEmployeeId(), param.getBaseDate(), 0, justLateEarly.getEnd1(), 1, false, true);
			workUpdate.updateRecordStartEndTimeReflect(startTimeData);
		}
	}

	@Override
	public void clearRecruitmenFrameTime(String employeeId, GeneralDate baseDate) {
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
		IntegrationOfDaily daily =overTimeService.calculateForAppReflect(employeeId, baseDate);
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

}
