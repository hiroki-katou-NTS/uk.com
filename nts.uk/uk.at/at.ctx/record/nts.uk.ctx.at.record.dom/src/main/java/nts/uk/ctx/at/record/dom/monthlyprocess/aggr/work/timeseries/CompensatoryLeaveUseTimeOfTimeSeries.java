package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;

/**
 * 時系列の代休使用時間
 * @author shuichi_ishida
 */
@Getter
public class CompensatoryLeaveUseTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	
	/** 代休使用時間 */
	//private CompensatoryLeaveOfDaily compensatoryLeaveUseTime;

	/**
	 * コンストラクタ
	 */
	public CompensatoryLeaveUseTimeOfTimeSeries(GeneralDate ymd){
		
		this.ymd = ymd;
		//this.compensatoryLeaveUseTime = new CompensatoryLeaveOfDaily();
	}

	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @return 時系列の代休使用時間
	 */
	public static CompensatoryLeaveUseTimeOfTimeSeries of(GeneralDate ymd){
		
		val domain = new CompensatoryLeaveUseTimeOfTimeSeries(ymd);
		return domain;
	}
}