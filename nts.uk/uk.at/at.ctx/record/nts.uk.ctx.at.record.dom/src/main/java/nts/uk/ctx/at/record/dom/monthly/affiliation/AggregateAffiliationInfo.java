package nts.uk.ctx.at.record.dom.monthly.affiliation;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.affiliationinformation.primitivevalue.ClassificationCode;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.primitive.WorkplaceCode;

@Getter
/** 集計所属情報 */
public class AggregateAffiliationInfo {

	/** 分類コード: 分類コード */
	private ClassificationCode classificationCode;
	
	/** 勤務種別コード: 勤務種別コード */
	private WorkTypeCode workTypeCode;
	
	/** 職位ID: 職位ID */
	private JobTitleId jobTitle;
	
	/** 職場ID: 職場ID (work place ID) */
	private WorkplaceCode workPlaceCode;
	
	/** 雇用コード: 雇用コード */
	private EmploymentCode employmentCode;

	public AggregateAffiliationInfo(ClassificationCode classificationCode, WorkTypeCode workTypeCode,
			JobTitleId jobTitle, WorkplaceCode workPlaceCode, EmploymentCode employmentCode) {
		super();
		this.classificationCode = classificationCode;
		this.workTypeCode = workTypeCode;
		this.jobTitle = jobTitle;
		this.workPlaceCode = workPlaceCode;
		this.employmentCode = employmentCode;
	}
}
