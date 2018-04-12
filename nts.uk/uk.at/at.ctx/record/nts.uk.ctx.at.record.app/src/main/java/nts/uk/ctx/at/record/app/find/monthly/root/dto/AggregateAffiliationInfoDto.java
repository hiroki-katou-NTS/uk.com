package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.affiliationinformation.primitivevalue.ClassificationCode;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AggregateAffiliationInfo;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.primitive.WorkplaceCode;

@Data
/** 集計所属情報 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateAffiliationInfoDto {

	/** 分類コード: 分類コード */
	@AttendanceItemValue
	@AttendanceItemLayout(jpPropertyName = "分類コード", layout = "A")
	private String classificationCode;

	/** 勤務種別コード: 勤務種別コード */
	@AttendanceItemValue
	@AttendanceItemLayout(jpPropertyName = "勤務種別コード", layout = "B")
	private String workTypeCode;

	/** 職位ID: 職位ID */
	@AttendanceItemValue
	@AttendanceItemLayout(jpPropertyName = "職位ID", layout = "C")
	private String jobTitle;

	/** 職場ID: 職場ID (work place ID) */
	@AttendanceItemValue
	@AttendanceItemLayout(jpPropertyName = "職場ID", layout = "D")
	private String workPlaceCode;

	/** 雇用コード: 雇用コード */
	@AttendanceItemValue
	@AttendanceItemLayout(jpPropertyName = "雇用コード", layout = "E")
	private String employmentCode;

	public static AggregateAffiliationInfoDto from(AggregateAffiliationInfo domain) {
		AggregateAffiliationInfoDto dto = new AggregateAffiliationInfoDto();
		if (domain != null) {
			dto.setClassificationCode(
					domain.getClassificationCode() == null ? null : domain.getClassificationCode().v());
			dto.setEmploymentCode(domain.getEmploymentCode() == null ? null : domain.getEmploymentCode().v());
			dto.setJobTitle(domain.getJobTitle() == null ? null : domain.getJobTitle().v());
			dto.setWorkPlaceCode(domain.getWorkPlaceCode() == null ? null : domain.getWorkPlaceCode().v());
			dto.setWorkTypeCode(domain.getWorkTypeCode() == null ? null : domain.getWorkTypeCode().v());
		}
		return dto;
	}

	public AggregateAffiliationInfo toDomain() {
		return new AggregateAffiliationInfo(
				classificationCode == null ? null : new ClassificationCode(classificationCode),
				workTypeCode == null ? null : new WorkTypeCode(workTypeCode),
				jobTitle == null ? null : new JobTitleId(jobTitle),
				workPlaceCode == null ? null : new WorkplaceCode(workPlaceCode),
				employmentCode == null ? null : new EmploymentCode(employmentCode));
	}
}
