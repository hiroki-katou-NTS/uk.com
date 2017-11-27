package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeFrameTime;

/**
 * 時系列の残業時間
 * @author shuichi_ishida
 */
@Getter
public class OverTimeWorkOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	/** 休出時間 */
	private List<OverTimeFrameTime> overTimeWorks;
	/** 法定内休出時間 */
	private List<OverTimeFrameTime> withinStatutoryOverTimeWorks;
	
	/**
	 * コンストラクタ
	 */
	public OverTimeWorkOfTimeSeries(){
		
		this.overTimeWorks = new ArrayList<>();
		this.withinStatutoryOverTimeWorks = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @return 時系列の残業時間
	 */
	public static OverTimeWorkOfTimeSeries of(GeneralDate ymd){
		
		OverTimeWorkOfTimeSeries domain = new OverTimeWorkOfTimeSeries();
		domain.ymd = ymd;
		return domain;
	}
}
