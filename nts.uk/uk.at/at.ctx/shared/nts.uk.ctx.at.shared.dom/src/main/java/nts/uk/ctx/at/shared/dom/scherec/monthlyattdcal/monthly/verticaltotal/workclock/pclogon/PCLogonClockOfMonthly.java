package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workclock.pclogon;

import java.io.Serializable;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 月別実績のPCログオン時刻
 * @author shuichi_ishida
 */
@Getter
public class PCLogonClockOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** PCログオン時刻 */
	private AggrPCLogonClock logonClock;
	/** PCログオフ時刻 */
	private AggrPCLogonClock logoffClock;
	
	/**
	 * コンストラクタ
	 */
	public PCLogonClockOfMonthly(){
		
		this.logonClock = new AggrPCLogonClock();
		this.logoffClock = new AggrPCLogonClock();
	}

	/**
	 * ファクトリー
	 * @param logonClock PCログオン時刻
	 * @param logoffClock PCログオフ時刻
	 * @return 月別実績のログオン時刻
	 */
	public static PCLogonClockOfMonthly of(
			AggrPCLogonClock logonClock,
			AggrPCLogonClock logoffClock){
		
		PCLogonClockOfMonthly domain = new PCLogonClockOfMonthly();
		domain.logonClock = logonClock;
		domain.logoffClock = logoffClock;
		return domain;
	}
	
	/**
	 * PCログオン時刻
	 * @param pcLogonInfoOpt 日別実績のPCログオン情報 
	 * @param timeLeavingOfDaily 日別実績の出退勤
	 * @param isWeekday 平日かどうか
	 * @param workType 勤務種類
	 * @param predTimeSetForCalc 計算用所定時間設定
	 */
	public void aggregate(
			Optional<PCLogOnInfoOfDailyAttd> pcLogonInfoOpt,
			TimeLeavingOfDailyAttd timeLeavingOfDaily,
			boolean isWeekday,
			WorkType workType,
			PredetermineTimeSetForCalc predTimeSetForCalc) {

		// ログオン時刻の集計
		this.logonClock.aggregateLogOn(pcLogonInfoOpt, isWeekday, workType, timeLeavingOfDaily);
		
		// ログオフ時刻の集計
		this.logoffClock.aggregateLogOff(pcLogonInfoOpt, timeLeavingOfDaily, isWeekday, workType, predTimeSetForCalc);
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(PCLogonClockOfMonthly target){
		
		this.logonClock.sum(target.logonClock);
		this.logoffClock.sum(target.logoffClock);
	}
}
