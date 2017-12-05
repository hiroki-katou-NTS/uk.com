package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;

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
	
	// 打刻反映時間帯
	private List<StampReflectTimezone> lstStampReflectTimezone;
	
	// 打刻取得範囲
	private TimeZoneOutput stampRange;
	
	// 臨時
	private TimeZoneOutput temporary;

}
