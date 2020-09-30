package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 時間外超過のフレックス時間
 * @author shuichi_ishida
 */
@Getter
public class FlexTimeOfExcessOutsideTime implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 超過フレ区分 */
	private ExcessFlexAtr excessFlexAtr;
	/** 原則時間 */
	@Setter
	private AttendanceTimeMonth principleTime;
	/** 便宜上時間 */
	@Setter
	private AttendanceTimeMonth forConvenienceTime;
	/** 当月フレックス時間 */
	@Setter
	private FlexTimeCurrentMonth flexTimeCurrentMonth;

	/**
	 * コンストラクタ
	 */
	public FlexTimeOfExcessOutsideTime(){
		
		this.excessFlexAtr = ExcessFlexAtr.PRINCIPLE;
		this.principleTime = new AttendanceTimeMonth(0);
		this.forConvenienceTime = new AttendanceTimeMonth(0);
		this.flexTimeCurrentMonth = new FlexTimeCurrentMonth();
	}
	
	/**
	 * ファクトリー
	 * @param excessFlexAtr 超過フレ区分
	 * @param principleTime 原則時間
	 * @param forConvenienceTime 便宜上時間
	 * @param flexTimeCurrentMonth 当月フレックス時間
	 * @return 時間外超過のフレックス時間
	 */
	public static FlexTimeOfExcessOutsideTime of(
			ExcessFlexAtr excessFlexAtr,
			AttendanceTimeMonth principleTime,
			AttendanceTimeMonth forConvenienceTime,
			FlexTimeCurrentMonth flexTimeCurrentMonth){

		val domain = new FlexTimeOfExcessOutsideTime();
		domain.excessFlexAtr = excessFlexAtr;
		domain.principleTime = principleTime;
		domain.forConvenienceTime = forConvenienceTime;
		domain.flexTimeCurrentMonth = flexTimeCurrentMonth;
		return domain;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(FlexTimeOfExcessOutsideTime target){

		this.principleTime = this.principleTime.addMinutes(target.principleTime.v());
		this.forConvenienceTime = this.forConvenienceTime.addMinutes(target.forConvenienceTime.v());
		this.flexTimeCurrentMonth.sum(target.flexTimeCurrentMonth);
	}
}
