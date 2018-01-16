package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.bonuspaytime;

import lombok.Getter;
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
	/** 休出加給時間 */
	private AttendanceTimeMonth holidayWorkBonusPayTime;
	/** 休出特定加給時間 */
	private AttendanceTimeMonth holidayWorkSpecificBonusPayTime;
	/** 特定加給時間 */
	private AttendanceTimeMonth specificBonusPayTime;
	
	/**
	 * コンストラクタ
	 */
	public AggregateBonusPayTime(){
		
		this.bonusPayFrameNo = 0;
		this.bonusPay = new AttendanceTimeMonth(0);
		this.holidayWorkBonusPayTime = new AttendanceTimeMonth(0);
		this.holidayWorkSpecificBonusPayTime = new AttendanceTimeMonth(0);
		this.specificBonusPayTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param bonusPayFrameNo 加給時間枠No
	 * @param bonusPay 加給時間
	 * @param holidayWorkBonusPayTime 休出加給時間
	 * @param holidayWorkSpecificBonusPayTime 休出特定加給時間
	 * @param specificBonusPayTime 特定加給時間
	 * @return 集計加給時間
	 */
	public static AggregateBonusPayTime of(
			int bonusPayFrameNo,
			AttendanceTimeMonth bonusPay,
			AttendanceTimeMonth holidayWorkBonusPayTime,
			AttendanceTimeMonth holidayWorkSpecificBonusPayTime,
			AttendanceTimeMonth specificBonusPayTime){
		
		val domain = new AggregateBonusPayTime();
		domain.bonusPayFrameNo = bonusPayFrameNo;
		domain.bonusPay = bonusPay;
		domain.holidayWorkBonusPayTime = holidayWorkBonusPayTime;
		domain.holidayWorkSpecificBonusPayTime = holidayWorkSpecificBonusPayTime;
		domain.specificBonusPayTime = specificBonusPayTime;
		return domain;
	}
}
