package nts.uk.ctx.at.record.dom.byperiod;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;

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
}
