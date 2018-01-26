package nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionalItemValueDto {

	@AttendanceItemLayout(layout = "A", jpPropertyName = "値")
	@AttendanceItemValue
	private String value;

	private Integer itemNo;
	
	private boolean isTimeItem;
	
	private boolean isTimesItem;
	
	private boolean isAmountItem;
}
