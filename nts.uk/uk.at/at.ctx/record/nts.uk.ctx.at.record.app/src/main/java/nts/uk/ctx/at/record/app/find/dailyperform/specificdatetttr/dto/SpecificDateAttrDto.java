package nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 特定日区分 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecificDateAttrDto {

	@AttendanceItemLayout(layout = "A", jpPropertyName = "特定日区分")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private int specificDate;

	private Integer itemNo;
}
