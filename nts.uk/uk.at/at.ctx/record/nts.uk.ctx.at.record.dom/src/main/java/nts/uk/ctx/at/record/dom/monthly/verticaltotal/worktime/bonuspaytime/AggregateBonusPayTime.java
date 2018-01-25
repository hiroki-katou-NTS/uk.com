package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.bonuspaytime;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 集計加給時間
 * @author shuichu_ishida
 */
@Getter
public class AggregateBonusPayTime {

	/** 加給枠No */
	private int bonusPayFrameNo;
	/** 加給時間 */
	private AttendanceTimeMonth bonusPay;
	/** 特定加給時間 */
	private AttendanceTimeMonth specificBonusPayTime;
	/** 休出加給時間 */
	private AttendanceTimeMonth holidayWorkBonusPayTime;
	/** 休出特定加給時間 */
	private AttendanceTimeMonth holidayWorkSpecificBonusPayTime;
	
	/**
	 * コンストラクタ
	 */
	public AggregateBonusPayTime(int bonusPayFrameNo){
		
		this.bonusPayFrameNo = bonusPayFrameNo;
		this.bonusPay = new AttendanceTimeMonth(0);
		this.specificBonusPayTime = new AttendanceTimeMonth(0);
		this.holidayWorkBonusPayTime = new AttendanceTimeMonth(0);
		this.holidayWorkSpecificBonusPayTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param bonusPayFrameNo 加給時間枠No
	 * @param bonusPay 加給時間
	 * @param specificBonusPayTime 特定加給時間
	 * @param holidayWorkBonusPayTime 休出加給時間
	 * @param holidayWorkSpecificBonusPayTime 休出特定加給時間
	 * @return 集計加給時間
	 */
	public static AggregateBonusPayTime of(
			int bonusPayFrameNo,
			AttendanceTimeMonth bonusPay,
			AttendanceTimeMonth specificBonusPayTime,
			AttendanceTimeMonth holidayWorkBonusPayTime,
			AttendanceTimeMonth holidayWorkSpecificBonusPayTime){
		
		val domain = new AggregateBonusPayTime(bonusPayFrameNo);
		domain.bonusPay = bonusPay;
		domain.specificBonusPayTime = specificBonusPayTime;
		domain.holidayWorkBonusPayTime = holidayWorkBonusPayTime;
		domain.holidayWorkSpecificBonusPayTime = holidayWorkSpecificBonusPayTime;
		return domain;
	}
	
	/**
	 * 加給時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToBonusPay(int minutes){
		this.bonusPay = this.bonusPay.addMinutes(minutes);
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
}
