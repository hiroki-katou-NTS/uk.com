package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
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
	private TimeChangeMeans stampSourceInfo;
	// 時刻
	private TimeWithDayAttr timeOfDay;
}
