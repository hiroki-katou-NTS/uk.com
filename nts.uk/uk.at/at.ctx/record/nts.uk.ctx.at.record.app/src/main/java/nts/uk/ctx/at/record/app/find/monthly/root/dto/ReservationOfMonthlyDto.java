package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.reservation.OrderAmountMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.reservation.ReservationOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の予約 */
public class ReservationOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	@AttendanceItemLayout(jpPropertyName = AMOUNT + LAYOUT_A, layout = LAYOUT_A)
	@AttendanceItemValue(type = ValueType.AMOUNT_NUM)
	/** 注文金額1 */
	private int amount1;

	@AttendanceItemLayout(jpPropertyName = AMOUNT + LAYOUT_B, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.AMOUNT_NUM)
	/** 注文金額2 */
	private int amount2;
	
	/** 注文数 */
	@AttendanceItemLayout(jpPropertyName = RESERVATION, layout = LAYOUT_C, indexField = DEFAULT_INDEX_FIELD_NAME, listMaxLength = 40)
	private List<ReservationNumberOfMonthlyDto> orders;

	public static ReservationOfMonthlyDto from(ReservationOfMonthly domain) {
		
		return new ReservationOfMonthlyDto(
				domain.getAmount1().v(), 
				domain.getAmount2().v(),
				ConvertHelper.mapTo(domain.getOrders(), o -> ReservationNumberOfMonthlyDto.from(o)));
	}
	
	public ReservationOfMonthly domain() {
		
		return ReservationOfMonthly.of(
				new OrderAmountMonthly(amount1), 
				new OrderAmountMonthly(amount2), 
				ConvertHelper.mapTo(orders, o -> o.toDomain()));
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case AMOUNT + LAYOUT_A:
			return Optional.of(ItemValue.builder().value(amount1).valueType(ValueType.AMOUNT));
		case AMOUNT + LAYOUT_B:
			return Optional.of(ItemValue.builder().value(amount2).valueType(ValueType.NUMBER));
		default:
			return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case AMOUNT + LAYOUT_A:
		case AMOUNT + LAYOUT_B:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case AMOUNT + LAYOUT_A:
			amount1 = value.valueOrDefault(0); break;
		case AMOUNT + LAYOUT_B:
			amount2 = value.valueOrDefault(0); break;
		default:
		}
	}
}
