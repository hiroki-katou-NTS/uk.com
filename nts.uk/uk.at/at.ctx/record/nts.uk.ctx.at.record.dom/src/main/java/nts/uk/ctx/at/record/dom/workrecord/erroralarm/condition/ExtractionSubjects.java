/**
 * 11:37:58 AM Nov 2, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.affiliationinformation.primitivevalue.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * @author hungnm
 *
 */
@Getter
public class ExtractionSubjects extends DomainObject {

	private Boolean filterByBusinessType;

	private Boolean filterByJobTitle;

	private Boolean filterByEmployment;

	private Boolean filterByClassification;

	private List<BusinessTypeCode> lstBusinessTypeCode;

	private List<String> lstJobTitleId;

	private List<EmploymentCode> lstEmploymentCode;

	private List<ClassificationCode> lstClassificationCode;

	private ExtractionSubjects(Boolean filterByBusinessType, Boolean filterByJobTitle, Boolean filterByEmployment,
			Boolean filterByClassification, List<BusinessTypeCode> lstBusinessTypeCode, List<String> lstJobTitleId,
			List<EmploymentCode> lstEmploymentCode, List<ClassificationCode> lstClassificationCode) {
		super();
		this.filterByBusinessType = filterByBusinessType;
		this.filterByJobTitle = filterByJobTitle;
		this.filterByEmployment = filterByEmployment;
		this.filterByClassification = filterByClassification;
		this.lstBusinessTypeCode = lstBusinessTypeCode;
		this.lstJobTitleId = lstJobTitleId;
		this.lstEmploymentCode = lstEmploymentCode;
		this.lstClassificationCode = lstClassificationCode;
	}

	public static ExtractionSubjects createFromJavaType(boolean filterByBusinessType, boolean filterByJobTitle,
			boolean filterByEmployment, boolean filterByClassification, List<String> lstBusinessTypeCode,
			List<String> lstJobTitleId, List<String> lstEmploymentCode, List<String> lstClassificationCode) {
		return new ExtractionSubjects(filterByBusinessType, filterByJobTitle, filterByEmployment,
				filterByClassification, lstBusinessTypeCode.stream().map((code) -> {
					return new BusinessTypeCode(code);
				}).collect(Collectors.toList()), lstJobTitleId, lstEmploymentCode.stream().map((code) -> {
					return new EmploymentCode(code);
				}).collect(Collectors.toList()), lstClassificationCode.stream().map((code) -> {
					return new ClassificationCode(code);
				}).collect(Collectors.toList()));
	}

}
