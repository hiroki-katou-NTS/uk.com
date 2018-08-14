package nts.uk.ctx.at.record.dom.byperiod;

import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 期間別のフレックス時間
 * @author shuichu_ishida
 */
@Getter
public class FlexTimeByPeriod implements Cloneable {

	/** フレックス時間 */
	private AttendanceTimeMonthWithMinus flexTime;
	/** フレックス超過時間 */
	private AttendanceTimeMonth flexExcessTime;
	/** フレックス不足時間 */
	private AttendanceTimeMonth flexShortageTime;
	/** 事前フレックス時間 */
	private AttendanceTimeMonth beforeFlexTime;
	
	/**
	 * コンストラクタ
	 */
	public FlexTimeByPeriod(){
		
		this.flexTime = new AttendanceTimeMonthWithMinus(0);
		this.flexExcessTime = new AttendanceTimeMonth(0);
		this.flexShortageTime = new AttendanceTimeMonth(0);
		this.beforeFlexTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param flexTime フレックス時間
	 * @param flexExcessTime フレックス超過時間
	 * @param flexShortageTime フレックス不足時間
	 * @param beforeFlexTime 事前フレックス時間
	 * @return 期間別のフレックス時間
	 */
	public static FlexTimeByPeriod of(
			AttendanceTimeMonthWithMinus flexTime,
			AttendanceTimeMonth flexExcessTime,
			AttendanceTimeMonth flexShortageTime,
			AttendanceTimeMonth beforeFlexTime){

		FlexTimeByPeriod domain = new FlexTimeByPeriod();
		domain.flexTime = flexTime;
		domain.flexExcessTime = flexExcessTime;
		domain.flexShortageTime = flexShortageTime;
		domain.beforeFlexTime = beforeFlexTime;
		return domain;
	}
	
	@Override
	public FlexTimeByPeriod clone() {
		FlexTimeByPeriod cloned = new FlexTimeByPeriod();
		try {
			cloned.flexTime = new AttendanceTimeMonthWithMinus(this.flexTime.v());
			cloned.flexExcessTime = new AttendanceTimeMonth(this.flexExcessTime.v());
			cloned.flexShortageTime = new AttendanceTimeMonth(this.flexShortageTime.v());
			cloned.beforeFlexTime = new AttendanceTimeMonth(this.beforeFlexTime.v());
		}
		catch (Exception e){
			throw new RuntimeException("FlexTimeByPeriod clone error.");
		}
		return cloned;
	}
	
	/**
	 * フレックス時間の集計
	 * @param period 期間
	 * @param attendanceTimeOfDailyMap 日別実績の勤怠時間リスト
	 */
	public void aggregate(
			DatePeriod period,
			Map<GeneralDate, AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyMap){
		
		for (val attendanceTime : attendanceTimeOfDailyMap.entrySet()){
			if (!period.contains(attendanceTime.getValue().getYmd())) continue;
			val actualWorkingTimeOfDaily = attendanceTime.getValue().getActualWorkingTimeOfDaily();
			val totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();
			val excessPrescribedTimeOfDaily = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
			val overTimeOfDaily = excessPrescribedTimeOfDaily.getOverTimeWork();
			if (!overTimeOfDaily.isPresent()) continue;
			val flexTime = overTimeOfDaily.get().getFlexTime();
			if (flexTime == null) continue;
			
			int flexMinutes = flexTime.getFlexTime().getTime().v();
			int beforeFlexMinutes = flexTime.getBeforeApplicationTime().v();
			
			this.flexTime = this.flexTime.addMinutes(flexMinutes);
			if (flexMinutes >= 0){
				this.flexExcessTime = this.flexExcessTime.addMinutes(flexMinutes);
			}
			else {
				this.flexShortageTime = this.flexShortageTime.addMinutes(-flexMinutes);
			}
			this.beforeFlexTime = this.beforeFlexTime.addMinutes(beforeFlexMinutes);
		}
	}
	
	/**
	 * 総労働対象時間の取得
	 * @return 総労働対象時間
	 */
	public AttendanceTimeMonth getTotalWorkingTargetTime(){
		
		return new AttendanceTimeMonth(this.flexExcessTime.v());
	}
}
