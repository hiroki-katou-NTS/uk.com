package nts.uk.ctx.at.record.app.find.dailyperform.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate.PropType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
/** 勤怠打刻 */
@AllArgsConstructor
@NoArgsConstructor
public class TimeStampDto implements ItemConst, AttendanceItemDataGate {

	/** 時刻 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = CLOCK)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private Integer timesOfDay;


	/** 場所コード */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = PLACE)
	@AttendanceItemValue
	private String placeCode;
	
	private int stampSourceInfo;
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case CLOCK:
			return Optional.of(ItemValue.builder().value(timesOfDay).valueType(ValueType.TIME_WITH_DAY));
		case PLACE:
			return Optional.of(ItemValue.builder().value(placeCode).valueType(ValueType.CODE));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case CLOCK:
			this.timesOfDay = value.valueOrDefault(null);
			break;
		case PLACE:
			this.placeCode = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case CLOCK:
		case PLACE:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}
	
	public static TimeStampDto createTimeStamp(WorkStamp c) {
		return  c == null || c.getTimeDay().getTimeWithDay()  == null || c.getTimeDay().getReasonTimeChange() ==null || c.getTimeDay().getReasonTimeChange().getTimeChangeMeans() ==null  || !c.getTimeDay().getTimeWithDay().isPresent()? null : new TimeStampDto(
					c.getTimeDay().getTimeWithDay().isPresent() && c.getTimeDay().getTimeWithDay() !=null ? c.getTimeDay().getTimeWithDay().get().valueAsMinutes():null,
												!c.getLocationCode().isPresent() ? null : c.getLocationCode().get().v(),
												c.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value);
	}
	
	@Override
	public TimeStampDto clone() {
		return new TimeStampDto(timesOfDay, placeCode, stampSourceInfo);
	}
	
	public static WorkStamp toDomain(TimeStampDto c) {
		return c == null || c.getTimesOfDay() == null ? null 
				: new WorkStamp(
				new TimeWithDayAttr(c.getTimesOfDay()),
				c.getPlaceCode() == null ? null : new WorkLocationCD(c.getPlaceCode()),
				c.stampInfo());
	}
	
	public TimeChangeMeans stampInfo(){
		switch (stampSourceInfo) {
		case 0:
			return TimeChangeMeans.REAL_STAMP;
		case 1:
			return TimeChangeMeans.APPLICATION;
		case 2:
			return TimeChangeMeans.DIRECT_BOUNCE;
		case 3:
			return TimeChangeMeans.DIRECT_BOUNCE_APPLICATION;
		case 4:
			return TimeChangeMeans.HAND_CORRECTION_PERSON;
		case 5:
			return TimeChangeMeans.HAND_CORRECTION_OTHERS;
		case 6:
			return TimeChangeMeans.AUTOMATIC_SET;
		case 7:
		default:
			return TimeChangeMeans.SPR_COOPERATION;
		}
	}
}
