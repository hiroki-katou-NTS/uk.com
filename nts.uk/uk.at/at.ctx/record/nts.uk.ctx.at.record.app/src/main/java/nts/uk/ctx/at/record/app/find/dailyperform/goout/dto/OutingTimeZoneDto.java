package nts.uk.ctx.at.record.app.find.dailyperform.goout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutingTimeZoneDto {

	private Integer workNo;

	@AttendanceItemLayout(layout = "A", jpPropertyName = "外出")
	private WithActualTimeStampDto outing;

	@AttendanceItemLayout(layout = "B", jpPropertyName = "戻り")
	private WithActualTimeStampDto comeBack;

	@AttendanceItemLayout(layout = "C", jpPropertyName = "外出理由")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private int reason;
	
	private int outTimeCalc;
	
	private int outTIme;
}
