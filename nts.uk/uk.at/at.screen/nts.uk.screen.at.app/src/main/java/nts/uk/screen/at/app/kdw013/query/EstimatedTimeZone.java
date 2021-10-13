package nts.uk.screen.at.app.kdw013.query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author sonnlb
 * 入力目安時間帯
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EstimatedTimeZone {
	// 年月日
	private GeneralDate ymd;

	// 開始時刻
	private TimeWithDayAttr startTime;

	// 終了時刻
	private TimeWithDayAttr endTime;

	// 休憩時間帯

	private List<BreakTimeSheet> breakTimeSheets;

	// List<計算用時間帯>

	private List<TimezoneUse> timezones;

	// List<時間帯>

	private List<TimeSpanForCalc> itemSpans;
}
