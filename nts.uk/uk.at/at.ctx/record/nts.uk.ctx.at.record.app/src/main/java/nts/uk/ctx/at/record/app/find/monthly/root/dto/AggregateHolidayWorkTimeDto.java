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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;

@Data
/** 集計休出時間 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateHolidayWorkTimeDto implements ItemConst, AttendanceItemDataGate {

	/** 休出枠NO */
	private int no;

	/** 休出時間 */
	private TimeMonthWithCalculationDto holidayWorkTime;

	/** 事前休出時間 */
	private int beforeHolidayWorkTime;

	/** 振替時間 */
	private TimeMonthWithCalculationDto transferTime;

	/** 法定内休出時間 */
	private int legalHolidayWorkTime;

	/** 法定内振替休出時間 */
	private int legalTransferHolidayWorkTime;

	/** フレックス内休出時間 */
	private int flexHolidayWorkTime;

	/** フレックス内振替休出時間 */
	private int flexTransferHolidayWorkTime;

	public AggregateHolidayWorkTime toDomain() {
		return AggregateHolidayWorkTime.of(new HolidayWorkFrameNo(no),
											holidayWorkTime == null ? new TimeMonthWithCalculation() : holidayWorkTime.toDomain(),
											new AttendanceTimeMonth(beforeHolidayWorkTime),
											transferTime == null ? new TimeMonthWithCalculation() : transferTime.toDomain(),
											new AttendanceTimeMonth(legalHolidayWorkTime),
											new AttendanceTimeMonth(legalTransferHolidayWorkTime),
											new AttendanceTimeMonth(flexHolidayWorkTime),
											new AttendanceTimeMonth(flexTransferHolidayWorkTime));
	}
	
	public static AggregateHolidayWorkTimeDto from(AggregateHolidayWorkTime domain) {
		AggregateHolidayWorkTimeDto dto = new AggregateHolidayWorkTimeDto();
		if(domain != null) {
			dto.setBeforeHolidayWorkTime(domain.getBeforeHolidayWorkTime() == null ? 0 : domain.getBeforeHolidayWorkTime().valueAsMinutes());
			dto.setNo(domain.getHolidayWorkFrameNo().v());
			dto.setHolidayWorkTime(TimeMonthWithCalculationDto.from(domain.getHolidayWorkTime()));
			dto.setLegalHolidayWorkTime(domain.getLegalHolidayWorkTime() == null ? 0 : domain.getLegalHolidayWorkTime().valueAsMinutes());
			dto.setLegalTransferHolidayWorkTime(domain.getLegalTransferHolidayWorkTime() == null ? 0 : domain.getLegalTransferHolidayWorkTime().valueAsMinutes());
			dto.setTransferTime(TimeMonthWithCalculationDto.from(domain.getTransferTime()));
			dto.setFlexHolidayWorkTime(domain.getFlexHolidayWorkTime().valueAsMinutes());
			dto.setFlexTransferHolidayWorkTime(domain.getFlexTransferHolidayWorkTime().valueAsMinutes());
		}
		return dto;
	}
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case BEFORE:
			return Optional.of(ItemValue.builder().value(beforeHolidayWorkTime).valueType(ValueType.TIME));
		case LEGAL + HOLIDAY_WORK:
			return Optional.of(ItemValue.builder().value(legalHolidayWorkTime).valueType(ValueType.TIME));
		case LEGAL + TRANSFER:
			return Optional.of(ItemValue.builder().value(legalTransferHolidayWorkTime).valueType(ValueType.TIME));
		case FLEX:
			return Optional.of(ItemValue.builder().value(flexHolidayWorkTime).valueType(ValueType.TIME));
		case FLEX + TRANSFER:
			return Optional.of(ItemValue.builder().value(flexTransferHolidayWorkTime).valueType(ValueType.TIME));
		default:
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case HOLIDAY_WORK:
		case TRANSFER:
			return new TimeMonthWithCalculationDto();
		default:
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case HOLIDAY_WORK:
			return Optional.ofNullable(holidayWorkTime);
		case TRANSFER:
			return Optional.ofNullable(transferTime);
		default:
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case BEFORE:
		case LEGAL + HOLIDAY_WORK:
		case LEGAL + TRANSFER:
		case FLEX:
		case FLEX + TRANSFER:
			return PropType.VALUE;
		default:
		}
		return PropType.OBJECT;
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case BEFORE:
			beforeHolidayWorkTime = value.valueOrDefault(0);
			break;
		case (LEGAL + HOLIDAY_WORK):
			legalHolidayWorkTime = value.valueOrDefault(0);
			break;
		case (LEGAL + TRANSFER):
			legalTransferHolidayWorkTime = value.valueOrDefault(0);
			break;
		case (FLEX):
			flexHolidayWorkTime = value.valueOrDefault(0);
			break;
		case (FLEX + TRANSFER):
			flexTransferHolidayWorkTime = value.valueOrDefault(0);
			break;
		default:
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case HOLIDAY_WORK:
			holidayWorkTime = (TimeMonthWithCalculationDto) value;
			break;
		case TRANSFER:
			transferTime = (TimeMonthWithCalculationDto) value;
			break;
		default:
		}
	}
}
