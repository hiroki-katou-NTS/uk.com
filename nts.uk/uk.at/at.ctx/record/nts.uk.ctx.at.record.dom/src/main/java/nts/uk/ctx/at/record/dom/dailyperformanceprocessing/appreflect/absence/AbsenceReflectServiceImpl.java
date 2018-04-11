package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.absence;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonReflectParameter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ApplicationReflectOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReasonNotReflectRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReflectedStateRecord;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
@Stateless
public class AbsenceReflectServiceImpl implements AbsenceReflectService{
	
	@Inject
	private ScheWorkUpdateService workTimeUpdate;
	@Inject
	private CommonProcessCheckService commonService;
	@Inject
	private BasicScheduleService basicScheService;
	@Override
	public ApplicationReflectOutput absenceReflect(CommonReflectParameter absencePara, boolean isPre) {
		try {
			//予定勤種の反映
			boolean isRecordWorkType = this.updateRecordWorktype(absencePara, true);
			//予定開始終了時刻の反映
			
			
			
			
			
			//勤種の反映
			workTimeUpdate.updateRecordWorkType(absencePara.getEmployeeId(), absencePara.getBaseDate(), absencePara.getWorkTypeCode(), false);
			return new ApplicationReflectOutput(ReflectedStateRecord.REFLECTED, ReasonNotReflectRecord.ACTUAL_CONFIRMED);
		} catch (Exception e) {
			return new ApplicationReflectOutput(absencePara.getReflectState(), absencePara.getReasoNotReflect());
		}
	}
	@Override
	public boolean updateRecordWorktype(CommonReflectParameter absencePara, boolean isSche) {
		if(!commonService.checkReflectScheWorkTimeType(absencePara, isSche)) {
			return false;
		}
		workTimeUpdate.updateRecordWorkType(absencePara.getEmployeeId(), absencePara.getBaseDate(), absencePara.getWorkTypeCode(), isSche);
		return true;
	}
	@Override
	public void reflectRecordTime(String employeeId, GeneralDate baseDate, String workTypeCode, boolean isReflect) {
		//INPUT．予定勤務種類変更フラグをチェックする
		if(!isReflect) {
			return;
		}
		//予定開始終了時刻をクリアするかチェックする
		//1日半日出勤・1日休日系の判定
		if(basicScheService.checkWorkDay(workTypeCode) == WorkStyle.ONE_DAY_REST) {
			//予定開始時刻の反映
			
		}
	}

	
}
