package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.excessoutside;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 残業枠合計時間
 * @author shuichu_ishida
 */
@Getter
public class OverTimeFrameTotalTime {

	/** 残業枠NO */
	private OverTimeFrameNo overTimeFrameNo;
	
	/** 残業時間 */
	private TimeMonthWithCalculation overTime;
	/** 振替残業時間 */
	private TimeMonthWithCalculation transferOverTime;

	/**
	 * コンストラクタ
	 * @param overTimeFrameNo 残業枠NO
	 */
	public OverTimeFrameTotalTime(OverTimeFrameNo overTimeFrameNo){

		this.overTimeFrameNo = overTimeFrameNo;
		
		this.overTime = TimeMonthWithCalculation.ofSameTime(0);
		this.transferOverTime = TimeMonthWithCalculation.ofSameTime(0);
	}

	/**
	 * ファクトリー
	 * @param overTimeFrameNo 残業枠NO
	 * @param overTime 残業時間
	 * @param transferOverTime 振替残業時間
	 * @return 残業枠合計時間
	 */
	public static OverTimeFrameTotalTime of(
			OverTimeFrameNo overTimeFrameNo,
			TimeMonthWithCalculation overTime,
			TimeMonthWithCalculation transferOverTime){

		val domain = new OverTimeFrameTotalTime(overTimeFrameNo);
		domain.overTime = overTime;
		domain.transferOverTime = transferOverTime;
		return domain;
	}
}
