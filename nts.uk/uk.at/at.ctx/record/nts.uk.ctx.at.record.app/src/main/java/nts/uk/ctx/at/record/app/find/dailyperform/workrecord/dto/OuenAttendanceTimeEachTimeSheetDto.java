package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.PremiumTimeDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenAttendanceTimeEachTimeSheet;

/** 時間帯別勤怠の時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OuenAttendanceTimeEachTimeSheetDto implements ItemConst, AttendanceItemDataGate {
	
	/** 総労働時間：勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TOTAL_LABOR + TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer totalTime;
	
	/** 休憩時間：勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = BREAK + TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer breakTime;
	
	/** 所定内時間：勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = WITHIN_STATUTORY + TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer withinTime;
	
	/** 割増時間：割増時間 */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = PREMIUM + TIME, 
			listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<PremiumTimeDto> premiumTimes;
	
	/** 医療時間：医療時間 */
	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = MEDICAL + TIME, 
			listMaxLength = 2, indexField = DEFAULT_ENUM_FIELD_NAME)
	private List<MedicalCareTimeEachTimeSheetDto> medicalCareTimes;
	
	public OuenAttendanceTimeEachTimeSheet toDomain() {
		return OuenAttendanceTimeEachTimeSheet.create(
				this.totalTime == null ? AttendanceTime.ZERO : new AttendanceTime(this.totalTime),
				this.breakTime == null ? AttendanceTime.ZERO : new AttendanceTime(this.breakTime),
				this.withinTime == null ? AttendanceTime.ZERO : new AttendanceTime(this.withinTime),
				ConvertHelper.mapTo(this.medicalCareTimes, c -> c.toDomain()),
				ConvertHelper.mapTo(this.premiumTimes, c -> c.toDomain()));
	}
	
	public static OuenAttendanceTimeEachTimeSheetDto valueOf(OuenAttendanceTimeEachTimeSheet domain) {
		return new OuenAttendanceTimeEachTimeSheetDto(
				domain.getTotalTime().valueAsMinutes(),
				domain.getBreakTime().valueAsMinutes(),
				domain.getWithinTime().valueAsMinutes(),
				ConvertHelper.mapTo(domain.getPremiumTime(), c -> PremiumTimeDto.valueOf(c)),
				ConvertHelper.mapTo(domain.getMedicalTime(), c -> MedicalCareTimeEachTimeSheetDto.valueOf(c)));
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case (TOTAL_LABOR + TIME):
		case (BREAK + TIME):
		case (WITHIN_STATUTORY + TIME):
			return PropType.VALUE;
		case (PREMIUM + TIME):
			return PropType.IDX_IN_IDX;
		case (MEDICAL + TIME):
			return PropType.ENUM_HAVE_IDX;
		default:
			return PropType.OBJECT;
		}
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case (PREMIUM + TIME):
			return new PremiumTimeDto();
		case (MEDICAL + TIME):
			return new MedicalCareTimeEachTimeSheetDto();
		default:
			return null;
		}
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case (TOTAL_LABOR + TIME):
			return Optional.of(ItemValue.builder().value(this.totalTime).valueType(ValueType.TIME));
		case (BREAK + TIME):
			return Optional.of(ItemValue.builder().value(this.breakTime).valueType(ValueType.TIME));
		case (WITHIN_STATUTORY + TIME):
			return Optional.of(ItemValue.builder().value(this.withinTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}
	
	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case (TOTAL_LABOR + TIME):
			this.totalTime = value.valueOrDefault(0);
			break;
		case (BREAK + TIME):
			this.breakTime = value.valueOrDefault(0);
			break;
		case (WITHIN_STATUTORY + TIME):
			this.withinTime = value.valueOrDefault(0);
			break;
		default:
			break;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		switch (path) {
		case (PREMIUM + TIME):
			return (List<T>) this.premiumTimes;
		case (MEDICAL + TIME):
			return (List<T>) this.medicalCareTimes;
		default:
			return new ArrayList<>();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		switch (path) {
		case (PREMIUM + TIME):
			this.premiumTimes = (List<PremiumTimeDto>) value;
			break;
		case (MEDICAL + TIME):
			this.medicalCareTimes = (List<MedicalCareTimeEachTimeSheetDto>) value;
			break;
		default:
			break;
		}
	}
	
	@Override
	public int size(String path) {
		switch (path) {
		case (PREMIUM + TIME):
			return 10;
		case (MEDICAL + TIME):
			return 2;
		default:
			return 0;
		}
	}
}
