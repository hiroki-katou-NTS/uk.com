package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.bonuspaytime;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 集計加給時間
 * @author shuichi_ishida
 */
@Getter
public class AggregateBonusPayTime implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 加給枠No */
	private int bonusPayFrameNo;
	/** 加給時間 */
	private AttendanceTimeMonth bonusPayTime;
	/** 特定加給時間 */
	private AttendanceTimeMonth specificBonusPayTime;
	/** 休出加給時間 */
	private AttendanceTimeMonth holidayWorkBonusPayTime;
	/** 休出特定加給時間 */
	private AttendanceTimeMonth holidayWorkSpecificBonusPayTime;
	/** 所定内加給時間 */
	private AttendanceTimeMonth within;
	/** 所定内特定加給時間 */
	private AttendanceTimeMonth withinSpecific;
	/** 所定外加給時間 */
	private AttendanceTimeMonth excess;
	/** 所定外特定加給時間 */
	private AttendanceTimeMonth excessSpecific;
	
	/**
	 * コンストラクタ
	 */
	public AggregateBonusPayTime(int bonusPayFrameNo){
		
		this.bonusPayFrameNo = bonusPayFrameNo;
		this.bonusPayTime = new AttendanceTimeMonth(0);
		this.specificBonusPayTime = new AttendanceTimeMonth(0);
		this.holidayWorkBonusPayTime = new AttendanceTimeMonth(0);
		this.holidayWorkSpecificBonusPayTime = new AttendanceTimeMonth(0);
		this.within = new AttendanceTimeMonth(0);
		this.withinSpecific = new AttendanceTimeMonth(0);
		this.excess = new AttendanceTimeMonth(0);
		this.excessSpecific = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param bonusPayFrameNo 加給時間枠No
	 * @param bonusPayTime 加給時間
	 * @param specificBonusPayTime 特定加給時間
	 * @param holidayWorkBonusPayTime 休出加給時間
	 * @param holidayWorkSpecificBonusPayTime 休出特定加給時間
	 * @param within 所定内加給時間
	 * @param withinSpecific 所定内特定加給時間
	 * @param excess 所定外加給時間
	 * @param excessSpecific 所定外特定加給時間
	 * @return 集計加給時間
	 */
	public static AggregateBonusPayTime of(
			int bonusPayFrameNo,
			AttendanceTimeMonth bonusPayTime,
			AttendanceTimeMonth specificBonusPayTime,
			AttendanceTimeMonth holidayWorkBonusPayTime,
			AttendanceTimeMonth holidayWorkSpecificBonusPayTime,
			AttendanceTimeMonth within,
			AttendanceTimeMonth withinSpecific,
			AttendanceTimeMonth excess,
			AttendanceTimeMonth excessSpecific){
		
		val domain = new AggregateBonusPayTime(bonusPayFrameNo);
		domain.bonusPayTime = bonusPayTime;
		domain.specificBonusPayTime = specificBonusPayTime;
		domain.holidayWorkBonusPayTime = holidayWorkBonusPayTime;
		domain.holidayWorkSpecificBonusPayTime = holidayWorkSpecificBonusPayTime;
		domain.within = within;
		domain.withinSpecific = withinSpecific;
		domain.excess = excess;
		domain.excessSpecific = excessSpecific;
		return domain;
	}
	
	/**
	 * 加給時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToBonusPayTime(int minutes){
		this.bonusPayTime = this.bonusPayTime.addMinutes(minutes);
	}
	
	/**
	 * 特定加給時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToSpecificBonusPayTime(int minutes){
		this.specificBonusPayTime = this.specificBonusPayTime.addMinutes(minutes);
	}
	
	/**
	 * 休出加給時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToHolidayWorkBonusPayTime(int minutes){
		this.holidayWorkBonusPayTime = this.holidayWorkBonusPayTime.addMinutes(minutes);
	}
	
	/**
	 * 休出特定加給時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToHolidayWorkSpecificBonusPayTime(int minutes){
		this.holidayWorkSpecificBonusPayTime = this.holidayWorkSpecificBonusPayTime.addMinutes(minutes);
	}
	
	/**
	 * 所定内加給時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToWithinBonusPayTime(int minutes){
		this.within = this.within.addMinutes(minutes);
	}

	/**
	 * 所定内特定加給時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToWithinSpecificBonusPayTime(int minutes){
		this.withinSpecific = this.withinSpecific.addMinutes(minutes);
	}

	/**
	 * 所定外加給時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToExcessBonusPayTime(int minutes){
		this.excess = this.excess.addMinutes(minutes);
	}

	/**
	 * 所定外特定加給時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToExcessSpecificBonusPayTime(int minutes){
		this.excessSpecific = this.excessSpecific.addMinutes(minutes);
	}
}
