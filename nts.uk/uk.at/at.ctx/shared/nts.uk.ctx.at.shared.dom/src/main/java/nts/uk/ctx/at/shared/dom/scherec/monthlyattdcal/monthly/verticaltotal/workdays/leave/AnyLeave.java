package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.leave;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;

/**
 * 任意休業
 * @author shuichi_ishida
 */
@Getter
public class AnyLeave implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 任意休業No */
	private int anyLeaveNo;
	/** 日数 */
	private AttendanceDaysMonth days;
	
	/**
	 * コンストラクタ
	 * @param anyLeaveNo 任意休業No
	 */
	public AnyLeave(int anyLeaveNo){
		
		this.anyLeaveNo = anyLeaveNo;
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
