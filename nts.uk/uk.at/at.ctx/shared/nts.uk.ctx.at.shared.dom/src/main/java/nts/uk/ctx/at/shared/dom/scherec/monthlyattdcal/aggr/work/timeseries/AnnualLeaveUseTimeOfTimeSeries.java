package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.AnnualOfDaily;

/**
 * 時系列の年休使用時間
 * @author shuichi_ishida
 */
@Getter
public class AnnualLeaveUseTimeOfTimeSeries implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

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
