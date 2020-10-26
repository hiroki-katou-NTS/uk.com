package nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto;

import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.DAILY_AFFILIATION_INFO_NAME)
public class AffiliationInforOfDailyPerforDto extends AttendanceItemCommon {

	@Override
	public String rootName() { return DAILY_AFFILIATION_INFO_NAME; }
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
	private String clsCode;

	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = RAISING_SALARY)
	@AttendanceItemValue
	private String subscriptionCode;

	@AttendanceItemLayout(layout = LAYOUT_F, jpPropertyName = BUSINESS_TYPE)
	@AttendanceItemValue
	private String businessTypeCode;
	
	public static AffiliationInforOfDailyPerforDto getDto(AffiliationInforOfDailyPerfor domain){
		AffiliationInforOfDailyPerforDto dto = new AffiliationInforOfDailyPerforDto();
		if(domain != null){
			dto.setClsCode(domain.getAffiliationInfor().getClsCode() == null ? null : domain.getAffiliationInfor().getClsCode().v());
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
			dto.setClsCode(domain.getClsCode() == null ? null : domain.getClsCode().v());
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
		dto.setClsCode(clsCode);
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
												new ClassificationCode(this.clsCode),
												this.subscriptionCode ==null?null:new BonusPaySettingCode(this.subscriptionCode),
												this.businessTypeCode ==null?null:new BusinessTypeCode(this.businessTypeCode));
		return domain.getAffiliationInfor();
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case EMPLOYEMENT:
			return Optional.of(ItemValue.builder().value(employmentCode).valueType(ValueType.CODE));
		case JOB_TITLE:
			return Optional.of(ItemValue.builder().value(jobId).valueType(ValueType.CODE));
		case WORKPLACE:
			return Optional.of(ItemValue.builder().value(workplaceID).valueType(ValueType.CODE));
		case CLASSIFICATION:
			return Optional.of(ItemValue.builder().value(clsCode).valueType(ValueType.CODE));
		case RAISING_SALARY:
			return Optional.of(ItemValue.builder().value(subscriptionCode).valueType(ValueType.CODE));
		case BUSINESS_TYPE:
			return Optional.of(ItemValue.builder().value(businessTypeCode).valueType(ValueType.CODE));
		default:
			return Optional.empty();
		}
	}

	@Override
	public boolean isRoot() { return true; }
	

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case EMPLOYEMENT:
			this.employmentCode = value.valueOrDefault(null);
			break;
		case JOB_TITLE:
			this.jobId = value.valueOrDefault(null);
			break;
		case WORKPLACE:
			this.workplaceID = value.valueOrDefault(null);
			break;
		case CLASSIFICATION:
			this.clsCode = value.valueOrDefault(null);
			break;
		case RAISING_SALARY:
			this.subscriptionCode = value.valueOrDefault(null);
			break;
		case BUSINESS_TYPE:
			this.businessTypeCode = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case EMPLOYEMENT:
		case JOB_TITLE:
		case WORKPLACE:
		case CLASSIFICATION:
		case RAISING_SALARY:
		case BUSINESS_TYPE:
			return PropType.VALUE;
		default:
			break;
		}
		return PropType.OBJECT;
	}
}
