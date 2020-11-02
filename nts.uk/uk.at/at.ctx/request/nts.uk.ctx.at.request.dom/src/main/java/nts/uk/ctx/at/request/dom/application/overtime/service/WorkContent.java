package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;

/**
 * Refactor5
 * define in excel file
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 勤務内容
public class WorkContent {
	// 勤務種類コード
	private String workTypeCode;
	// 就業時間帯コード
	private String workTimeCode;
	// 時間帯 NO = 1 and NO = 2
	private List<TimeZone> timeZones;
	// 休憩時間帯 休憩枠NO = 1 ~ 10
	private List<BreakTimeSheet> breakTimes;
}
