package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
/** 期間 */
@NoArgsConstructor
@AllArgsConstructor
public class DatePeriodDto {

	/** 開始日 */
	@AttendanceItemValue(type = ValueType.DATE)
	@AttendanceItemLayout(jpPropertyName = "開始日", layout = "A")
	private GeneralDate start;

	/** 終了日 */
	@AttendanceItemValue(type = ValueType.DATE)
	@AttendanceItemLayout(jpPropertyName = "終了日", layout = "B")
	private GeneralDate end;
}
