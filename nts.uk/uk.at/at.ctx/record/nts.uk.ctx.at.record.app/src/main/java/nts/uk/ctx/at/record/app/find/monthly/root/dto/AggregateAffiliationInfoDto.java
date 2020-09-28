package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.affiliation.AggregateAffiliationInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

@Data
/** 集計所属情報 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateAffiliationInfoDto implements ItemConst {

	/** 分類コード: 分類コード */
	@AttendanceItemValue
	@AttendanceItemLayout(jpPropertyName = CLASSIFICATION, layout = LAYOUT_A)
	private String classificationCode;

	/** 勤務種別コード: 勤務種別コード */
	@AttendanceItemValue
	@AttendanceItemLayout(jpPropertyName = BUSINESS_TYPE, layout = LAYOUT_B)
	private String businessTypeCode;

	/** 職位ID: 職位ID */
	@AttendanceItemValue
	@AttendanceItemLayout(jpPropertyName = JOB_TITLE, layout = LAYOUT_C)
	private String jobTitle;

	/** 職場ID: 職場ID (work place ID) */
	@AttendanceItemValue
	@AttendanceItemLayout(jpPropertyName = WORKPLACE, layout = LAYOUT_D)
	private String workPlaceCode;

	/** 雇用コード: 雇用コード */
	@AttendanceItemValue
	@AttendanceItemLayout(jpPropertyName = EMPLOYEMENT, layout = LAYOUT_E)
	private String employmentCode;

	public static AggregateAffiliationInfoDto from(AggregateAffiliationInfo domain) {
		AggregateAffiliationInfoDto dto = new AggregateAffiliationInfoDto();
		if (domain != null) {
			dto.setClassificationCode(domain.getClassCd() == null ? null : domain.getClassCd().v());
			dto.setEmploymentCode(domain.getEmploymentCd() == null ? null : domain.getEmploymentCd().v());
			dto.setJobTitle(domain.getJobTitleId() == null ? null : domain.getJobTitleId().v());
			dto.setWorkPlaceCode(domain.getWorkplaceId() == null ? null : domain.getWorkplaceId().v());
			dto.setBusinessTypeCode(domain.getBusinessTypeCd() == null ? null : domain.getBusinessTypeCd().v());
		}
		return dto;
	}

	public AggregateAffiliationInfo toDomain() {
		return AggregateAffiliationInfo.of(new EmploymentCode(employmentCode),
				new WorkplaceId(workPlaceCode),
				new JobTitleId(jobTitle),
				new ClassificationCode(classificationCode),
				new BusinessTypeCode(businessTypeCode));
	}
}
