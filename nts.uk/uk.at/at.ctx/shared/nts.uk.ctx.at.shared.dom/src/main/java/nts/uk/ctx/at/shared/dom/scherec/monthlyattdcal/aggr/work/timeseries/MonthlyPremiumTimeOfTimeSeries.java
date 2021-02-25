package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 時系列の月割増時間
 * @author shuichu_ishida
 */
@Getter
public class MonthlyPremiumTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	/** 月割増合計時間 */
	private AttendanceTimeMonth monthlyTotalPremiumTime;
	
	/**
	 * コンストラクタ
	 * @param ymd 年月日
	 */
	public MonthlyPremiumTimeOfTimeSeries(GeneralDate ymd){
		
		this.ymd = ymd;
		this.monthlyTotalPremiumTime = new AttendanceTimeMonth(0);
	}

	/**
	 * 月割増時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToMonthlyPremiumTime(int minutes){
		this.monthlyTotalPremiumTime = this.monthlyTotalPremiumTime.addMinutes(minutes);
	}
}
