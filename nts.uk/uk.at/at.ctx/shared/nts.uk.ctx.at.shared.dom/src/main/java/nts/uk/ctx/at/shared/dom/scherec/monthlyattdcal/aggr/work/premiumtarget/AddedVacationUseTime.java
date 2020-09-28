package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 加算した休暇使用時間
 * @author shuichu_ishida
 */
@Getter
public class AddedVacationUseTime implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 月単位の加算時間 */
	private AttendanceTimeMonth addTimePerMonth;
	
	/**
	 * コンストラクタ
	 */
	public AddedVacationUseTime(){
		
		this.addTimePerMonth = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param addTimePerMonth 月単位の加算時間
	 * @return 加算した休暇使用時間
	 */
	public static AddedVacationUseTime of(AttendanceTimeMonth addTimePerMonth){
		
		val domain = new AddedVacationUseTime();
		domain.addTimePerMonth = addTimePerMonth;
		return domain;
	}
	
	/**
	 * 月単位の加算時間に加算する
	 * @param minutes 加算する時間（分）
	 */
	public void addMinutesToAddTimePerMonth(int minutes){
		
		this.addTimePerMonth = this.addTimePerMonth.addMinutes(minutes);
	}
}
