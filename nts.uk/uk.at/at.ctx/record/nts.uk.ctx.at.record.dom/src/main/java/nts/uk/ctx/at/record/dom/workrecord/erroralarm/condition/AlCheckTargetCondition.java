/**
 * 11:37:58 AM Nov 2, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.affiliationinformation.primitivevalue.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * @author hungnm
 *
 */
// アラームチェック対象者の条件
public class AlCheckTargetCondition extends DomainObject {

	// 勤務種別でしぼり込む
	private boolean filterByBusinessType;

	// 職位でしぼり込む
	private boolean filterByJobTitle;

	// 雇用でしぼり込む
	private boolean filterByEmployment;

	// 分類でしぼり込む
	private boolean filterByClassification;

	// 対象勤務種別
	private List<BusinessTypeCode> lstBusinessTypeCode;

	// 対象職位
	private List<String> lstJobTitleId;

	// 対象雇用
	private List<EmploymentCode> lstEmploymentCode;

	// 対象分類
	private List<ClassificationCode> lstClassificationCode;

	public AlCheckTargetCondition(boolean filterByBusinessType, boolean filterByJobTitle, boolean filterByEmployment,
			boolean filterByClassification, List<String> lstBusinessTypeCode, List<String> lstJobTitleId,
			List<String> lstEmploymentCode, List<String> lstClassificationCode) {
		super();
		this.filterByBusinessType = filterByBusinessType;
		this.filterByJobTitle = filterByJobTitle;
		this.filterByEmployment = filterByEmployment;
		this.filterByClassification = filterByClassification;
		this.lstBusinessTypeCode = lstBusinessTypeCode.stream().map((code) -> {
			return new BusinessTypeCode(code);
		}).collect(Collectors.toList());
		this.lstJobTitleId = lstJobTitleId;
		this.lstEmploymentCode = lstEmploymentCode.stream().map((code) -> {
			return new EmploymentCode(code);
		}).collect(Collectors.toList());
		this.lstClassificationCode = lstClassificationCode.stream().map((code) -> {
			return new ClassificationCode(code);
		}).collect(Collectors.toList());
	}

	public boolean getFilterByBusinessType() {
		return filterByBusinessType;
	}

	public boolean getFilterByJobTitle() {
		return filterByJobTitle;
	}

	public boolean getFilterByEmployment() {
		return filterByEmployment;
	}

	public boolean getFilterByClassification() {
		return filterByClassification;
	}

	public List<BusinessTypeCode> getLstBusinessTypeCode() {
		return lstBusinessTypeCode;
	}

	public List<String> getLstJobTitleId() {
		return lstJobTitleId;
	}
	public List<EmploymentCode> getLstEmploymentCode() {
		return lstEmploymentCode;
	}

	public List<ClassificationCode> getLstClassificationCode() {
		return lstClassificationCode;
	}

	public void clearDuplicate(){
		this.lstBusinessTypeCode = this.lstBusinessTypeCode.stream().distinct().collect(Collectors.toList());
		this.lstJobTitleId = this.lstJobTitleId.stream().distinct().collect(Collectors.toList());
		this.lstEmploymentCode = this.lstEmploymentCode.stream().distinct().collect(Collectors.toList());
		this.lstClassificationCode = this.lstClassificationCode.stream().distinct().collect(Collectors.toList());
	}
}
