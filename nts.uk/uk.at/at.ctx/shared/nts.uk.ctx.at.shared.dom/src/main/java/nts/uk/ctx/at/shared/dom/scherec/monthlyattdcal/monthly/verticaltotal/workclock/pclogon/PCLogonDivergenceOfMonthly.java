package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workclock.pclogon;

import java.io.Serializable;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 月別実績のPCログオン乖離
 * @author shuichi_ishida
 */
@Getter
public class PCLogonDivergenceOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** PCログオン乖離 */
	private AggrPCLogonDivergence logonDivergence;
	/** PCログオフ乖離 */
	private AggrPCLogonDivergence logoffDivergence;
	
	/**
	 * コンストラクタ
	 */
	public PCLogonDivergenceOfMonthly(){
		
		this.logonDivergence = new AggrPCLogonDivergence();
		this.logoffDivergence = new AggrPCLogonDivergence();
	}

	/**
	 * ファクトリー
	 * @param logonDivergence PCログオン乖離
	 * @param logoffDivergence PCログオフ乖離
	 * @return 月別実績のログオン乖離
	 */
	public static PCLogonDivergenceOfMonthly of(
			AggrPCLogonDivergence logonDivergence,
			AggrPCLogonDivergence logoffDivergence){
		
		PCLogonDivergenceOfMonthly domain = new PCLogonDivergenceOfMonthly();
		domain.logonDivergence = logonDivergence;
		domain.logoffDivergence = logoffDivergence;
		return domain;
	}
	
	/**
	 * PCログオン乖離
	 * @param pcLogonInfoOpt 日別実績のPCログオン情報 
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 * @param timeLeavingOfDaily 日別実績の出退勤
	 * @param isWeekday 平日かどうか
	 * @param workType 勤務種類
	 * @param predTimeSetForCalc 計算用所定時間設定
	 */
	public void aggregate(
			Optional<PCLogOnInfoOfDailyAttd> pcLogonInfoOpt,
			AttendanceTimeOfDailyAttendance attendanceTimeOfDaily,
			TimeLeavingOfDailyAttd timeLeavingOfDaily,
			boolean isWeekday,
			WorkType workType,
			PredetermineTimeSetForCalc predTimeSetForCalc){

		// ログオン乖離の集計
		this.logonDivergence.aggregateLogon(pcLogonInfoOpt, attendanceTimeOfDaily, timeLeavingOfDaily,
				isWeekday, workType);
		
		// ログオフ乖離の集計
		this.logoffDivergence.aggregateLogoff(pcLogonInfoOpt, attendanceTimeOfDaily, timeLeavingOfDaily,
				isWeekday, workType, predTimeSetForCalc);
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(PCLogonDivergenceOfMonthly target){
		
		this.logonDivergence.sum(target.logonDivergence);
		this.logoffDivergence.sum(target.logoffDivergence);
	}
}
