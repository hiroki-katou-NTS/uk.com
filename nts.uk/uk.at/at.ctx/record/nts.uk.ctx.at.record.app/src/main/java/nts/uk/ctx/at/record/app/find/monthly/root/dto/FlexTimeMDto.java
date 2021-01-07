package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculationAndMinus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeCurrentMonth;

@Data
/** フレックス時間 */
@NoArgsConstructor
@AllArgsConstructor
public class FlexTimeMDto implements ItemConst, AttendanceItemDataGate {

	/** フレックス時間: 計算付き月間時間(マイナス有り) */
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A)
	private TimeMonthWithCalculationDto flexTime;

	/** 事前フレックス時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_B)
	private int beforeFlexTime;

	/** 法定外フレックス時間: 勤怠月間時間(マイナス有り) */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = ILLEGAL, layout = LAYOUT_C)
	private int illegalFlexTime;

	/** 法定内フレックス時間: 勤怠月間時間(マイナス有り) */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = LEGAL, layout = LAYOUT_D)
	private int legalFlexTime;

	public FlexTime toDomain() {
		return FlexTime.of(flexTime == null ? new TimeMonthWithCalculationAndMinus() : flexTime.toDomainWithMinus(),
				new AttendanceTimeMonth(beforeFlexTime),
				new AttendanceTimeMonthWithMinus(legalFlexTime),
				new AttendanceTimeMonthWithMinus(illegalFlexTime),
				new FlexTimeCurrentMonth());
	}
	
	public static FlexTimeMDto from(FlexTime domain) {
		FlexTimeMDto dto = new FlexTimeMDto();
		if(domain != null) {
			dto.setBeforeFlexTime(domain.getBeforeFlexTime() == null ? 0 : domain.getBeforeFlexTime().valueAsMinutes());
			dto.setFlexTime(TimeMonthWithCalculationDto.from(domain.getFlexTime()));
			dto.setIllegalFlexTime(domain.getIllegalFlexTime() == null ? 0 : domain.getIllegalFlexTime().valueAsMinutes());
			dto.setLegalFlexTime(domain.getLegalFlexTime() == null ? 0 : domain.getLegalFlexTime().valueAsMinutes());
		}
		return dto;
	}

	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case BEFORE:
			return Optional.of(ItemValue.builder().value(beforeFlexTime).valueType(ValueType.TIME));
		case ILLEGAL:
			return Optional.of(ItemValue.builder().value(illegalFlexTime).valueType(ValueType.TIME));
		case LEGAL:
			return Optional.of(ItemValue.builder().value(legalFlexTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}
	

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (path.equals(TIME)) {
			return new TimeMonthWithCalculationDto();
		}
		return null;
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		if (path.equals(TIME)) {
			return Optional.ofNullable(flexTime);
		}
		return Optional.empty();
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case BEFORE:
		case ILLEGAL:
		case LEGAL:
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
		case ILLEGAL:
			illegalFlexTime = value.valueOrDefault(0);
			break;
		case LEGAL:
			legalFlexTime = value.valueOrDefault(0);
			break;
		default:
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if (path.equals(TIME)) {
			flexTime = (TimeMonthWithCalculationDto) value;
		}
	}


}
