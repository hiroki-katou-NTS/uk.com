package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の予約 */
public class ReservationOfMonthlyDto implements ItemConst {

	@AttendanceItemLayout(jpPropertyName = AMOUNT, layout = LAYOUT_A)
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 注文金額: 注文金額 */
	private Integer reservationAmount;

	@AttendanceItemLayout(jpPropertyName = NUMBER, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 注文数: 注文数 */
	private Integer reservationNumber;
}
