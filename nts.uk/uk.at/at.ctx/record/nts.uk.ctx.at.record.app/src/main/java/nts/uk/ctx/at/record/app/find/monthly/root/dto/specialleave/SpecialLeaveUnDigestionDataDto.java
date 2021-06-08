package nts.uk.ctx.at.record.app.find.monthly.root.dto.specialleave;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeavaRemainTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainDay;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUnDigestion;

/**
 * 特別休暇未消化数
 * 
 * @author thanh_nx
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialLeaveUnDigestionDataDto implements ItemConst, AttendanceItemDataGate {
	/**
	 * 日数
	 */
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	@AttendanceItemValue(type = ValueType.DAYS)
	private double days;
	/**
	 * 時間
	 */
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer times;

	public static SpecialLeaveUnDigestionDataDto from(SpecialLeaveUnDigestion dom) {
		return new SpecialLeaveUnDigestionDataDto(dom.getDays().v(), dom.getTimes().map(x -> x.v()).orElse(null));
	}

	public SpecialLeaveUnDigestion toDomain() {

		return new SpecialLeaveUnDigestion(new SpecialLeaveRemainDay(days),
				times == null ? Optional.empty() : Optional.of(new SpecialLeavaRemainTime(times)));
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case DAYS:
			return Optional.of(ItemValue.builder().value(days).valueType(ValueType.DAYS));
		case TIME:
			return Optional.of(ItemValue.builder().value(times).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case DAYS:
			days = value.valueOrDefault(0.0);
			break;
		case TIME:
			times = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}

	@Override
	public PropType typeOf(String path) {

		switch (path) {
		case DAYS:
		case TIME:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

}
