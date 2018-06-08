package nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.affiliationinformation.primitivevalue.ClassificationCode;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceLayoutConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

@Data
@AttendanceItemRoot(rootName = AttendanceLayoutConst.DAILY_BUSINESS_TYPE_NAME)
public class BusinessTypeOfDailyPerforDto extends AttendanceItemCommon {

	private String employeeId;
	
	private GeneralDate baseDate; 
	
	@AttendanceItemLayout(layout = "A", jpPropertyName = "勤務種別コード")
	@AttendanceItemValue
	private String businessTypeCode;
	
	public static BusinessTypeOfDailyPerforDto getDto(WorkTypeOfDailyPerformance domain){
		BusinessTypeOfDailyPerforDto dto = new BusinessTypeOfDailyPerforDto();
		if(domain != null){
			dto.setBusinessTypeCode(domain.getWorkTypeCode() == null ? null : domain.getWorkTypeCode().v());
			dto.setBaseDate(domain.getDate());
			dto.setEmployeeId(domain.getEmployeeId());
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.baseDate;
	}

	@Override
	public WorkTypeOfDailyPerformance toDomain(String employeeId, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		if (employeeId == null) {
			employeeId = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		return new WorkTypeOfDailyPerformance(employeeId, date, this.businessTypeCode);
	}
}
