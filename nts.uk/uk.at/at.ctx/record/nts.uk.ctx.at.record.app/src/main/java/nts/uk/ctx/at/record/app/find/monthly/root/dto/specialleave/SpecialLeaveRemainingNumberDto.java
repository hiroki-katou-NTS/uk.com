package nts.uk.ctx.at.record.app.find.monthly.root.dto.specialleave;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.TimeOfRemain;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainingDetail;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainingNumber;

/**
 * 特別休暇残数
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialLeaveRemainingNumberDto implements ItemConst, AttendanceItemDataGate {

	/** 合計残日数 */
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	@AttendanceItemValue(type = ValueType.DAYS)
	public double dayNumberOfRemain;

	/** 合計残時間 */
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.TIME)
	public Integer timeOfRemain;

	/** 明細 */
	private List<SpecialLeaveRemainingDetailDto> details;

	public static SpecialLeaveRemainingNumberDto from(SpecialLeaveRemainingNumber dom) {
		if (dom == null) {
			return null;
		}

		return new SpecialLeaveRemainingNumberDto(
				dom.getDayNumberOfRemain().v(), 
				dom.getTimeOfRemain().map(x -> x.v()).orElse(null),
				dom.getDetails().stream().map(x -> new SpecialLeaveRemainingDetailDto(x.getGrantDate(), x.getDays().v(),
																						x.getTime().map(y -> y.v()).orElse(null)))
										.collect(Collectors.toList()));

	}

	public SpecialLeaveRemainingNumber toDomain() {
		return new SpecialLeaveRemainingNumber(new DayNumberOfRemain(dayNumberOfRemain),
				Optional.ofNullable(timeOfRemain == null ? null : new TimeOfRemain(timeOfRemain)),
				details.stream().map(x -> SpecialLeaveRemainingDetail.of(x.getGrantDate(),
																		new DayNumberOfRemain(x.getDays()), 
																		Optional.ofNullable(x.getTime() == null ? null : new TimeOfRemain(x.getTime()))))
						.collect(Collectors.toList()));
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case DAYS:
			return Optional.of(ItemValue.builder().value(dayNumberOfRemain).valueType(ValueType.DAYS));
		case TIME:
			return Optional.of(ItemValue.builder().value(timeOfRemain).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case DAYS:
			dayNumberOfRemain = value.valueOrDefault(0);
			break;
		case TIME:
			timeOfRemain = value.valueOrDefault(null);
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
