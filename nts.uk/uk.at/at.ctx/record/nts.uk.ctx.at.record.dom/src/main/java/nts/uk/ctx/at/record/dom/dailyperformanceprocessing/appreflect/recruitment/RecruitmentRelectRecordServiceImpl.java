package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.recruitment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonReflectParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime.HolidayWorkReflectProcess;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.ScheStartEndTimeReflectOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.StartEndTimeOffReflect;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.StartEndTimeOutput;
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
	@Override
	public boolean recruitmentReflect(CommonReflectParameter param, boolean isPre) {
		//予定勤種就時の反映
		//予定開始終了の反映
		this.reflectScheWorkTimeType(param);
		//勤種・就時の反映
		ReflectParameter reflectData = new ReflectParameter(param.getEmployeeId(), param.getBaseDate(), param.getWorkTimeCode(), param.getWorkTypeCode());		
		workUpdate.updateWorkTimeType(reflectData, false);
		//開始終了時刻の反映
		
		
		return false;
	}

	@Override
	public void reflectScheWorkTimeType(CommonReflectParameter param) {
		//予定を反映できるかチェックする
		if(!holidayProcess.checkScheWorkTimeReflect(param.getEmployeeId(), param.getBaseDate(), param.getWorkTimeCode(), param.isScheTimeReflectAtr(), param.getScheAndRecordSameChangeFlg())) {
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
		
	}

}
