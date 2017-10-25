/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.DailyRecreateClassification;

/**
 * @author danpv
 *
 * 日別作成用の設定情報
 */
@Getter
public class SettingInforForDailyCreation extends CalExeSettingInfor {

	/**
	 * 0 : もう一度作り直す 1 : 一部修正
	 */
	private DailyRecreateClassification dailyRecreateClassification;

	/**
	 * check-boxes
	 */
	private PartResetClassification partResetClassification;

	public SettingInforForDailyCreation() {
		super();
	}

}
