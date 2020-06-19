package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.worktime.enums.StampSourceInfo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 勤怠打刻
 *
 */
@Getter
@NoArgsConstructor
@Setter
public class WorkStampOutPut {

	/*
	 * 丸め後の時刻
	 */
	private TimeWithDayAttr AfterRoundingTime;
	
	/*
	 * 時刻
	 */
	private TimeWithDayAttr timeWithDay;
	
	/*
	 * 場所コード
	 */
	private WorkLocationCD locationCode;
	
	/*
	 * 打刻元情報
	 */
	private StampSourceInfo stampSourceInfo;
	
	public WorkStampOutPut(TimeWithDayAttr afterRoundingTime, TimeWithDayAttr timeWithDay, WorkLocationCD locationCode,
			StampSourceInfo stampSourceInfo) {
		super();
		AfterRoundingTime = afterRoundingTime;
		this.timeWithDay = timeWithDay;
		this.locationCode = locationCode;
		this.stampSourceInfo = stampSourceInfo;
	}
}
