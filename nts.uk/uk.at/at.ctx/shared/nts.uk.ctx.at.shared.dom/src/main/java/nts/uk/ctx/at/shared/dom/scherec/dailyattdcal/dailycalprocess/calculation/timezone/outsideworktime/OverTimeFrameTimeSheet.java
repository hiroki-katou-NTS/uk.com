package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 残業時間枠時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
@AllArgsConstructor
public class OverTimeFrameTimeSheet implements Cloneable{

	//時間帯
	private TimeSpanForDailyCalc timeSpan;
	
	//残業枠No
	private OverTimeFrameNo frameNo;
	
	//【追加予定】計算残業時間
	@Setter
	private AttendanceTime overTimeCalc;
	
	//【追加予定】計算振替残業時間
	@Setter
	private AttendanceTime tranferTimeCalc;

	public OverTimeFrameTimeSheet(TimeSpanForDailyCalc timeSpan, OverTimeFrameNo frameNo) {
		this.timeSpan = timeSpan;
		this.frameNo = frameNo;
		this.overTimeCalc = new AttendanceTime(0);
		this.tranferTimeCalc = new AttendanceTime(0);
	}
	
	@Override
	public OverTimeFrameTimeSheet clone() {
		return new OverTimeFrameTimeSheet(
				new TimeSpanForDailyCalc(new TimeWithDayAttr(timeSpan.getStart().v()),
						new TimeWithDayAttr(timeSpan.getEnd().v())),
				new OverTimeFrameNo(frameNo.v()), new AttendanceTime(overTimeCalc.v()), new AttendanceTime(tranferTimeCalc.v()));
	}
}
