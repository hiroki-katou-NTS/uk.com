package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find;

import java.util.List;

import lombok.Getter;

/**
 * @author hieunv
 *
 */
@Getter
public class AlarmCheckTargetConditionPubExport {
	/* 勤務種別でしぼり込む */
	private boolean filterByBusinessType;
	/* 職位でしぼり込む */
	private boolean filterByJobTitle;
	/* 雇用でしぼり込む */
	private boolean filterByEmployment;
	/* 分類でしぼり込む */
	private boolean filterByClassification;
	/* 対象勤務種別 */
	private List<String> lstBusinessType;
	/* 対象職位 */
	private List<String> lstJobTitle;
	/* 対象雇用 */
	private List<String> lstEmployment;
	/* 対象分類 */
	private List<String> lstClassification;
	
	public AlarmCheckTargetConditionPubExport(
			boolean filterByBusinessType,
			boolean filterByJobTitle,
			boolean filterByEmployment,
			boolean filterByClassification,
			List<String> lstBusinessType,
			List<String> lstJobTitle,
			List<String> lstEmployment,
			List<String> lstClassification) {
		this.filterByJobTitle = filterByJobTitle;
		this.filterByEmployment = filterByEmployment;
		this.filterByClassification = filterByClassification;
		this.lstBusinessType = lstBusinessType;
		this.lstJobTitle = lstJobTitle;
		this.lstEmployment = lstEmployment;
		this.lstClassification = lstClassification;
	}
	
}
