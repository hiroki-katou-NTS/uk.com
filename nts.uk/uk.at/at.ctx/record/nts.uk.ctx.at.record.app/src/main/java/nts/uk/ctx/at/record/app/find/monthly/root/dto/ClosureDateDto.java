package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
/** 日付 */
@NoArgsConstructor
@AllArgsConstructor
public class ClosureDateDto {

	/** 日: int */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "日", layout = "A")
	private Integer closureDay;

	/** 末日とする: boolean */
	@AttendanceItemValue(type = ValueType.BOOLEAN)
	@AttendanceItemLayout(jpPropertyName = "末日とする", layout = "B")
	private Boolean lastDayOfMonth;
}
