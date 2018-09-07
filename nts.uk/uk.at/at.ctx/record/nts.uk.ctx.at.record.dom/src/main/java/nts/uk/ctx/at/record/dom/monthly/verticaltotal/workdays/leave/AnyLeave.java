package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonthDom;

/**
 * 任意休業
 * @author shuichu_ishida
 */
@Getter
public class AnyLeave {

	/** 任意休業No */
	private int anyLeaveNo;
	/** 日数 */
	private AttendanceDaysMonthDom days;
	
	/**
	 * コンストラクタ
	 * @param anyLeaveNo 任意休業No
	 */
	public AnyLeave(int anyLeaveNo){
		
		this.anyLeaveNo = anyLeaveNo;
		this.days = new AttendanceDaysMonthDom(0.0);
	}

	/**
	 * ファクトリー
	 * @param anyLeaveNo 任意休業No
	 * @param days 日数
	 * @return 任意休業
	 */
	public static AnyLeave of(
			int anyLeaveNo, AttendanceDaysMonthDom days){
		
		val domain = new AnyLeave(anyLeaveNo);
		domain.days = days;
		return domain;
	}
	
	/**
	 * 日数を加算する
	 * @param days 日数
	 */
	public void addDays(Double days){
		this.days = this.days.addDays(days);
	}
}
