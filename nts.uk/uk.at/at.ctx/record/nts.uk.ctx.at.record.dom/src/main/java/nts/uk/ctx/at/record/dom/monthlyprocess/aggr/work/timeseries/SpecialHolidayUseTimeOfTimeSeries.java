package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;

/**
 * 時系列の特別休暇使用時間
 * @author shuichi_ishida
 */
@Getter
public class SpecialHolidayUseTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	/** 特別休暇使用時間 */
	//private SpecialHolidayOfDaily specialHolidayUseTime;

	/**
	 * コンストラクタ
	 */
	public SpecialHolidayUseTimeOfTimeSeries(){
		
		//this.specialHolidayUseTime = new SpecialHolidayOfDaily();
	}

	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @return 時系列の特別休暇使用時間
	 */
	public static SpecialHolidayUseTimeOfTimeSeries of(GeneralDate ymd){
		
		val domain = new SpecialHolidayUseTimeOfTimeSeries();
		domain.ymd = ymd;
		return domain;
	}
}
