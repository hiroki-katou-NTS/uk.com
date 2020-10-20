package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.excessoutside;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;

/**
 * 休出枠合計時間
 * @author shuichu_ishida
 */
@Getter
public class HolidayWorkFrameTotalTime {

	/** 休出枠NO */
	private HolidayWorkFrameNo holidayWorkFrameNo;
	
	/** 休出時間 */
	private TimeMonthWithCalculation holidayWorkTime;
	/** 振替時間 */
	private TimeMonthWithCalculation transferTime;

	/**
	 * コンストラクタ
	 * @param holidayWorkFrameNo 休出枠NO
	 */
	public HolidayWorkFrameTotalTime(HolidayWorkFrameNo holidayWorkFrameNo){
		
		this.holidayWorkFrameNo = holidayWorkFrameNo;

		this.holidayWorkTime = TimeMonthWithCalculation.ofSameTime(0);
		this.transferTime = TimeMonthWithCalculation.ofSameTime(0);
	}
	
	/**
	 * ファクトリー
	 * @param holidayWorkFrameNo 休出枠NO
	 * @param holidayWorkTime 休出時間
	 * @param transferTime 振替時間
	 * @return 休出枠合計時間
	 */
	public static HolidayWorkFrameTotalTime of(
			HolidayWorkFrameNo holidayWorkFrameNo,
			TimeMonthWithCalculation holidayWorkTime,
			TimeMonthWithCalculation transferTime){
		
		val domain = new HolidayWorkFrameTotalTime(holidayWorkFrameNo);
		domain.holidayWorkTime = holidayWorkTime;
		domain.transferTime = transferTime;
		return domain;
	}
}
