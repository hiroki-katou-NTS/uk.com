/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexworkset;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluOffdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluWeekdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class FlexWorkSetting.
 * フレックス勤務設定
 */
@Getter
// フレックス勤務設定
public class FlexWorkSetting extends AggregateRoot {

	/** The working code. */
	// 就業時間帯コード
	private String workingCode;

	/** The weekday work time. */
	// 平日勤務時間帯
	private FluWeekdayWorkTime weekdayWorkTime;

	/** The offday work time. */
	// 休日勤務時間帯
	private FluOffdayWorkTime offdayWorkTime;

	// 共通設定
	// private WorkTimeCommonSet commonSetting;

	// 休憩設定: 固定勤務の休憩設定
	// private 固定勤務の休憩設定 restSetting;

	// 打刻反映時間帯
	// private List<打刻反映時間帯> stampImprintingTime

	// 半日用シフトを使用する
	// private Boolean useHalfDayShift;

	// コアタイム時間帯設定
	 private CoreTimeSetting coreTimeSetting;
	 
	 /**
	  * 時間帯の中で一番早い時刻を取得
	  * @param workType 勤務種類
	  * @return 時刻
	  */
	 public TimeWithDayAttr getMostEarlyTime(WorkType workType) {
		 List<TimeSpanWithRounding> timeSpans = new ArrayList<>();
//		 if(workType/*平日*/) {
//			 timeSpans.addAll(weekdayWorkTime.getWorkingTime().getWorkingHours().stream().map(tc -> tc.getTimeSpan()).collect(Collectors.toList()));
//			 timeSpans.addAll(weekdayWorkTime.getWorkingTime().getWorkingHours().stream().map(tc -> tc.getTimeSpan()).collect(Collectors.toList()));
//		 }
////		 else if(/*休日*/) {
////			 timeSpans.addAll(offdayWorkTime.getWorkingTimes().stream().map(tc -> tc.getTimeSpan()).collect(Collectors.toList()));
////		 }
//		 else {
//			 throw new Exception("unknown day Atr:"+workType);
//		 }
		 timeSpans.stream().sorted((first,second) -> first.getStart().compareTo(second.getStart()));
		 return timeSpans.get(0).getStart();
	 }

}
