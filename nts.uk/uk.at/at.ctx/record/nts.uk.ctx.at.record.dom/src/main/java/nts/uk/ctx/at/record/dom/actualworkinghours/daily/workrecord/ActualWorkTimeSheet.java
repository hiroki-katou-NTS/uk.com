package nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;

/** 作業実績の時間帯 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActualWorkTimeSheet {
	
	/**　開始: 勤怠打刻(実打刻付き) */
	private TimeActualStamp start;
	
	/**　終了: 勤怠打刻(実打刻付き) */
	private TimeActualStamp end;

}
