package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ScheAndRecordSameChangeFlg;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectParameter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeIsFluidWork;

@Stateless
public class AfterScheTimeReflectImpl implements AfterScheTimeReflect{
	@Inject
	private WorkTimeIsFluidWork isFluidWork;
	@Inject
	private ScheTimeReflect scheTimeReflect;
	@Inject
	private ScheWorkUpdateService scheUpdateService;
	@Override
	public void reflectScheTime(GobackReflectParameter para, boolean timeTypeScheReflect) {
		//予定時刻反映できるかチェックする
		if(!this.checkScheTimeCanReflect(para.getGobackData().getWorkTimeCode(), para.getScheAndRecordSameChangeFlg())) {
			return;
		}
		//(開始時刻)反映する時刻を求める
		TimeOfDayReflectOutput startTime = scheTimeReflect.getTimeOfDayReflect(timeTypeScheReflect, para.getGobackData().getStartTime1(), ApplyTimeAtr.START, para.getGobackData().getWorkTimeCode(), para.getScheTimeReflectAtr());
		if(startTime.isReflectFlg()) {
			//予定開始時刻の反映
			TimeReflectParameter timeRef = new TimeReflectParameter(para.getEmployeeId(), para.getDateData(), startTime.getTimeOfDay(), 1, true);
			scheUpdateService.updateStartTimeOfReflect(timeRef);
		}
		//(終了時刻)反映する時刻を求める
		TimeOfDayReflectOutput endTime = scheTimeReflect.getTimeOfDayReflect(timeTypeScheReflect, para.getGobackData().getEndTime1(), ApplyTimeAtr.END, para.getGobackData().getWorkTimeCode(), para.getScheTimeReflectAtr());
		if(endTime.isReflectFlg()) {
			//予定終了時刻の反映
			TimeReflectParameter timeRef = new TimeReflectParameter(para.getEmployeeId(), para.getDateData(), endTime.getTimeOfDay(), 1, false);
			scheUpdateService.updateStartTimeOfReflect(timeRef);
		}
		//(開始時刻2)反映する時刻を求める,
		TimeOfDayReflectOutput startTime2 = scheTimeReflect.getTimeOfDayReflect(timeTypeScheReflect, 
				para.getGobackData().getStartTime2(), ApplyTimeAtr.START2, para.getGobackData().getWorkTimeCode(), para.getScheTimeReflectAtr());
		if(startTime2.isReflectFlg()) {
			//予定開始時刻の反映
			TimeReflectParameter timeRef = new TimeReflectParameter(para.getEmployeeId(), para.getDateData(), startTime2.getTimeOfDay(), 2, true);
			scheUpdateService.updateStartTimeOfReflect(timeRef);
		}
		// (終了時刻2)反映する時刻を求める
		TimeOfDayReflectOutput endTime2 = scheTimeReflect.getTimeOfDayReflect(timeTypeScheReflect, para.getGobackData().getEndTime2(), ApplyTimeAtr.END2, para.getGobackData().getWorkTimeCode(), para.getScheTimeReflectAtr());
		if(endTime2.isReflectFlg()) {
			//予定終了時刻の反映
			TimeReflectParameter timeRef = new TimeReflectParameter(para.getEmployeeId(), para.getDateData(), endTime2.getTimeOfDay(), 2, false);
			scheUpdateService.updateStartTimeOfReflect(timeRef);
		}
	}

	@Override
	public boolean checkScheTimeCanReflect(String workTimeCode, ScheAndRecordSameChangeFlg scheAndRecordSameChange) {
		//INPUT．予定と実績を同じに変更する区分をチェックする
		if(scheAndRecordSameChange == ScheAndRecordSameChangeFlg.NOTAUTO) {
			return false;
		} else if (scheAndRecordSameChange == ScheAndRecordSameChangeFlg.ALWAY
				|| (scheAndRecordSameChange == ScheAndRecordSameChangeFlg.FLUIDWORK
						&& isFluidWork.checkWorkTimeIsFluidWork(workTimeCode))){
			return true;
		} 
		return false;
	}

}
