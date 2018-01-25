package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.premiumtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の割増時間
 * @author shuichu_ishida
 */
@Getter
public class PremiumTimeOfMonthly {

	/** 割増時間 */
	private Map<Integer, AggregatePremiumTime> premiumTime;
	/** 深夜時間 */
	private AttendanceTimeMonth midnightTime;
	/** 法定内時間外時間 */
	private AttendanceTimeMonth legalOutsideWorkTime;
	/** 法定内休出時間 */
	private AttendanceTimeMonth legalHolidayWorkTime;
	/** 法定外時間外時間 */
	private AttendanceTimeMonth illegalOutsideWorkTime;
	/** 法定外休出時間 */
	private AttendanceTimeMonth illegalHolidayWorkTime;
	
	/**
	 * コンストラクタ
	 */
	public PremiumTimeOfMonthly(){
		
		this.premiumTime = new HashMap<>();
		this.midnightTime = new AttendanceTimeMonth(0);
		this.legalOutsideWorkTime = new AttendanceTimeMonth(0);
		this.legalHolidayWorkTime = new AttendanceTimeMonth(0);
		this.illegalOutsideWorkTime = new AttendanceTimeMonth(0);
		this.illegalHolidayWorkTime = new AttendanceTimeMonth(0);
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
	public static PremiumTimeOfMonthly of(
			List<AggregatePremiumTime> premiumTimes,
			AttendanceTimeMonth midnightTime,
			AttendanceTimeMonth legalOutsideWorkTime,
			AttendanceTimeMonth legalHolidayWorkTime,
			AttendanceTimeMonth illegalOutsideWorkTime,
			AttendanceTimeMonth illegalHolidayWorkTime){
		
		val domain = new PremiumTimeOfMonthly();
		for (val premiumTime : premiumTimes){
			val premiumTimeItemNo = Integer.valueOf(premiumTime.getPremiumTimeItemNo());
			if (!domain.premiumTime.containsKey(premiumTimeItemNo)) {
				domain.premiumTime.put(premiumTimeItemNo, premiumTime);
			}
		}
		domain.midnightTime = midnightTime;
		domain.legalOutsideWorkTime = legalOutsideWorkTime;
		domain.legalHolidayWorkTime = legalHolidayWorkTime;
		domain.illegalOutsideWorkTime = illegalOutsideWorkTime;
		domain.illegalHolidayWorkTime = illegalHolidayWorkTime;
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 */
	public void aggregate(List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		this.premiumTime = new HashMap<>();
		
		// 日別実績の「割増時間」を集計する
		for (val attendanceTimeOfDaily : attendanceTimeOfDailys){
			val actualWorkingTime = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
			val premiumTimeOfDaily = actualWorkingTime.getPremiumTimeOfDailyPerformance();
			for (val premiumTime : premiumTimeOfDaily.getPremiumTimes()){
				val premiumTimeNo = premiumTime.getPremiumTimeNo();
				if (!this.premiumTime.containsKey(premiumTimeNo)) {
					this.premiumTime.put(premiumTimeNo, new AggregatePremiumTime(premiumTimeNo));
				}
				val targetPremiumTime = this.premiumTime.get(premiumTimeNo);
				targetPremiumTime.addMinutesToTime(premiumTime.getPremitumTime().v());
			}
		}
	}
}
