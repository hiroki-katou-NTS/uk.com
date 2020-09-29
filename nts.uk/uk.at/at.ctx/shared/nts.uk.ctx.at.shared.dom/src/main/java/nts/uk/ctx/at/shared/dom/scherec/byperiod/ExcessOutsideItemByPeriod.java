package nts.uk.ctx.at.shared.dom.scherec.byperiod;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 期間別の時間外超過項目
 * @author shuichu_ishida
 */
@Getter
public class ExcessOutsideItemByPeriod implements Cloneable {

	/** 内訳NO */
	private int breakdownNo;

	/** 超過時間 */
	private AttendanceTimeMonth excessTime;

	/**
	 * コンストラクタ
	 * @param breakdownNo 内訳NO
	 */
	public ExcessOutsideItemByPeriod(int breakdownNo){
		
		this.breakdownNo = breakdownNo;
		this.excessTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param breakdownNo 内訳NO
	 * @param excessTime 超過時間
	 * @return 期間別の時間外超過項目
	 */
	public static ExcessOutsideItemByPeriod of(
			int breakdownNo,
			AttendanceTimeMonth excessTime){
		
		ExcessOutsideItemByPeriod domain = new ExcessOutsideItemByPeriod(breakdownNo);
		domain.excessTime = excessTime;
		return domain;
	}
	
	@Override
	public ExcessOutsideItemByPeriod clone() {
		ExcessOutsideItemByPeriod cloned = new ExcessOutsideItemByPeriod(this.breakdownNo);
		try {
			cloned.excessTime = new AttendanceTimeMonth(this.excessTime.v());
		}
		catch (Exception e){
			throw new RuntimeException("ExcessOutsideItemByPeriod clone error.");
		}
		return cloned;
	}
}
