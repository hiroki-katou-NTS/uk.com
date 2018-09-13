package nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 特定日区分 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecificDateAttrDto implements ItemConst {

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = ATTRIBUTE)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int specificDate;

	private Integer no;
	
	@Override
	public SpecificDateAttrDto clone() {
		return new SpecificDateAttrDto(specificDate, no);
	}
}
