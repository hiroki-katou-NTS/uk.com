package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * KIF 001
 * 時間帯 - 打刻反映範囲
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeZoneOutput {
	
	/** The start. */
	//開始
	private TimeWithDayAttr start;
	
	/** The end. */
	//終了
	private TimeWithDayAttr end;

}
