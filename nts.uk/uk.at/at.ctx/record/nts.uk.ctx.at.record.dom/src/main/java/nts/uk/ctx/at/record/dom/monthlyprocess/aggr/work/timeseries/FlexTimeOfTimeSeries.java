package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 時系列のフレックス時間
 * @author shuichi_ishida
 */
@Getter
public class FlexTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	/** フレックス時間（日別実績） */
	//private FlexTime flexTime;
	
	/**
	 * コンストラクタ
	 */
	public FlexTimeOfTimeSeries(){
		
		//this.flexTime = new FlexTime();
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @return 時系列の休出時間
	 */
	public static FlexTimeOfTimeSeries of(GeneralDate ymd){
		
		FlexTimeOfTimeSeries domain = new FlexTimeOfTimeSeries();
		domain.ymd = ymd;
		return domain;
	}
}
