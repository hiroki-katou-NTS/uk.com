package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
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
	@AttendanceItemLayout(jpPropertyName = NUMBER, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.NUMBER)
	/** 注文数: 注文数 */
	private int reservationNumber;

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case AMOUNT:
			return Optional.of(ItemValue.builder().value(reservationAmount).valueType(ValueType.AMOUNT));
		case NUMBER:
			return Optional.of(ItemValue.builder().value(reservationNumber).valueType(ValueType.NUMBER));
		default:
			return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case AMOUNT:
		case NUMBER:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case AMOUNT:
			reservationAmount = value.valueOrDefault(0); break;
		case NUMBER:
			reservationNumber = value.valueOrDefault(0); break;
		default:
		}
	}
	
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
}
