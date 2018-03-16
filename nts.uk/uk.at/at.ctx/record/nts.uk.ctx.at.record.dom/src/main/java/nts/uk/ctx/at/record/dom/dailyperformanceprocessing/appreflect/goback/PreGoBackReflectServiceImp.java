package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ApplicationReflectOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReasonNotReflectRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReflectedStateRecord;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;

@Stateless
public class PreGoBackReflectServiceImp implements PreGoBackReflectService {
	@Inject
	private WorkTimeTypeScheReflect timeTypeSche;
	@Inject
	private ScheTimeReflect scheTimeReflect;
	@Inject
	private ScheWorkUpdateService workTimeUpdate;
	@Inject
	private CommonProcessCheckService commonService;

	@Override
	public ApplicationReflectOutput gobackReflect(GobackReflectParameter para) {
		try {
			//予定勤種・就時の反映
			boolean chkTimeTypeSche = timeTypeSche.workTimeAndTypeScheReflect(para);
			//予定時刻の反映
			scheTimeReflect.reflectScheTime(para, chkTimeTypeSche);
			//勤種・就時の反映
			boolean workTypeTimeReflect;
			//実績勤務種類による勤種・就時を反映できるかチェックする
			if(timeTypeSche.checkReflectWorkTimeType(para)) {
				ReflectParameter reflectData = new ReflectParameter(para.getEmployeeId(), 
						para.getDateData(), para.getGobackData().getWorkTimeCode(), 
						para.getGobackData().getWorkTypeCode()); 
				workTimeUpdate.updateWorkTimeType(reflectData, commonService.lstScheWorkItem(), false);
				workTypeTimeReflect = true;
			} else {
				workTypeTimeReflect = false;			
			}
			//時刻の反映
			scheTimeReflect.reflectTime(para, workTypeTimeReflect);
			return new ApplicationReflectOutput(ReflectedStateRecord.REFLECTED, ReasonNotReflectRecord.WORK_FIXED);
		} catch(Exception ex) {
			return new ApplicationReflectOutput(para.getGobackData().getReflectState(), para.getGobackData().getReasoNotReflect());
		}
	}

	@Override
	public ApplicationReflectOutput afterGobackReflect(GobackReflectParameter para) {
		// TODO Auto-generated method stub
		return null;
	}

}
