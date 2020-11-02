package nts.uk.ctx.at.request.dom.application.overtime.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * Refactor5
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 時間帯
public class TimeZone {
	// NO
	private Integer frame;
	// 開始時刻
	private TimeWithDayAttr startTime;
	// 終了時刻
	private TimeWithDayAttr endTime;
}
