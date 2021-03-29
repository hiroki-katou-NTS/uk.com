package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.holidaytime.HolidayTimeOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の勤務時間 */
public class HolidayTimeOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** 法定内休日時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = LEGAL, layout = LAYOUT_A)
	private int legalHolTime;
	
	/** 法定外休日時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = ILLEGAL, layout = LAYOUT_B)
	private int illegalHolTime;
	
	/** 法定外祝日休日時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = ILLEGAL + PUBLIC_HOLIDAY, layout = LAYOUT_C)
	private int illegalSpeHolTime;
	
	public static HolidayTimeOfMonthlyDto from(HolidayTimeOfMonthly domain) {
		HolidayTimeOfMonthlyDto dto = new HolidayTimeOfMonthlyDto();
		if(domain != null) {
			dto.setIllegalHolTime(domain.getIllegalHolidayTime() == null ? 0 : domain.getIllegalHolidayTime().valueAsMinutes());
			dto.setIllegalSpeHolTime(domain.getIllegalSpecialHolidayTime() == null ? 0 : domain.getIllegalSpecialHolidayTime().valueAsMinutes());
			dto.setLegalHolTime(domain.getLegalHolidayTime() == null ? 0 : domain.getLegalHolidayTime().valueAsMinutes());
		}
		return dto;
	}
	public HolidayTimeOfMonthly toDomain() {
		return HolidayTimeOfMonthly.of(toAttendanceTimeMonth(legalHolTime),
										toAttendanceTimeMonth(illegalHolTime),
										toAttendanceTimeMonth(illegalSpeHolTime));
	}

	private AttendanceTimeMonth toAttendanceTimeMonth(Integer time) {
		return new AttendanceTimeMonth(time);
	}
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case LEGAL:
			return Optional.of(ItemValue.builder().value(legalHolTime).valueType(ValueType.TIME));
		case ILLEGAL:
			return Optional.of(ItemValue.builder().value(illegalHolTime).valueType(ValueType.TIME));
		case (ILLEGAL + PUBLIC_HOLIDAY):
			return Optional.of(ItemValue.builder().value(illegalSpeHolTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case LEGAL:
		case ILLEGAL:
		case (ILLEGAL + PUBLIC_HOLIDAY):
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}
	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case LEGAL:
			legalHolTime = value.valueOrDefault(0); break;
		case ILLEGAL:
			illegalHolTime = value.valueOrDefault(0); break;
		case (ILLEGAL + PUBLIC_HOLIDAY):
			illegalSpeHolTime = value.valueOrDefault(0); break;
		default:
		}
	}
}
