package nts.uk.ctx.at.record.dom.monthly.excessoutside;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 時間外超過
 * @author shuichu_ishida
 */
@Getter
public class ExcessOutsideWork {

	/** 超過NO */
	private int excessNo;
	/** 超過時間 */
	private AttendanceTimeMonth excessTime;
	/** 内訳No */
	private int breakdownNo;
	
	/**
	 * コンストラクタ
	 * @param excessNo 超過NO
	 */
	public ExcessOutsideWork(int excessNo){
		
		this.excessNo = excessNo;
		this.excessTime = new AttendanceTimeMonth(0);
		this.breakdownNo = 0;
	}
	
	/**
	 * ファクトリー
	 * @param excessNo 超過NO
	 * @param excessTime 超過時間
	 * @param breakdownNo 内訳NO
	 * @return 時間外超過
	 */
	public static ExcessOutsideWork of(int excessNo, AttendanceTimeMonth excessTime, int breakdownNo){
		
		ExcessOutsideWork domain = new ExcessOutsideWork(excessNo);
		domain.excessTime = new AttendanceTimeMonth(excessTime.v());
		domain.breakdownNo = breakdownNo;
		return domain;
	}
}
