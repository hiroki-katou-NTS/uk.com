package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.absence;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ApplicationReflectOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReasonNotReflectRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReflectedStateRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ScheAndRecordSameChangeFlg;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeIsFluidWork;
@Stateless
public class AbsenceReflectServiceImpl implements AbsenceReflectService{
	@Inject
	private WorkInformationRepository workInforRepos;
	@Inject
	private WorkTimeIsFluidWork workTimeisFluidWork;
	@Inject
	private ScheWorkUpdateService workTimeUpdate;
	@Override
	public ApplicationReflectOutput absenceReflect(AbsenceReflectParameter absencePara, boolean isPre) {
		try {
			//予定勤種の反映
			this.reflectScheWorkTimeWorkType(absencePara, isPre);
			//勤種の反映
			workTimeUpdate.updateRecordWorkType(absencePara.getEmployeeId(), absencePara.getBaseDate(), absencePara.getWorkTypeCode(), false);
			return new ApplicationReflectOutput(ReflectedStateRecord.REFLECTED, ReasonNotReflectRecord.ACTUAL_CONFIRMED);
		} catch (Exception e) {
			return new ApplicationReflectOutput(absencePara.getReflectState(), absencePara.getReasoNotReflect());
		}
	}

	@Override
	public void reflectScheWorkTimeWorkType(AbsenceReflectParameter absencePara, boolean isPre) {
		//予定勤種を反映できるかチェックする
		if(!this.checkReflectScheWorkTimeType(absencePara, isPre)) {
			return;
		}
		//予定勤種の反映
		workTimeUpdate.updateRecordWorkType(absencePara.getEmployeeId(), absencePara.getBaseDate(), absencePara.getWorkTypeCode(), true);
	}

	@Override
	public boolean checkReflectScheWorkTimeType(AbsenceReflectParameter absencePara, boolean isPre) {
		//INPUT．予定反映区分をチェックする
		if((absencePara.isScheTimeReflectAtr() == true && isPre)
				|| absencePara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.ALWAY) {
			return true;
		}
		//INPUT．予定と実績を同じに変更する区分をチェックする
		if(absencePara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.FLUIDWORK) {
			//ドメインモデル「日別実績の勤務情報」を取得する
			Optional<WorkInfoOfDailyPerformance> optWorkInfor = workInforRepos.find(absencePara.getEmployeeId(), absencePara.getBaseDate());
			if(!optWorkInfor.isPresent()) {
				return false;
			}
			WorkInfoOfDailyPerformance workInforData = optWorkInfor.get();
			WorkInformation recordWorkInformation = workInforData.getRecordWorkInformation();
			//流動勤務かどうかの判断処理
			return workTimeisFluidWork.checkWorkTimeIsFluidWork(recordWorkInformation.getWorkTimeCode().v());
		}
		
		return false;
	}
}
