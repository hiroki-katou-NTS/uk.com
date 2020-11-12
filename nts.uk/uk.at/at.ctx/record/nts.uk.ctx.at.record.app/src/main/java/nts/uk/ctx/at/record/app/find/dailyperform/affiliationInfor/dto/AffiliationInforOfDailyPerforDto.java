package nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.DAILY_AFFILIATION_INFO_NAME)
public class AffiliationInforOfDailyPerforDto extends AttendanceItemCommon {

	/***/
	private static final long serialVersionUID = 1L;
	
	private String employeeId;
	
	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate baseDate; 
	
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = EMPLOYEMENT)
	@AttendanceItemValue
	private String employmentCode;

	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = JOB_TITLE)
	@AttendanceItemValue
	private String jobId;

	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = WORKPLACE)
	@AttendanceItemValue
	private String workplaceID;

	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = CLASSIFICATION)
	@AttendanceItemValue
	private String classificationCode;

	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = RAISING_SALARY)
	@AttendanceItemValue
	private String subscriptionCode;

	@AttendanceItemLayout(layout = LAYOUT_F, jpPropertyName = BUSINESS_TYPE)
	@AttendanceItemValue
	private String businessTypeCode;
	
	public static AffiliationInforOfDailyPerforDto getDto(AffiliationInforOfDailyPerfor domain){
		AffiliationInforOfDailyPerforDto dto = new AffiliationInforOfDailyPerforDto();
		if(domain != null){
			dto.setClassificationCode(domain.getAffiliationInfor().getClsCode() == null ? null : domain.getAffiliationInfor().getClsCode().v());
			dto.setEmploymentCode(domain.getAffiliationInfor().getEmploymentCode() == null ? null : domain.getAffiliationInfor().getEmploymentCode().v());
			dto.setJobId(domain.getAffiliationInfor().getJobTitleID());
			dto.setSubscriptionCode(domain.getAffiliationInfor().getBonusPaySettingCode() == null ? null 
					: domain.getAffiliationInfor().getBonusPaySettingCode().v());
			dto.setWorkplaceID(domain.getAffiliationInfor().getWplID());
			dto.setBaseDate(domain.getYmd());
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setBusinessTypeCode(domain.getAffiliationInfor().getBusinessTypeCode().isPresent()?
					domain.getAffiliationInfor().getBusinessTypeCode().get().v():null);
			dto.exsistData();
			
		}
		return dto;
	}
	
	public static AffiliationInforOfDailyPerforDto getDto(String employeeID,GeneralDate ymd,AffiliationInforOfDailyAttd domain){
		AffiliationInforOfDailyPerforDto dto = new AffiliationInforOfDailyPerforDto();
		if(domain != null){
			dto.setClassificationCode(domain.getClsCode() == null ? null : domain.getClsCode().v());
			dto.setEmploymentCode(domain.getEmploymentCode() == null ? null : domain.getEmploymentCode().v());
			dto.setJobId(domain.getJobTitleID());
			dto.setSubscriptionCode(domain.getBonusPaySettingCode() == null ? null 
					: domain.getBonusPaySettingCode().v());
			dto.setWorkplaceID(domain.getWplID());
			dto.setBaseDate(ymd);
			dto.setEmployeeId(employeeID);
			dto.setBusinessTypeCode(domain.getBusinessTypeCode() != null && domain.getBusinessTypeCode().isPresent()?
					domain.getBusinessTypeCode().get().v():null);
			dto.exsistData();
		}
		return dto;
	}
	
	@Override
	public AffiliationInforOfDailyPerforDto clone(){
		AffiliationInforOfDailyPerforDto dto = new AffiliationInforOfDailyPerforDto();
		dto.setClassificationCode(classificationCode);
		dto.setEmploymentCode(employmentCode);
		dto.setJobId(jobId);
		dto.setSubscriptionCode(subscriptionCode);
		dto.setWorkplaceID(workplaceID);
		dto.setBaseDate(workingDate());
		dto.setEmployeeId(employeeId());
		dto.setEmploymentCode(employmentCode);
		dto.setBusinessTypeCode(businessTypeCode);
		if(this.isHaveData()){
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
	public AffiliationInforOfDailyAttd toDomain(String employeeId, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		if (employeeId == null) {
			employeeId = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		AffiliationInforOfDailyPerfor domain = new AffiliationInforOfDailyPerfor(new EmploymentCode(this.employmentCode), 
												employeeId, this.jobId, this.workplaceID, date,
												new ClassificationCode(this.classificationCode),
												this.subscriptionCode ==null?null:new BonusPaySettingCode(this.subscriptionCode),
												this.businessTypeCode ==null?null:new BusinessTypeCode(this.businessTypeCode));
		return domain.getAffiliationInfor();
	}
}
