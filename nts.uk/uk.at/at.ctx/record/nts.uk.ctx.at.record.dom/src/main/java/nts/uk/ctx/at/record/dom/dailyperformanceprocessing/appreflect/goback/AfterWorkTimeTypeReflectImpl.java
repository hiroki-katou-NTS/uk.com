package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ScheAndRecordSameChangeFlg;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.AppReflectRecordWork;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeIsFluidWork;

@Stateless
public class AfterWorkTimeTypeReflectImpl implements AfterWorkTimeTypeReflect{
	@Inject
	private WorkTimeTypeScheReflect tyScheReflect;
	@Inject
	private WorkTimeIsFluidWork isFluidWork;
	@Inject
	private WorkUpdateService workUpdate;
	
	@Override
	public AppReflectRecordWork workTimeAndTypeScheReflect(GobackReflectParameter para, WorkInfoOfDailyPerformance dailyInfor) {
		if(!this.checkReflectWorkTimeType(para)) {
			return new AppReflectRecordWork(false, dailyInfor);
		}
		//予定勤種・就時の反映
		ReflectParameter reflectInfo = new ReflectParameter(para.getEmployeeId(), para.getDateData(), 
				para.getGobackData().getWorkTimeCode(), 
				para.getGobackData().getWorkTypeCode(), false); 
		workUpdate.updateWorkTimeType(reflectInfo, true, dailyInfor);
		
		return new AppReflectRecordWork(true, dailyInfor);
	}

	@Override
	public boolean checkReflectWorkTimeType(GobackReflectParameter para) {
		//INPUT．勤務を変更するをチェックする
		if(para.getGobackData().getChangeAppGobackAtr() == ChangeAppGobackAtr.NOTCHANGE) {
			return false;
		}
		//INPUT．予定と実績を同じに変更する区分をチェックする
		if(para.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.DO_NOT_CHANGE_AUTO) {
			return false;
		} else if (para.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.ALWAYS_CHANGE_AUTO
				|| (para.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.AUTO_CHANGE_ONLY_WORK
						&& isFluidWork.checkWorkTimeIsFluidWork(para.getGobackData().getWorkTimeCode()))){
			return tyScheReflect.checkScheAndRecordSamseChange(para.getEmployeeId(), para.getDateData(), para.isOutResReflectAtr());
		} 
		return false;
	}

}
