package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.AnnualOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 時系列の年休使用時間
 * @author shuichi_ishida
 */
@Getter
public class AnnualLeaveUseTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	
	/** 年休使用時間 */
	private AnnualOfDaily annualLeaveUseTime;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveUseTimeOfTimeSeries(GeneralDate ymd){
		
		this.ymd = ymd;
		this.annualLeaveUseTime = new AnnualOfDaily(new AttendanceTime(0), new AttendanceTime(0));
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param annualLeaveUseTime 年休使用時間
	 * @return 時系列の年休使用時間
	 */
	public static AnnualLeaveUseTimeOfTimeSeries of(
			GeneralDate ymd, AnnualOfDaily annualLeaveUseTime){
		
		val domain = new AnnualLeaveUseTimeOfTimeSeries(ymd);
		domain.annualLeaveUseTime = annualLeaveUseTime;
		return domain;
	}
}
