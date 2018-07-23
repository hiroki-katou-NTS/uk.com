package nts.uk.ctx.at.record.dom.workrecord.erroralarm.subjecttoextracted.collapsetargetperson;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class AlarmCheckTargetConRc {
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

	public AlarmCheckTargetConRc(String id, boolean filterByBusinessType, boolean filterByJobTitle,
			boolean filterByEmployment, boolean filterByClassification, List<String> lstBusinessTypeCode,
			List<String> lstJobTitleId, List<String> lstEmploymentCode, List<String> lstClassificationCode) {
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
	
	
}
