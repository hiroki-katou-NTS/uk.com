package nts.uk.ctx.at.record.app.find.dailyperform.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;

@Data
/** 勤怠打刻(実打刻付き) */
@AllArgsConstructor
@NoArgsConstructor
public class WithActualTimeStampDto implements ItemConst, AttendanceItemDataGate {

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = STAMP)
	private TimeStampDto time;

	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = ACTUAL)
	private TimeStampDto actualTime;
	
	/** 打刻反映回数 */
	// @AttendanceItemLayout(layout = "C")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer numberOfReflectionStamp;
	
	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case STAMP:
			return Optional.ofNullable(time);
		case ACTUAL:
			return Optional.ofNullable(actualTime);
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case STAMP:
			time = (TimeStampDto) value;
			break;
		case ACTUAL:
			actualTime = (TimeStampDto) value;
			break;
		default:
			break;
		}
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case STAMP:
		case ACTUAL:
			return new TimeStampDto();
		default:
			return null;
		}
	}
	
	public static WithActualTimeStampDto toWithActualTimeStamp(TimeActualStamp stamp){
		return stamp == null ? null : new WithActualTimeStampDto(
											TimeStampDto.createTimeStamp(stamp.getStamp().orElse(null)), 
											TimeStampDto.createTimeStamp(stamp.getActualStamp().orElse(null)),
											stamp.getNumberOfReflectionStamp());
	}
	
	@Override
	public WithActualTimeStampDto clone(){
		return new WithActualTimeStampDto(time == null ? null : time.clone(), 
											actualTime == null ? null : actualTime.clone(), 
											numberOfReflectionStamp);
	}
	
	public TimeActualStamp toDomain(){
		return new TimeActualStamp(TimeStampDto.toDomain(actualTime), TimeStampDto.toDomain(time), numberOfReflectionStamp);
	}
	
}
