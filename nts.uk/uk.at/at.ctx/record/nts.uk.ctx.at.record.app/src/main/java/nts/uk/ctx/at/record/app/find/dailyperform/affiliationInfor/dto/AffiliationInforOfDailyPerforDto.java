package nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.item.ConvertibleAttendanceItem;

@Data
@AttendanceItemRoot(rootName = "日別実績の所属情報")
public class AffiliationInforOfDailyPerforDto implements ConvertibleAttendanceItem {

	private String employeeId;
	
	private GeneralDate baseDate; 
	
	@AttendanceItemLayout(layout = "A", jpPropertyName = "雇用コード")
	@AttendanceItemValue
	private String employmentCode;

	@AttendanceItemLayout(layout = "B", jpPropertyName = "職位ID")
	@AttendanceItemValue
	private String jobId;

	@AttendanceItemLayout(layout = "C", jpPropertyName = "職場ID")
	@AttendanceItemValue
	private String workplaceID;

	@AttendanceItemLayout(layout = "D", jpPropertyName = "分類コード")
	@AttendanceItemValue
	private String classificationCode;

//	@AttendanceItemLayout(layout = "E", jpPropertyName = "加給コード")
//	@AttendanceItemValue
	private String subscriptionCode;
	
	public static AffiliationInforOfDailyPerforDto getDto(AffiliationInforOfDailyPerfor domain){
		AffiliationInforOfDailyPerforDto dto = new AffiliationInforOfDailyPerforDto();
		if(domain != null){
			dto.setClassificationCode(domain.getClsCode().v());
			dto.setEmploymentCode(domain.getEmploymentCode().v());
			dto.setJobId(domain.getJobTitleID());
			dto.setSubscriptionCode(domain.getBonusPaySettingCode().v());
			dto.setWorkplaceID(domain.getWplID());
			dto.setBaseDate(domain.getYmd());
			dto.setEmployeeId(domain.getEmployeeId());
		}
		return dto;
	}
}
