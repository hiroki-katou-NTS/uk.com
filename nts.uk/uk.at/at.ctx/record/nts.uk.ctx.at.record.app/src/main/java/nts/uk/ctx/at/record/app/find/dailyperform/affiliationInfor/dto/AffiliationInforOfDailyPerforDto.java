package nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

@Data
@AttendanceItemRoot(rootName = "日別実績の所属情報")
public class AffiliationInforOfDailyPerforDto implements ConvertibleAttendanceItem {

	private String employeeId;
	
	private GeneralDate baseDate; 
	
	@AttendanceItemLayout(layout = "A", jpPropertyName = "雇用コード")
	@AttendanceItemValue(itemId = 626)
	private String employmentCode;

	@AttendanceItemLayout(layout = "B", jpPropertyName = "職位ID")
	@AttendanceItemValue(itemId = 625)
	private String jobId;

	@AttendanceItemLayout(layout = "C", jpPropertyName = "職場ID")
	@AttendanceItemValue(itemId = 623)
	private String workplaceID;

	@AttendanceItemLayout(layout = "D", jpPropertyName = "分類コード")
	@AttendanceItemValue(itemId = 624)
	private String classificationCode;

	@AttendanceItemLayout(layout = "E", jpPropertyName = "加給コード")
	@AttendanceItemValue
	private String subscriptionCode;
}
