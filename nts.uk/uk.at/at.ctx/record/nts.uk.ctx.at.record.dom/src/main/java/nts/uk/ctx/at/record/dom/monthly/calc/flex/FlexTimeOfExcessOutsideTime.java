package nts.uk.ctx.at.record.dom.monthly.calc.flex;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 時間外超過のフレックス時間
 * @author shuichi_ishida
 */
@Getter
public class FlexTimeOfExcessOutsideTime {

	/** 超過フレ区分 */
	private ExcessFlexAtr excessFlexAtr;
	/** 原則時間 */
	private AttendanceTimeMonth principleTime;
	/** 便宜上時間 */
	private AttendanceTimeMonth forConvenienceTime;
	
	/**
	 * コンストラクタ
	 */
	public FlexTimeOfExcessOutsideTime(){
		
		this.excessFlexAtr = ExcessFlexAtr.Principle;
	}

	/**
	 * ファクトリー
	 * @param excessFlexAtr 超過フレ区分
	 * @param principleTime 原則時間
	 * @param forConvenienceTime 便宜上時間
	 * @return 時間外超過のフレックス時間
	 */
	public static FlexTimeOfExcessOutsideTime of(
			ExcessFlexAtr excessFlexAtr,
			AttendanceTimeMonth principleTime,
			AttendanceTimeMonth forConvenienceTime){

		FlexTimeOfExcessOutsideTime domain = new FlexTimeOfExcessOutsideTime();
		domain.excessFlexAtr = excessFlexAtr;
		domain.principleTime = principleTime;
		domain.forConvenienceTime = forConvenienceTime;
		return domain;
	}
}
