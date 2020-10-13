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
 * 出退勤打刻反映先
 */
@Getter
@Setter
@NoArgsConstructor
public class TimePrintDestinationOutput {
	//場所コード
	private WorkLocationCD locationCode;
	//打刻元情報
	private TimeChangeMeans stampSourceInfo;
	//時刻
	private TimeWithDayAttr timeOfDay;
	public TimePrintDestinationOutput(WorkLocationCD locationCode, TimeChangeMeans stampSourceInfo,
			TimeWithDayAttr timeOfDay) {
		super();
		this.locationCode = locationCode;
		this.stampSourceInfo = stampSourceInfo;
		this.timeOfDay = timeOfDay;
	}
	
}
