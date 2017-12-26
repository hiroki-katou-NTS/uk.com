package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 時系列の年休使用時間
 * @author shuichi_ishida
 */
@Getter
public class AnnualLeaveUseTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	/** 年休使用時間 */
	//private AnnualLeaveOfDaily annualLeaveUseTime;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveUseTimeOfTimeSeries(){
		
		//this.annualLeaveUseTime = new AnnualLeaveOfDaily();
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @return 時系列の年休使用時間
	 */
	public static AnnualLeaveUseTimeOfTimeSeries of(GeneralDate ymd){
		
		AnnualLeaveUseTimeOfTimeSeries domain = new AnnualLeaveUseTimeOfTimeSeries();
		domain.ymd = ymd;
		return domain;
	}
}
