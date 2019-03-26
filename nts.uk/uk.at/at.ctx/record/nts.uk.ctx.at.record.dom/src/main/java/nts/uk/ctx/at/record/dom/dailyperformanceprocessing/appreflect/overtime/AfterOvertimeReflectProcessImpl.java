package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ScheAndRecordSameChangeFlg;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectPara;
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
	public WorkInfoOfDailyPerformance checkScheReflect(OvertimeParameter overtimePara, WorkInfoOfDailyPerformance dailyInfor) {
		//ＩNPUT．勤務種類コードとＩNPUT．就業時間帯コードをチェックする
		if((overtimePara.getOvertimePara().getWorkTimeCode().isEmpty()
				|| overtimePara.getOvertimePara().getWorkTypeCode().isEmpty())
				//INPUT．勤種反映フラグをチェックする (勤種反映フラグ(実績))
				|| !overtimePara.isActualReflectFlg()) {
			return dailyInfor;
		}
		//INPUT．予定と実績を同じに変更する区分をチェックする
		if(overtimePara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.DO_NOT_CHANGE_AUTO) {
			return dailyInfor;
		}
		if(overtimePara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.AUTO_CHANGE_ONLY_WORK) {
			//INPUT．就業時間帯コードに値があるかチェックする
			if(overtimePara.getOvertimePara().getWorkTimeCode().isEmpty()) {
				return dailyInfor;
			}
			//流動勤務かどうかの判断処理
			boolean isWorktimeIsFluid = workTimeService.checkWorkTimeIsFluidWork(overtimePara.getOvertimePara().getWorkTimeCode());
			if(!isWorktimeIsFluid) {
				return dailyInfor;
			}			
		}
		//INPUT．予定と実績を同じに変更する区分が「常に自動変更する」
		//流動勤務かどうかの判断処理 is True
		//予定勤種・就時の反映
		ReflectParameter reflectPara = new ReflectParameter(overtimePara.getEmployeeId(),
				overtimePara.getDateInfo(),
				overtimePara.getOvertimePara().getWorkTimeCode(), 
				overtimePara.getOvertimePara().getWorkTypeCode(), false);
		return scheWorkUpdateService.updateWorkTimeType(reflectPara, true, dailyInfor);
	}

	@Override
	public WorkInfoOfDailyPerformance checkScheWorkStarEndReflect(OvertimeParameter overtimePara, 
			boolean workReflect, WorkTimeTypeOutput workTimeType, WorkInfoOfDailyPerformance dailyInfor) {
		//設定による予定開始終了時刻を反映できるかチェックする
		if(!this.checkReflectStartEndForSetting(overtimePara, workReflect)) {
			return dailyInfor;
		}
		//予定開始終了時刻の反映(事前事後共通部分) TODO
		//return scheStartEndTimeReflect.reflectScheStartEndTime(overtimePara, workTimeType, dailyInfor);
		return null;
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
		if(overtimePara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.AUTO_CHANGE_ONLY_WORK) {
			//流動勤務かどうかの判断処理
			isWorktimeIsFluid = workTimeService.checkWorkTimeIsFluidWork(overtimePara.getOvertimePara().getWorkTimeCode());
		}
		if(overtimePara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.ALWAYS_CHANGE_AUTO
				|| isWorktimeIsFluid) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public AttendanceTimeOfDailyPerformance reflectOvertimeFrame(OvertimeParameter para, AttendanceTimeOfDailyPerformance attendanceTimeData) {
		Map<Integer, Integer> tmp = new HashMap<>();
		for(Map.Entry<Integer,Integer> entry : para.getOvertimePara().getMapOvertimeFrame().entrySet()){
			//INPUT．残業時間のループ中の番をチェックする
			//INPUT．残業時間のループ中の番を、残業時間(反映用)に追加する
			if(entry.getValue() > 0) {
				tmp.put(entry.getKey(), entry.getValue());
			}
		}
		// TODO
		//return scheWorkUpdate.reflectOffOvertime(para.getEmployeeId(), para.getDateInfo(), tmp, false, attendanceTimeData);
		return null;
	}

	@Override
	public AttendanceTimeOfDailyPerformance reflectTimeShiftNight(String employeeId, GeneralDate baseDate, Integer timeNight, 
			AttendanceTimeOfDailyPerformance attendanceTimeData) {
		//INPUT．外深夜時間をチェックする
		if(timeNight <= 0) {
			return attendanceTimeData;
		}
		//所定外深夜時間の反映
		//所定外深夜時間を反映する + 所定外深夜時間の編集状態を更新する
		//attendanceTimeData = scheWorkUpdate.updateTimeShiftNight(employeeId, baseDate, timeNight, false, attendanceTimeData);
		//休出時間(深夜)(法内)の反映
		//休出時間(深夜)(法外)の反映
		//休出時間(深夜)(祝日)の反映
		return scheWorkUpdate.updateBreakNight(employeeId, baseDate, attendanceTimeData);
		
	}

	
}
