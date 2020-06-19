package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.worktime.enums.StampSourceInfo;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * 
 * @author dungdt 
 * 入退門反映先
 */
@Getter
@Setter
@NoArgsConstructor
public class ReflectEntryGateOutput {
	// 場所コード
	private WorkLocationCD locationCode;
	// 打刻元情報
	private StampSourceInfo stampSourceInfo;
	// 時刻
	private TimeWithDayAttr timeOfDay;
}
