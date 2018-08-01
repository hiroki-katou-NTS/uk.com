package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.AppReflectRecordWork;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
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
	@Inject
	private WorkInformationRepository workRepository;
	@Override
	public boolean gobackReflect(GobackReflectParameter para) {
		try {
			WorkInfoOfDailyPerformance dailyInfor = workRepository.find(para.getEmployeeId(), para.getDateData()).get();
			//予定勤種・就時の反映
			AppReflectRecordWork chkTimeTypeSche = timeTypeSche.reflectScheWorkTimeType(para, dailyInfor);
			//予定時刻の反映
			dailyInfor = scheTimeReflect.reflectScheTime(para, chkTimeTypeSche.isChkReflect(), dailyInfor);
			//勤種・就時の反映
			dailyInfor = timeTypeSche.reflectRecordWorktimetype(para, dailyInfor);
			//時刻の反映
			AppReflectRecordWork reflectWorkTypeTime = this.workTypetimeReflect(para, dailyInfor);
			workRepository.updateByKeyFlush(reflectWorkTypeTime.getDailyInfo());
			
			scheTimeReflect.reflectTime(para, reflectWorkTypeTime.isChkReflect());			
			return true;
		} catch(Exception ex) {
			return false;
		}
	}

	@Override
	public boolean afterGobackReflect(GobackReflectParameter para) {
		try {
			WorkInfoOfDailyPerformance dailyInfor = workRepository.find(para.getEmployeeId(), para.getDateData()).get();
			//予定勤種・就時の反映
			AppReflectRecordWork chkTimeTypeChe = afterWorkTimeType.workTimeAndTypeScheReflect(para, dailyInfor);
			//予定時刻の反映
			dailyInfor = afterScheTime.reflectScheTime(para, chkTimeTypeChe.isChkReflect(), chkTimeTypeChe.getDailyInfo());
			//勤種・就時の反映
			dailyInfor = timeTypeSche.reflectRecordWorktimetype(para, dailyInfor);
			
			AppReflectRecordWork reflectWorkTypeTime = this.workTypetimeReflect(para, dailyInfor);
			workRepository.updateByKeyFlush(reflectWorkTypeTime.getDailyInfo());
			//時刻の反映
			scheTimeReflect.reflectTime(para, reflectWorkTypeTime.isChkReflect());
			
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
	private AppReflectRecordWork workTypetimeReflect(GobackReflectParameter para, WorkInfoOfDailyPerformance dailyInfor) {
		boolean workTypeTimeReflect;
		//実績勤務種類による勤種・就時を反映できるかチェックする
		if(timeTypeSche.checkReflectWorkTimeType(para)) {
			ReflectParameter reflectData = new ReflectParameter(para.getEmployeeId(), 
					para.getDateData(), para.getGobackData().getWorkTimeCode(), 
					para.getGobackData().getWorkTypeCode()); 
			dailyInfor = workTimeUpdate.updateWorkTimeType(reflectData, false, dailyInfor);
			workTypeTimeReflect = true;
		} else {
			workTypeTimeReflect = false;			
		}
		return new AppReflectRecordWork(workTypeTimeReflect, dailyInfor);
	}

}
