package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;

@Stateless
public class PreGoBackReflectServiceImp implements PreGoBackReflectService {
	@Inject
	private WorkTimeTypeScheReflect timeTypeSche;
	@Inject
	private ScheTimeReflect scheTimeReflect;
	@Inject
	private WorkUpdateService workTimeUpdate;
	@Inject
	private AfterWorkTimeTypeReflect afterWorkTimeType;
	@Inject
	private AfterScheTimeReflect afterScheTime;
	@Override
	public boolean gobackReflect(GobackReflectParameter para) {
		try {
			//予定勤種・就時の反映
			boolean chkTimeTypeSche = timeTypeSche.reflectScheWorkTimeType(para);
			//予定時刻の反映
			scheTimeReflect.reflectScheTime(para, chkTimeTypeSche);
			//勤種・就時の反映
			timeTypeSche.reflectRecordWorktimetype(para);
			//時刻の反映
			scheTimeReflect.reflectTime(para, this.workTypetimeReflect(para));
			return true;
		} catch(Exception ex) {
			return false;
		}
	}

	@Override
	public boolean afterGobackReflect(GobackReflectParameter para) {
		try {
			//予定勤種・就時の反映
			Boolean chkTimeTypeChe = afterWorkTimeType.workTimeAndTypeScheReflect(para);
			//予定時刻の反映
			afterScheTime.reflectScheTime(para, chkTimeTypeChe);
			//勤種・就時の反映
			timeTypeSche.reflectRecordWorktimetype(para);
			//時刻の反映
			scheTimeReflect.reflectTime(para, this.workTypetimeReflect(para));
			
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	/**
	 * 勤種・就時の反映
	 * @param para
	 * @return
	 */
	private boolean workTypetimeReflect(GobackReflectParameter para) {
		boolean workTypeTimeReflect;
		//実績勤務種類による勤種・就時を反映できるかチェックする
		if(timeTypeSche.checkReflectWorkTimeType(para)) {
			ReflectParameter reflectData = new ReflectParameter(para.getEmployeeId(), 
					para.getDateData(), para.getGobackData().getWorkTimeCode(), 
					para.getGobackData().getWorkTypeCode()); 
			workTimeUpdate.updateWorkTimeType(reflectData, false);
			workTypeTimeReflect = true;
		} else {
			workTypeTimeReflect = false;			
		}
		return workTypeTimeReflect;
	}

}
