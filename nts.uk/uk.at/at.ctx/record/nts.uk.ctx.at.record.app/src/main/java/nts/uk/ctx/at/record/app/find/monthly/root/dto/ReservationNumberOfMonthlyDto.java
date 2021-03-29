package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate.PropType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.reservation.ReservationDetailOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の予約明細 */ 
public class ReservationNumberOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** 弁当メニュー枠番 */
	private int no;

	/** 注文数 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = NUMBER, layout = LAYOUT_A)
	private int orderNumber;
	
	public static ReservationNumberOfMonthlyDto from(ReservationDetailOfMonthly domain) {
		ReservationNumberOfMonthlyDto dto = new ReservationNumberOfMonthlyDto();
		if(domain != null) {
			dto.setOrderNumber(domain.getOrder().v());
			dto.setNo(domain.getFrameNo());
		}
		return dto;
	}
	
	public ReservationDetailOfMonthly toDomain(){
		return ReservationDetailOfMonthly.of(no, orderNumber);
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		if (path.equals(NUMBER)) {
			return Optional.of(ItemValue.builder().value(orderNumber).valueType(ValueType.NUMBER));
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		if (path.equals(NUMBER)) {
			return PropType.VALUE;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		if (path.equals(NUMBER)) {
			orderNumber = value.valueOrDefault(0);
		}
	}
}
