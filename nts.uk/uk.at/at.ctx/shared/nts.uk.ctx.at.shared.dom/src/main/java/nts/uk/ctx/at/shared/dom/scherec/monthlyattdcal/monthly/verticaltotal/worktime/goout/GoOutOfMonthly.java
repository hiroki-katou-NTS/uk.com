package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.goout;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

/**
 * 月別実績の外出
 * @author shuichi_ishida
 */
@Getter
public class GoOutOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 外出 */
	private Map<GoingOutReason, AggregateGoOut> goOuts;
	/** 育児外出 */
	private Map<ChildCareAtr, GoOutForChildCare> goOutForChildCares;
	
	/**
	 * コンストラクタ
	 */
	public GoOutOfMonthly(){
		
		this.goOuts = new HashMap<>();
		this.goOutForChildCares = new HashMap<>();
	}

	/**
	 * ファクトリー
	 * @param goOuts 外出
	 * @param goOutForChildCares 育児外出
	 * @return 月別実績の外出
	 */
	public static GoOutOfMonthly of(
			List<AggregateGoOut> goOuts,
			List<GoOutForChildCare> goOutForChildCares){
		
		val domain = new GoOutOfMonthly();
		for (val goOut : goOuts){
			val goOutReason = goOut.getGoOutReason();
			domain.goOuts.putIfAbsent(goOutReason, goOut);
		}
		for (val goOutForChildCare : goOutForChildCares){
			val childCareAtr = goOutForChildCare.getChildCareAtr();
			domain.goOutForChildCares.putIfAbsent(childCareAtr, goOutForChildCare);
		}
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	public void aggregate(AttendanceTimeOfDailyAttendance attendanceTimeOfDaily){

		if (attendanceTimeOfDaily == null) return;
		
		val totalWorkingTime = attendanceTimeOfDaily.getActualWorkingTimeOfDaily().getTotalWorkingTime();
		val outingTimeList = totalWorkingTime.getOutingTimeOfDailyPerformance();
		val shortTime = totalWorkingTime.getShotrTimeOfDaily();
		
		// 日別実績の「外出時間・回数」を集計する
		for (val outingTime : outingTimeList){
			if (outingTime.getReason() == null) continue;
			GoingOutReason goingOutReason = outingTime.getReason();
			val recordTotal = outingTime.getRecordTotalTime();
			if (recordTotal == null) continue;
			
			this.goOuts.putIfAbsent(goingOutReason, new AggregateGoOut(goingOutReason));
			val targetGoOut = this.goOuts.get(goingOutReason);
			targetGoOut.addMinutesToLegalTime(/** 集計外出。所定内時間 */
					recordTotal.getWithinTotalTime().getTotalTime().getTime().v(),
					recordTotal.getWithinTotalTime().getTotalTime().getCalcTime().v());
			targetGoOut.addMinutesToIllegalTime(/** 集計外出。所定外時間 */
					recordTotal.getExcessTotalTime().getTime().v(),
					recordTotal.getExcessTotalTime().getCalcTime().v());
			targetGoOut.addMinutesToTotalTime(/** 集計外出。合計時間 */
					recordTotal.getTotalTime().getTime().v(),
					recordTotal.getTotalTime().getCalcTime().v());
			targetGoOut.addMinutesToCoreOutTime(/** 集計外出。コアタイム外時間 */
					recordTotal.getWithinTotalTime().getWithinCoreTime().getTime().valueAsMinutes(), 
					recordTotal.getWithinTotalTime().getWithinCoreTime().getCalcTime().valueAsMinutes());
			targetGoOut.addTimes(outingTime.getWorkTime().v()); /** 集計外出。回数 */
		}
		
		// 日別実績の「短時間・回数」を集計する
		if (shortTime != null){
			ChildCareAtr childCareAtr = shortTime.getChildCareAttribute();
			
			this.goOutForChildCares.putIfAbsent(childCareAtr, new GoOutForChildCare(childCareAtr));
			val targetChildCare = this.goOutForChildCares.get(childCareAtr);
			/** 回数 */
			targetChildCare.addTimes(shortTime.getWorkTimes().v());
			/** 時間 */
			targetChildCare.addMinutesToTime(shortTime.getTotalTime().getTotalTime().getTime().v());
			/** 所定外介護時間 */
			targetChildCare.addMinutesToExcessTime(shortTime.getTotalTime().getExcessOfStatutoryTotalTime().getTime().valueAsMinutes());
			/** 所定内介護時間 */
			targetChildCare.addMinutesToWithinTime(shortTime.getTotalTime().getWithinStatutoryTotalTime().getTime().valueAsMinutes());
		}
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(GoOutOfMonthly target){
		
		for (val goOut : this.goOuts.values()){
			val goOutReason = goOut.getGoOutReason();
			if (target.goOuts.containsKey(goOutReason)){
				val targetGoOut = target.goOuts.get(goOutReason);
				goOut.addTimes(targetGoOut.getTimes().v());
				goOut.addMinutesToLegalTime(
						targetGoOut.getLegalTime().getTime().v(), targetGoOut.getLegalTime().getCalcTime().v());
				goOut.addMinutesToIllegalTime(
						targetGoOut.getIllegalTime().getTime().v(), targetGoOut.getIllegalTime().getCalcTime().v());
				goOut.addMinutesToTotalTime(
						targetGoOut.getTotalTime().getTime().v(), targetGoOut.getTotalTime().getCalcTime().v());
				goOut.addMinutesToCoreOutTime(
						targetGoOut.getCoreOutTime().getTime().v(), targetGoOut.getCoreOutTime().getCalcTime().v());
			}
		}
		for (val targetGoOut : target.goOuts.values()){
			val goOutReason = targetGoOut.getGoOutReason();
			this.goOuts.putIfAbsent(goOutReason, targetGoOut);
		}
		
		for (val childCare : this.goOutForChildCares.values()){
			val childCareAtr = childCare.getChildCareAtr();
			if (target.goOutForChildCares.containsKey(childCareAtr)){
				val targetChildCare = target.goOutForChildCares.get(childCareAtr);
				childCare.addTimes(targetChildCare.getTimes().v());
				childCare.addMinutesToTime(targetChildCare.getTime().v());
				childCare.addMinutesToWithinTime(targetChildCare.getWithinTime().v());
				childCare.addMinutesToExcessTime(targetChildCare.getExcessTime().v());
			}
		}
		for (val targetChildCare : target.goOutForChildCares.values()){
			val childCareAtr = targetChildCare.getChildCareAtr();
			this.goOutForChildCares.putIfAbsent(childCareAtr, targetChildCare);
		}
	}
}
