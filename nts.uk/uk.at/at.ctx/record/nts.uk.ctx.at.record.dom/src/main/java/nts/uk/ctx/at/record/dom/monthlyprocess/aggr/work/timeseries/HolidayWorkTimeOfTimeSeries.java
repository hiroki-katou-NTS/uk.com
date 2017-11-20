package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.holidaywork.HolidayWorkFrameTime;

/**
 * 時系列の休出時間
 * @author shuichi_ishida
 */
@Getter
public class HolidayWorkTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	/** 休出時間 */
	private List<HolidayWorkFrameTime> holidayWorkTimes;
	/** 法定内休出時間 */
	private List<HolidayWorkFrameTime> withinStatutoryHolidayWorkTimes;
	
	/**
	 * コンストラクタ
	 */
	public HolidayWorkTimeOfTimeSeries(){
		
		this.holidayWorkTimes = new ArrayList<>();
		this.withinStatutoryHolidayWorkTimes = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @return 時系列の休出時間
	 */
	public static HolidayWorkTimeOfTimeSeries of(GeneralDate ymd){
		
		HolidayWorkTimeOfTimeSeries domain = new HolidayWorkTimeOfTimeSeries();
		domain.ymd = ymd;
		return domain;
	}
}
