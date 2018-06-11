package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の予約 */
public class ReservationOfMonthlyDto {

	@AttendanceItemLayout(jpPropertyName = "注文金額", layout = "A")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 注文金額: 注文金額 */
	private int reservationAmount;

	@AttendanceItemLayout(jpPropertyName = "注文数", layout = "B")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 注文数: 注文数 */
	private int reservationNumber;
}
