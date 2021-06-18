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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

@Data
/** 集計残業時間 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateOverTimeDto implements ItemConst, AttendanceItemDataGate {

	/** 残業枠NO */
	private int no;

	/** 残業時間 */
	private TimeMonthWithCalculationDto overTime;

	/** 事前残業時間 */
	private int beforeOverTime;

	/** 振替残業時間 */
	private TimeMonthWithCalculationDto transferOverTime;

	/** 法定内残業時間 */
	private int legalOverTime;

	/** 法定内振替残業時間 */
	private int legalTransferOverTime;

	/** フレックス内残業時間 */
	private int flexOverTime;

	/** フレックス内振替残業時間 */
	private int flexTransferOverTime;
	
	public AggregateOverTime toDomain() {
		return AggregateOverTime.of(new OverTimeFrameNo(no), 
									overTime == null ? new TimeMonthWithCalculation() : overTime.toDomain(),
									new AttendanceTimeMonth(beforeOverTime),
									transferOverTime == null ? new TimeMonthWithCalculation() : transferOverTime.toDomain(),
									new AttendanceTimeMonth(legalOverTime),
									new AttendanceTimeMonth(legalTransferOverTime),
									new AttendanceTimeMonth(flexOverTime),
									new AttendanceTimeMonth(flexTransferOverTime));
	}
	
	public static AggregateOverTimeDto from(AggregateOverTime domain) {
		AggregateOverTimeDto dto = new AggregateOverTimeDto();
		if(domain != null) {
			dto.setBeforeOverTime(domain.getBeforeOverTime() == null ? 0 : domain.getBeforeOverTime().valueAsMinutes());
			dto.setLegalOverTime(domain.getLegalOverTime() == null ? 0 : domain.getLegalOverTime().valueAsMinutes());
			dto.setLegalTransferOverTime(domain.getLegalTransferOverTime() == null ? 0 : domain.getLegalTransferOverTime().valueAsMinutes());
			dto.setOverTime(TimeMonthWithCalculationDto.from(domain.getOverTime()));
			dto.setNo(domain.getOverTimeFrameNo().v());
			dto.setTransferOverTime(TimeMonthWithCalculationDto.from(domain.getTransferOverTime()));
			dto.setFlexOverTime(domain.getFlexOverTime().valueAsMinutes());
			dto.setFlexTransferOverTime(domain.getFlexTransferOverTime().valueAsMinutes());
		}
		return dto;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case BEFORE:
			return Optional.of(ItemValue.builder().value(beforeOverTime).valueType(ValueType.TIME));
		case LEGAL:
			return Optional.of(ItemValue.builder().value(legalOverTime).valueType(ValueType.TIME));
		case (LEGAL + TRANSFER):
			return Optional.of(ItemValue.builder().value(legalTransferOverTime).valueType(ValueType.TIME));
		case FLEX:
			return Optional.of(ItemValue.builder().value(flexOverTime).valueType(ValueType.TIME));
		case (FLEX + TRANSFER):
			return Optional.of(ItemValue.builder().value(flexTransferOverTime).valueType(ValueType.TIME));
		default:
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case OVERTIME:
		case TRANSFER:
			return new TimeMonthWithCalculationDto();
		default:
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case OVERTIME:
			return Optional.ofNullable(overTime);
		case TRANSFER:
			return Optional.ofNullable(transferOverTime);
		default:
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case BEFORE:
		case LEGAL:
		case (LEGAL + TRANSFER):
		case FLEX:
		case (FLEX + TRANSFER):
			return PropType.VALUE;
		default:
		}
		return PropType.OBJECT;
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case BEFORE:
			beforeOverTime = value.valueOrDefault(0);
			break;
		case LEGAL:
			legalOverTime = value.valueOrDefault(0);
			break;
		case (LEGAL + TRANSFER):
			legalTransferOverTime = value.valueOrDefault(0);
			break;
		case FLEX:
			flexOverTime = value.valueOrDefault(0);
			break;
		case (FLEX + TRANSFER):
			flexTransferOverTime = value.valueOrDefault(0);
			break;
		default:
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case OVERTIME:
			overTime = (TimeMonthWithCalculationDto) value;
			break;
		case TRANSFER:
			transferOverTime = (TimeMonthWithCalculationDto) value;
			break;
		default:
		}
	}
}
