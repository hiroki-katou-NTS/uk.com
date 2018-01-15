/**
 * 2:59:46 PM Dec 4, 2017
 */
package nts.uk.ctx.at.record.app.find.workrecord.erroralarm.condition;

import java.util.List;

import lombok.Value;

/**
 * @author hungnm
 *
 */
@Value
public class AlarmCheckTargetConditionDto {
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
