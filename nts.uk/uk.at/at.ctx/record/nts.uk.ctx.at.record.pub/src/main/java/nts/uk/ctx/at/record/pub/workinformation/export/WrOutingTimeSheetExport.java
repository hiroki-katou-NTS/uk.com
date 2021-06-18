package nts.uk.ctx.at.record.pub.workinformation.export;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

@Getter
@Setter
@NoArgsConstructor
public class WrOutingTimeSheetExport {
	/*
	 * 外出枠NO
	 */
	private OutingFrameNo outingFrameNo;
	
	// 外出: 勤怠打刻(実打刻付き) - primitive value
	private Optional<TimeActualStamp> goOut;
	
	/*
	 * 計算時間
	 */
	private AttendanceTime outingTimeCalculation;
	
	/*
	 * 外出時間
	 */
	private AttendanceTime outingTime;
	
	/*
	 * 外出理由
	 */
	private GoingOutReason reasonForGoOut;
	
	// 戻り: 勤怠打刻(実打刻付き) - primitive value
	private Optional<TimeActualStamp> comeBack;
}
