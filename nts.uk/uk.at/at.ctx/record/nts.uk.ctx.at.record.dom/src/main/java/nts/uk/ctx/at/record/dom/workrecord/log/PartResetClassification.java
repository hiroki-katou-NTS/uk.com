/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.Getter;

/**
 * @author danpv
 *
 */
@Getter
public class PartResetClassification {

	//マスタ再設定
	private boolean masterReset;

	//休業再設定
	private boolean holidayReset;

	// 就業時間帯再設定
	private boolean workingHoursReset;

	// 打刻のみ再度反映
	private boolean reflectsTheNumberOfFingerprintChecks;

	// 特定日区分再設定
	private boolean specificDateClassificationReset;

	// 申し送り時間再設定
	private boolean assignmentTimeReset;

	// 育児・介護短時間再設定
	private boolean childOrNurseCareTimeReset;

	// 計算区分再設定
	private boolean calculationClassificationReset;

}
