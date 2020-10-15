package nts.uk.ctx.at.record.app.find.dailyperform.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
/** 勤怠打刻(実打刻付き) */
@AllArgsConstructor
@NoArgsConstructor
public class WithActualTimeStampDto implements ItemConst {

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = STAMP)
	private TimeStampDto time;

	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = ACTUAL)
	private TimeStampDto actualTime;
	
	/** 時間外時間 */
	private Integer overtime;
	
	/** 時間外深夜時間 */
	private Integer overLateNightTime;
	
	private Integer timeZoneStart;
	
	private Integer timeZoneEnd;
	
	/** 打刻反映回数 */
	// @AttendanceItemLayout(layout = "C")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer numberOfReflectionStamp;
	
	public static WithActualTimeStampDto toWithActualTimeStamp(TimeActualStamp stamp){
		return stamp == null ? null : new WithActualTimeStampDto(
											TimeStampDto.createTimeStamp(stamp.getStamp().orElse(null)), 
											TimeStampDto.createTimeStamp(stamp.getActualStamp().orElse(null)),
											stamp.getOvertimeDeclaration().map(c -> c.getOverTime().valueAsMinutes()).orElse(null),
											stamp.getOvertimeDeclaration().map(c -> c.getOverLateNightTime().valueAsMinutes()).orElse(null),
											stamp.getTimeVacation().map(c -> c.getStart().valueAsMinutes()).orElse(null),
											stamp.getTimeVacation().map(c -> c.getEnd().valueAsMinutes()).orElse(null),
											stamp.getNumberOfReflectionStamp());
	}
	
	@Override
	public WithActualTimeStampDto clone(){
		return new WithActualTimeStampDto(time == null ? null : time.clone(), 
											actualTime == null ? null : actualTime.clone(), 
											overtime, overLateNightTime, timeZoneStart, timeZoneEnd,
											numberOfReflectionStamp);
	}
	
	public TimeActualStamp toDomain(){
		return new TimeActualStamp(TimeStampDto.toDomain(actualTime), TimeStampDto.toDomain(time), numberOfReflectionStamp,
				overtime == null ? null : new OvertimeDeclaration(new AttendanceTime(overtime), new AttendanceTime(overLateNightTime)),
				timeZoneStart == null ? null : new TimeZone(new TimeWithDayAttr(timeZoneStart), new TimeWithDayAttr(timeZoneEnd)));
	}
	
}
