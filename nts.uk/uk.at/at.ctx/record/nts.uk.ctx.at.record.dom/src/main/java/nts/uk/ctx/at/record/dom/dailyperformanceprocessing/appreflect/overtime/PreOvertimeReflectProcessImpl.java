package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ScheAndRecordSameChangeFlg;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeIsFluidWork;
@Stateless
public class PreOvertimeReflectProcessImpl implements PreOvertimeReflectProcess{
	@Inject
	private WorkInformationRepository workRepository;
	@Inject
	private WorkTimeIsFluidWork workTimeService;
	@Inject
	private ScheWorkUpdateService workUpdate;
	@Inject
	private CommonProcessCheckService commonService;
	@Override
	public void workTimeWorkTimeUpdate(PreOvertimeParameter para) {
		//INPUT．勤種反映フラグ(予定)をチェックする
		if(!para.isScheReflectFlg()) {
			return;
		}
		ReflectParameter reflectInfo = new ReflectParameter(para.getEmployeeId(), 
				para.getDateInfo(), 
				para.getOvertimePara().getWorkTimeCode(), 
				para.getOvertimePara().getWorkTypeCode()); 
		workUpdate.updateWorkTimeType(reflectInfo, commonService.lstScheWorkTimeType());		
	}
	
	@Override
	public boolean changeFlg(PreOvertimeParameter para) {
		//INPUT．勤種反映フラグ(実績)をチェックする
		if(!para.isActualReflectFlg()) {
			return false;
		}
		//ドメインモデル「日別実績の勤務情報」を取得する
		Optional<WorkInfoOfDailyPerformance> optDailyPerfor = workRepository.find(para.getEmployeeId(), para.getDateInfo());
		if(!optDailyPerfor.isPresent()) {
			return false;
		}
		//勤種・就時の反映
		ReflectParameter reflectInfo = new ReflectParameter(para.getEmployeeId(), 
				para.getDateInfo(), 
				para.getOvertimePara().getWorkTimeCode(), 
				para.getOvertimePara().getWorkTypeCode()); 
		workUpdate.updateWorkTimeType(reflectInfo, commonService.lstItemRecord());
		WorkInfoOfDailyPerformance dailyPerfor = optDailyPerfor.get();
		//反映前後勤就に変更があるかチェックする
		//取得した勤務種類コード ≠ INPUT．勤務種類コード OR
		//取得した就業時間帯コード ≠ INPUT．就業時間帯コード
		if(dailyPerfor.getRecordWorkInformation().getWorkTimeCode().v().equals(para.getOvertimePara().getWorkTimeCode())
				||dailyPerfor.getRecordWorkInformation().getWorkTypeCode().v().equals(para.getOvertimePara().getWorkTypeCode())){
			 return true;
		}
		
		return false;
		
	}
	@Override
	public boolean startAndEndTimeReflectSche(PreOvertimeParameter para, boolean changeFlg,
			WorkInfoOfDailyPerformance dailyData) {
		//設定による予定開始終了時刻を反映できるかチェックする
		if(!this.timeReflectCheck(para, changeFlg, dailyData)) {
			return false;
		}

		//予定開始終了時刻の反映(事前事後共通部分)
		//@@
		
		
		
		
		
		
		return false;
	}
	@Override
	public boolean timeReflectCheck(PreOvertimeParameter para, boolean changeFlg,
			WorkInfoOfDailyPerformance dailyData) {
		//INPUT．勤種反映フラグ(予定)をチェックする
		if(para.isScheReflectFlg()) {
			return true;
		}
		//実績に反映するかチェックする
		//INPUT．勤種反映フラグ(実績) == する AND
		//INPUT．予定出退勤反映フラグ == する AND
		//反映前後勤就の変更フラグ == true
		if(!para.isActualReflectFlg()
				|| !para.isScheTimeOutFlg()
				|| !changeFlg) {
			return false;
		}
		//INPUT．予定と実績を同じに変更する区分をチェックする
		if(para.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.ALWAY) {
			return true;
		}
		//流動勤務かどうかの判断処理
		return workTimeService.checkWorkTimeIsFluidWork(para.getOvertimePara().getWorkTimeCode());
		
	}

}
