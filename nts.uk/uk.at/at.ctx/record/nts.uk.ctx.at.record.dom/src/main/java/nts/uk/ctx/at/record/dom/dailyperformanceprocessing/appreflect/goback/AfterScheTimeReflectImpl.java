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
		TimeOfDayReflectOutput startTime = scheTimeReflect.getTimeOfDayReflect(para, timeTypeScheReflect, ApplyTimeAtr.START);
		if(startTime.isReflectFlg()) {
			//予定開始時刻の反映
			TimeReflectParameter timeRef = new TimeReflectParameter(para.getEmployeeId(), para.getDateData(), para.getGobackData().getStartTime1(), 1, true);
			scheUpdateService.updateStartTimeOfReflect(timeRef);
		}
		//(終了時刻)反映する時刻を求める
		TimeOfDayReflectOutput endTime = scheTimeReflect.getTimeOfDayReflect(para, timeTypeScheReflect, ApplyTimeAtr.END);
		if(endTime.isReflectFlg()) {
			//予定終了時刻の反映
			TimeReflectParameter timeRef = new TimeReflectParameter(para.getEmployeeId(), para.getDateData(), para.getGobackData().getEndTime1(), 1, false);
			scheUpdateService.updateStartTimeOfReflect(timeRef);
		}
		//TODO (開始時刻2)反映する時刻を求める, (終了時刻2)反映する時刻を求める
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
