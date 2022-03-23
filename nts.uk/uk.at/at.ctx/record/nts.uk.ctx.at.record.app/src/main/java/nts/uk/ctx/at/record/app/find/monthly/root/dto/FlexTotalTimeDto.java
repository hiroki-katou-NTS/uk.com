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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculationAndMinus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeTotalTimeMonth;

/** フレックス合計時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlexTotalTimeDto implements ItemConst, AttendanceItemDataGate {

	/** フレックス時間 */
	private TimeMonthWithCalculationDto flexTime;
	/** 法定内フレックス時間 */
	private int legalFlexTime;
	/** 法定外フレックス時間 */
	private int illegalFlexTime;
	
	public static FlexTotalTimeDto from (FlexTimeTotalTimeMonth domain) {
		
		if (domain == null) {
			return new FlexTotalTimeDto();
		}
		return new FlexTotalTimeDto(TimeMonthWithCalculationDto.from(domain.getFlexTime()), 
									domain.getLegalFlexTime().valueAsMinutes(), 
									domain.getIllegalFlexTime().valueAsMinutes());
	}
	
	public FlexTimeTotalTimeMonth domain() {
		
		return FlexTimeTotalTimeMonth.of(
				flexTime == null ? TimeMonthWithCalculationAndMinus.ofSameTime(0) : flexTime.toDomainWithMinus(), 
				new AttendanceTimeMonth(legalFlexTime),
				new AttendanceTimeMonth(illegalFlexTime)) ;
	}	
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
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
		switch (path) {
		case FLEX:
			return new TimeMonthWithCalculationDto();
		default:
			return null;
		}
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case FLEX:
			return Optional.ofNullable(flexTime);
		default:
			return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
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
		switch (path) {
		case FLEX:
			flexTime = (TimeMonthWithCalculationDto) value; break;
		default:
		}
	}

}
