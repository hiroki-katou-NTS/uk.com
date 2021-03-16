package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の休出時間 */
public class HolidayWorkTimeOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** 休出合計時間 */
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_A)
	private TimeMonthWithCalculationDto totalHolidayWorkTime;
	/** 事前休出時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_B)
	private int beforeHolidayWorkTime;
	/** 振替合計時間 */
	@AttendanceItemLayout(jpPropertyName = TRANSFER + TOTAL, layout = LAYOUT_C)
	private TimeMonthWithCalculationDto totalTransferTime;
	/** 集計休出時間 */
	@AttendanceItemLayout(jpPropertyName = AGGREGATE, layout = LAYOUT_D, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<AggregateHolidayWorkTimeDto> aggregateHolidayWorkTimeMap;

	public HolidayWorkTimeOfMonthly toDomain() {
		return HolidayWorkTimeOfMonthly.of(totalHolidayWorkTime == null ? new TimeMonthWithCalculation() : totalHolidayWorkTime.toDomain(),
											new AttendanceTimeMonth(beforeHolidayWorkTime),
											totalTransferTime == null ? new TimeMonthWithCalculation() : totalTransferTime.toDomain(),
											ConvertHelper.mapTo(aggregateHolidayWorkTimeMap, c -> c.toDomain()));
	}
	
	public static HolidayWorkTimeOfMonthlyDto from(HolidayWorkTimeOfMonthly domain) {
		HolidayWorkTimeOfMonthlyDto dto = new HolidayWorkTimeOfMonthlyDto();
		if(domain != null) {
			dto.setAggregateHolidayWorkTimeMap(ConvertHelper.mapTo(domain.getAggregateHolidayWorkTimeMap(), 
					c -> AggregateHolidayWorkTimeDto.from(c.getValue())));
			dto.setBeforeHolidayWorkTime(domain.getBeforeHolidayWorkTime() == null ? 0 : domain.getBeforeHolidayWorkTime().valueAsMinutes());
			dto.setTotalHolidayWorkTime(TimeMonthWithCalculationDto.from(domain.getTotalHolidayWorkTime()));
			dto.setTotalTransferTime(TimeMonthWithCalculationDto.from(domain.getTotalTransferTime()));
		}
		return dto;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		if (BEFORE.equals(path)) {
			return Optional.of(ItemValue.builder().value(beforeHolidayWorkTime).valueType(ValueType.TIME));
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case TOTAL:
		case (TRANSFER + TOTAL):
			return new TimeMonthWithCalculationDto();
		case AGGREGATE:
			return new AggregateHolidayWorkTimeDto();
		default:
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case TOTAL:
			return Optional.ofNullable(totalHolidayWorkTime);
		case (TRANSFER + TOTAL):
			return Optional.ofNullable(totalTransferTime);
		default:
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public int size(String path) {
		if(AGGREGATE.equals(path)){
			return 10;
		}
		return AttendanceItemDataGate.super.size(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case BEFORE:
			return PropType.VALUE;
		case AGGREGATE:
			return PropType.IDX_LIST;
		default:
			return PropType.OBJECT;
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if(AGGREGATE.equals(path)){
			return (List<T>) aggregateHolidayWorkTimeMap;
		}
		return AttendanceItemDataGate.super.gets(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		if (BEFORE.equals(path)) {
			beforeHolidayWorkTime = value.valueOrDefault(0);
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case TOTAL:
			totalHolidayWorkTime = (TimeMonthWithCalculationDto) value;
			break;
		case (TRANSFER + TOTAL):
			totalTransferTime = (TimeMonthWithCalculationDto) value;
			break;
		default:
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if(AGGREGATE.equals(path)){
			aggregateHolidayWorkTimeMap = (List<AggregateHolidayWorkTimeDto>) value;
		}
	}
}
