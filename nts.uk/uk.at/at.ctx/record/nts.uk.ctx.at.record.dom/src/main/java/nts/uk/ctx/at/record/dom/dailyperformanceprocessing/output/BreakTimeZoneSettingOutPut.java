package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

/**
 * 
 * @author dungdt 入退門反映先
 */
@Getter
@Setter
@NoArgsConstructor
public class BreakTimeZoneSettingOutPut {
	// 時間帯
	private List<DeductionTime> lstTimezone = new ArrayList<>();
}
