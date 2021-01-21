package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.premiumtime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * 月別実績の割増時間
 * @author shuichi_ishida
 */
@Getter
public class PremiumTimeOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 割増時間 */
	private Map<Integer, AggregatePremiumTime> premiumTime;
	
	/**
	 * コンストラクタ
	 */
	public PremiumTimeOfMonthly(){
		
		this.premiumTime = new HashMap<>();
	}
	
	/**
	 * ファクトリー
	 * @param premiumTimes 割増時間
	 * @param midnightTime 深夜時間
	 * @param legalOutsideWorkTime 法定内時間外時間
	 * @param legalHolidayWorkTime 法定内休出時間
	 * @param illegalOutsideWorkTime 法定外時間外時間
	 * @param illegalHolidayWorkTime 法定外休出時間
	 * @return 月別実績の割増時間
	 */
	public static PremiumTimeOfMonthly of(List<AggregatePremiumTime> premiumTimes){
		
		val domain = new PremiumTimeOfMonthly();
		for (val premiumTime : premiumTimes){
			val premiumTimeItemNo = Integer.valueOf(premiumTime.getPremiumTimeItemNo());
			domain.premiumTime.putIfAbsent(premiumTimeItemNo, premiumTime);
		}
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	public void aggregate(AttendanceTimeOfDailyAttendance attendanceTimeOfDaily){

		if (attendanceTimeOfDaily == null) return;
		
		val actualWorkingTime = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
		val premiumTimeOfDaily = actualWorkingTime.getPremiumTimeOfDailyPerformance();
		if (premiumTimeOfDaily.getPremiumTimes() == null) return;
		for (val premiumTime : premiumTimeOfDaily.getPremiumTimes()){
			val premiumTimeNo = premiumTime.getPremiumTimeNo();
			this.premiumTime.putIfAbsent(premiumTimeNo, new AggregatePremiumTime(premiumTimeNo));
			val targetPremiumTime = this.premiumTime.get(premiumTimeNo);
			targetPremiumTime.addMinutesToTime(premiumTime.getPremitumTime().v());
			targetPremiumTime.addAmount(premiumTime.getPremiumAmount().v());
		}
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(PremiumTimeOfMonthly target){
		
		for (val premiumValue : this.premiumTime.values()){
			val itemNo = premiumValue.getPremiumTimeItemNo();
			if (target.premiumTime.containsKey(itemNo)){
				premiumValue.addMinutesToTime(target.premiumTime.get(itemNo).getTime().v());
				premiumValue.addAmount(target.premiumTime.get(itemNo).getAmount().v());
			}
		}
		for (val targetPremiumValue : target.premiumTime.values()){
			val itemNo = targetPremiumValue.getPremiumTimeItemNo();
			this.premiumTime.putIfAbsent(itemNo, targetPremiumValue);
		}
	}
}
