package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ScheAndRecordSameChangeFlg;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeIsFluidWork;

@Stateless
public class AfterOvertimeReflectProcessImpl implements AfterOvertimeReflectProcess {
	@Inject
	private WorkTimeIsFluidWork workTimeService;
	@Inject
	private ScheWorkUpdateService scheWorkUpdateService;
	@Inject
	private ScheStartEndTimeReflect scheStartEndTimeReflect;
	@Inject
	private StartEndTimeOffReflect startEndTimeOffReflect;
	
	@Override
	public boolean checkScheReflect(OvertimeParameter overtimePara) {
		//ＩNPUT．勤務種類コードとＩNPUT．就業時間帯コードをチェックする
		if((overtimePara.getOvertimePara().getWorkTimeCode().isEmpty()
				|| overtimePara.getOvertimePara().getWorkTypeCode().isEmpty())
				//INPUT．勤種反映フラグをチェックする (勤種反映フラグ(実績))
				|| !overtimePara.isActualReflectFlg()) {
			return true;
		}
		//INPUT．予定と実績を同じに変更する区分をチェックする
		if(overtimePara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.NOTAUTO) {
			return true;
		}
		if(overtimePara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.FLUIDWORK) {
			//INPUT．就業時間帯コードに値があるかチェックする
			if(overtimePara.getOvertimePara().getWorkTimeCode().isEmpty()) {
				return true;
			}
			//流動勤務かどうかの判断処理
			boolean isWorktimeIsFluid = workTimeService.checkWorkTimeIsFluidWork(overtimePara.getOvertimePara().getWorkTimeCode());
			if(!isWorktimeIsFluid) {
				return false;
			}			
		}
		//INPUT．予定と実績を同じに変更する区分が「常に自動変更する」
		//流動勤務かどうかの判断処理 is True
		//予定勤種・就時の反映
		ReflectParameter reflectPara = new ReflectParameter(overtimePara.getEmployeeId(),
				overtimePara.getDateInfo(),
				overtimePara.getOvertimePara().getWorkTimeCode(), 
				overtimePara.getOvertimePara().getWorkTypeCode());
		scheWorkUpdateService.updateWorkTimeType(reflectPara, true);
		
		return false;
	}

	@Override
	public void checkScheWorkStarEndReflect(OvertimeParameter overtimePara, 
			boolean workReflect, WorkTimeTypeOutput workTimeType) {
		//設定による予定開始終了時刻を反映できるかチェックする
		if(!this.checkReflectStartEndForSetting(overtimePara, workReflect)) {
			return;
		}
		//予定開始終了時刻の反映(事前事後共通部分)
		scheStartEndTimeReflect.reflectScheStartEndTime(overtimePara, workTimeType);
	}

	@Override
	public boolean checkReflectStartEndForSetting(OvertimeParameter overtimePara, boolean workReflect) {
		//実績に反映するかチェックする
		if(!overtimePara.isActualReflectFlg()
				|| !overtimePara.isScheTimeOutFlg()
				|| !workReflect) {
			return false;
		}
		boolean isWorktimeIsFluid = false;
		//INPUT．予定と実績を同じに変更する区分をチェックする
		if(overtimePara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.FLUIDWORK) {
			//流動勤務かどうかの判断処理
			isWorktimeIsFluid = workTimeService.checkWorkTimeIsFluidWork(overtimePara.getOvertimePara().getWorkTimeCode());
		}
		if(overtimePara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.ALWAY
				|| isWorktimeIsFluid) {
			return true;
		}
		
		return false;
	}

	@Override
	public void recordStartEndReflect(OvertimeParameter overtimePara, WorkTimeTypeOutput workTimeType) {
		//自動打刻をクリアする
		startEndTimeOffReflect.clearAutomaticEmbossing(overtimePara.getEmployeeId(), overtimePara.getDateInfo(), workTimeType.getWorkTypeCode(), overtimePara.isAutoClearStampFlg(), null);
	}

}
