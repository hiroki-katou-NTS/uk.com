package nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

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
