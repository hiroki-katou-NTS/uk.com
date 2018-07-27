package nts.uk.ctx.at.record.dom.monthly.excessoutside;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 時間外超過
 * @author shuichu_ishida
 */
@Getter
public class ExcessOutsideWork {

	/** 内訳NO */
	private int breakdownNo;
	/** 超過NO */
	private int excessNo;
	/** 超過時間 */
	private AttendanceTimeMonth excessTime;
	
	/**
	 * コンストラクタ
	 * @param breakdownNo 内訳NO
	 * @param excessNo 超過NO
	 */
	public ExcessOutsideWork(int breakdownNo, int excessNo){
		
		this.breakdownNo = breakdownNo;
		this.excessNo = excessNo;
		this.excessTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param breakdownNo 内訳NO
	 * @param excessNo 超過NO
	 * @param excessTime 超過時間
	 * @return 時間外超過
	 */
	public static ExcessOutsideWork of(int breakdownNo, int excessNo, AttendanceTimeMonth excessTime){
		
		ExcessOutsideWork domain = new ExcessOutsideWork(breakdownNo, excessNo);
		domain.excessTime = excessTime;
		return domain;
	}
	
	/**
	 * 分を超過時間に加算する
	 * @param minutes
	 */
	public void addMinutesToExcessTime(int minutes){
		this.excessTime = this.excessTime.addMinutes(minutes);
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(ExcessOutsideWork target){

		this.excessTime = this.excessTime.addMinutes(target.excessTime.v());
	}
}
