package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;

/**
 * 時系列の積立年休使用時間
 * @author shuichi_ishida
 */
@Getter
public class RetentionYearlyUseTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	/** 積立年休使用時間 */
	//private RetentionYearlyOfDaily retentionYearly;

	/**
	 * コンストラクタ
	 */
	public RetentionYearlyUseTimeOfTimeSeries(){
		
		//this.retentionYearly = new RetentionYearlyOfDaily();
	}

	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @return 時系列の積立年休使用時間
	 */
	public static RetentionYearlyUseTimeOfTimeSeries of(GeneralDate ymd){
		
		val domain = new RetentionYearlyUseTimeOfTimeSeries();
		domain.ymd = ymd;
		return domain;
	}
}
