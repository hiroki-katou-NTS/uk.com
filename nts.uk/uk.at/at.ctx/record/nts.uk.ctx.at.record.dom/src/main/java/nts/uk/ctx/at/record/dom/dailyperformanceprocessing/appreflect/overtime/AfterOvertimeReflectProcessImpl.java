package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ScheAndRecordSameChangeFlg;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectPara;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectParameter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeIsFluidWork;

@Stateless
public class AfterOvertimeReflectProcessImpl implements AfterOvertimeReflectProcess {
	@Inject
	private WorkTimeIsFluidWork workTimeService;
	@Inject
	private WorkUpdateService scheWorkUpdateService;
	@Inject
	private ScheStartEndTimeReflect scheStartEndTimeReflect;
	@Inject
	private StartEndTimeOffReflect startEndTimeOffReflect;
	@Inject
	private WorkUpdateService scheWorkUpdate;
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
		startEndTimeOffReflect.clearAutomaticEmbossing(overtimePara.getEmployeeId(), overtimePara.getDateInfo(), workTimeType.getWorkTypeCode(), overtimePara.isAutoClearStampFlg(), 0);
		//出退勤時刻反映できるかチェックする
		if(!overtimePara.isActualReflectFlg()
				&& !overtimePara.isScheTimeOutFlg()) {
			return;
		}
		//開始終了時刻の反映(事後)
		this.reflectStartEndtime(overtimePara, workTimeType);
	}

	@Override
	public void reflectStartEndtime(OvertimeParameter para, WorkTimeTypeOutput timeTypeData) {
		//反映する開始終了時刻を求める
		StartEndTimeRelectCheck startEndTimeCheck = new StartEndTimeRelectCheck(para.getEmployeeId(), para.getDateInfo(), para.getOvertimePara().getStartTime1(), 
				para.getOvertimePara().getEndTime1(), para.getOvertimePara().getStartTime2(), 
				para.getOvertimePara().getEndTime2(), 
				para.getOvertimePara().getWorkTimeCode(), para.getOvertimePara().getWorkTypeCode(), para.getOvertimePara().getOvertimeAtr());
		ScheStartEndTimeReflectOutput findStartEndTimeReflect = scheStartEndTimeReflect.findStartEndTime(startEndTimeCheck, timeTypeData);
		//ジャスト遅刻早退により時刻を編集する
		StartEndTimeOutput startEndTimeData = startEndTimeOffReflect.justLateEarly(timeTypeData.getWorktimeCode(), findStartEndTimeReflect);
		//申請する開始終了時刻に値があるかチェックする
		if(para.getOvertimePara().getStartTime1() != null
				|| para.getOvertimePara().getEndTime1() != null
				|| para.getOvertimePara().getStartTime2() != null
				|| para.getOvertimePara().getEndTime2() != null) {
			//１回勤務反映区分(output)をチェックする
			if(findStartEndTimeReflect.isCountReflect1Atr()) {
				//開始時刻の反映
				//終了時刻の反映
				TimeReflectPara timePara = new TimeReflectPara(para.getEmployeeId(), para.getDateInfo(), startEndTimeData.getStart1(), startEndTimeData.getEnd1(), 1, true, true);
				scheWorkUpdate.updateRecordStartEndTimeReflect(timePara);
				
			}
			//２回勤務反映区分(output)をチェックする
			if(findStartEndTimeReflect.isCountReflect2Atr()) {
				//開始時刻２の反映
				//終了時刻２の反映
				TimeReflectPara timePara = new TimeReflectPara(para.getEmployeeId(), para.getDateInfo(), startEndTimeData.getStart2(), startEndTimeData.getEnd2(), 2, true, true);
				scheWorkUpdate.updateRecordStartEndTimeReflect(timePara);
			}
		} else {
			//１回勤務反映区分(output)をチェックする
			if(findStartEndTimeReflect.isCountReflect1Atr()) {				
				//開始時刻を反映できるかチェックする
				boolean isStart = scheStartEndTimeReflect.checkStartEndTimeReflect(para.getEmployeeId(), para.getDateInfo(), 1, 
						timeTypeData.getWorkTypeCode(), para.getOvertimePara().getOvertimeAtr(), true);
				//終了時刻を反映できるかチェックする
				boolean isEnd = scheStartEndTimeReflect.checkStartEndTimeReflect(para.getEmployeeId(), para.getDateInfo(), 1,
						timeTypeData.getWorkTypeCode(), para.getOvertimePara().getOvertimeAtr(),false);
				TimeReflectPara timePara1 = new TimeReflectPara(para.getEmployeeId(), para.getDateInfo(), startEndTimeData.getStart1(), startEndTimeData.getEnd1(), 1, isStart, isEnd);
				scheWorkUpdate.updateRecordStartEndTimeReflect(timePara1);
				
			}
			//２回勤務反映区分(output)をチェックする
			if(findStartEndTimeReflect.isCountReflect2Atr()) {				
				//開始時刻2を反映できるかチェックする
				boolean isStart = scheStartEndTimeReflect.checkStartEndTimeReflect(para.getEmployeeId(), para.getDateInfo(), 2,
						timeTypeData.getWorkTypeCode(), para.getOvertimePara().getOvertimeAtr(), true);
				//終了時刻を反映できるかチェックする
				boolean isEnd = scheStartEndTimeReflect.checkStartEndTimeReflect(para.getEmployeeId(), para.getDateInfo(), 2, 
						timeTypeData.getWorkTypeCode(), para.getOvertimePara().getOvertimeAtr(), false);
				TimeReflectPara timePara2 = new TimeReflectPara(para.getEmployeeId(), para.getDateInfo(), startEndTimeData.getStart2(), startEndTimeData.getEnd2(), 2, isStart, isEnd);
				scheWorkUpdate.updateRecordStartEndTimeReflect(timePara2);
			}
		}
	}

	@Override
	public void reflectOvertimeFrame(OvertimeParameter para) {
		Map<Integer, Integer> tmp = new HashMap<>();
		for(Map.Entry<Integer,Integer> entry : para.getOvertimePara().getMapOvertimeFrame().entrySet()){
			//INPUT．残業時間のループ中の番をチェックする
			//INPUT．残業時間のループ中の番を、残業時間(反映用)に追加する
			if(entry.getValue() > 0) {
				tmp.put(entry.getKey(), entry.getValue());
			}
		}
		scheWorkUpdate.reflectOffOvertime(para.getEmployeeId(), para.getDateInfo(), tmp, false);
	}

	@Override
	public void reflectTimeShiftNight(String employeeId, GeneralDate baseDate, Integer timeNight) {
		//INPUT．外深夜時間をチェックする
		if(timeNight <= 0) {
			return;
		}
		//所定外深夜時間の反映
		//所定外深夜時間を反映する + 所定外深夜時間の編集状態を更新する
		scheWorkUpdate.updateTimeShiftNight(employeeId, baseDate, timeNight, false);
		//休出時間(深夜)(法内)の反映
		//休出時間(深夜)(法外)の反映
		//休出時間(深夜)(祝日)の反映
		scheWorkUpdate.updateBreakNight(employeeId, baseDate);
		
	}

	
}
