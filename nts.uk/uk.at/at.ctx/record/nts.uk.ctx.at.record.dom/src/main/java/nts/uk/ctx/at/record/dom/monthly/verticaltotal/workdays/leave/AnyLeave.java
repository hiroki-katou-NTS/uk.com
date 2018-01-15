package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;

/**
 * 任意休業
 * @author shuichu_ishida
 */
@Getter
public class AnyLeave {

	/** 任意休業No */
	private int anyLeaveNo;
	/** 日数 */
	private AttendanceDaysMonth days;
	
	/**
	 * コンストラクタ
	 */
	public AnyLeave(){
		
		this.anyLeaveNo = 0;
		this.days = new AttendanceDaysMonth(0.0);
	}

	/**
	 * ファクトリー
	 * @param anyLeaveNo 任意休業No
	 * @param days 日数
	 * @return 任意休業
	 */
	public static AnyLeave of(
			int anyLeaveNo, AttendanceDaysMonth days){
		
		val domain = new AnyLeave();
		domain.anyLeaveNo = anyLeaveNo;
		domain.days = days;
		return domain;
	}
}
