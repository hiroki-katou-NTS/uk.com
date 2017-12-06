package nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 計算用時間帯
 * 
 * @author trungtran
 *
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TimeSpanForCalc {
	/** 終了時刻 */
	TimeWithDayAttr end;

	/** 開始時刻 */
	TimeWithDayAttr start;
}
