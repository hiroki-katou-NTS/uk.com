package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;

/**
 * 
 * @author nampt
 * 休日時打刻反映時間帯
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class StampReflectOnHolidayOutPut {
	
	// 前々日の打刻反映範囲
	private StampReflectRangeOutput stampReflectTwoDayBefore;
	
	// 前日の打刻反映範囲
	private StampReflectRangeOutput stampReflectPreviousDay;
	
	// 当日の打刻反映範囲
	private StampReflectRangeOutput stampReflectThisDay;
	
	// 翌日の打刻反映範囲
	private StampReflectRangeOutput stampReflectNextDay;

}
