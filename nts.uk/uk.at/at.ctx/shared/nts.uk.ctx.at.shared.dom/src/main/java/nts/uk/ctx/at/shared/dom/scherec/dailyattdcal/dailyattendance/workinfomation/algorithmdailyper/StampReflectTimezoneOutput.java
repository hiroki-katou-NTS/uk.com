package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 打刻反映時間帯 for KIF 001
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StampReflectTimezoneOutput {
	
	/** The work no. */
	// 勤務NO
	private WorkNo workNo;

	/** The classification. */
	// 出退勤区分
	private GoLeavingWorkAtr classification;

	/** The end time. */
	// 終了時刻
	private TimeWithDayAttr endTime;

	/** The start time. */
	// 開始時刻
	private TimeWithDayAttr startTime;

}
