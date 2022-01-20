package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeCurrentMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeTotalTimeMonth;

@Data
/** フレックス時間 */
@NoArgsConstructor
@AllArgsConstructor
public class FlexTimeMDto implements ItemConst, AttendanceItemDataGate {

	/** フレックス時間: 計算付き月間時間(マイナス有り) */
	private FlexTotalTimeDto flexTime;

	/** 事前フレックス時間: 勤怠月間時間 */
	private int beforeFlexTime;
	
	private FlexCurrentMonthDto currentMonth;

	public FlexTime toDomain() {
		return FlexTime.of(flexTime == null ? new FlexTimeTotalTimeMonth() : flexTime.domain(),
				new AttendanceTimeMonth(beforeFlexTime),
				currentMonth == null ? new FlexTimeCurrentMonth() : currentMonth.toDomain());
	}
	
	public static FlexTimeMDto from(FlexTime domain) {
		FlexTimeMDto dto = new FlexTimeMDto();
		if(domain != null) {
			dto.setBeforeFlexTime(domain.getBeforeFlexTime() == null ? 0 : domain.getBeforeFlexTime().valueAsMinutes());
			dto.setFlexTime(FlexTotalTimeDto.from(domain.getFlexTime()));
			dto.setCurrentMonth(FlexCurrentMonthDto.from(domain.getFlexTimeCurrentMonth()));
		}
		return dto;
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case BEFORE:
			return Optional.of(ItemValue.builder().value(beforeFlexTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case CUR_MONTH:
			return new FlexCurrentMonthDto();
		case FLEX:
			return new FlexTotalTimeDto();
		default:
			return null;
		}
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case CUR_MONTH:
			return Optional.ofNullable(currentMonth);
		case FLEX:
			return Optional.ofNullable(flexTime);
		default:
			return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case BEFORE:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case BEFORE:
			beforeFlexTime = value.valueOrDefault(0);
			break;
		default:
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case CUR_MONTH:
			currentMonth = (FlexCurrentMonthDto) value; break;
		case FLEX:
			flexTime = (FlexTotalTimeDto) value; break;
		default:
		}
	}

}
