package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find;

import java.util.List;

import lombok.Value;

/**
 * @author hieunv
 *
 */
@Value
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
}
