package nts.uk.ctx.at.function.dom.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * @author hungtt
 *
 */
// アラームチェック対象者の条件
@Getter
public class AlarmCheckTargetCondition extends DomainObject {

	private String id;
	
	// 勤務種別でしぼり込む
	private boolean filterByBusinessType;

	// 職位でしぼり込む
	private boolean filterByJobTitle;

	// 雇用でしぼり込む
	private boolean filterByEmployment;

	// 分類でしぼり込む
	private boolean filterByClassification;

	// 対象勤務種別
	private List<String> lstBusinessTypeCode = new ArrayList<>();

	// 対象職位
	private List<String> lstJobTitleId = new ArrayList<>();

	// 対象雇用
	private List<String> lstEmploymentCode = new ArrayList<>();

	// 対象分類
	private List<String> lstClassificationCode = new ArrayList<>();

	public AlarmCheckTargetCondition(String id, boolean filterByBusinessType, boolean filterByJobTitle, boolean filterByEmployment,
			boolean filterByClassification, List<String> lstBusinessTypeCode, List<String> lstJobTitleId,
			List<String> lstEmploymentCode, List<String> lstClassificationCode) {
		super();
		this.id = id;
		this.filterByBusinessType = filterByBusinessType;
		this.filterByJobTitle = filterByJobTitle;
		this.filterByEmployment = filterByEmployment;
		this.filterByClassification = filterByClassification;
		this.lstBusinessTypeCode = lstBusinessTypeCode;
		this.lstJobTitleId = lstJobTitleId;
		this.lstEmploymentCode = lstEmploymentCode;
		this.lstClassificationCode = lstClassificationCode;
	}
	
	public void changeState(AlarmCheckTargetCondition value) {
		this.filterByBusinessType = value.filterByBusinessType;
		this.filterByClassification = value.filterByClassification;
		this.filterByEmployment = value.filterByEmployment;
		this.filterByJobTitle = value.filterByJobTitle;
		this.lstBusinessTypeCode = value.lstBusinessTypeCode;
		this.lstClassificationCode = value.lstClassificationCode;
		this.lstEmploymentCode = value.lstEmploymentCode;
		this.lstJobTitleId = value.lstJobTitleId;
	}

}
