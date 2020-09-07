package nts.uk.ctx.at.shared.dom.dailyattdcal.workinfo.timereflectfromworkinfo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author nampt
 * 打刻反映範囲
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class StampReflectRangeOutput {
	
	// 外出
	private TimeZoneOutput goOut;
	
	// 出退勤
	private List<StampReflectTimezoneOutput> lstStampReflectTimezone;
	
	// 打刻取得範囲
	private TimeZoneOutput stampRange;
	
	// 臨時
	private TimeZoneOutput temporary;

}
