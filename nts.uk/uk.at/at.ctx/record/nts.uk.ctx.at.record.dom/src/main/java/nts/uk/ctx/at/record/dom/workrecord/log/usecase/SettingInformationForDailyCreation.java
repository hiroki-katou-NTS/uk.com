/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.usecase;

import lombok.Getter;

/**
 * @author danpv
 *
 */
@Getter
public class SettingInformationForDailyCreation {

	/**
	 *	0 : もう一度作り直す 
	 *	1 : 一部修正
	 */
	private DailyRecreateClassification dailyRecreateClassification;
	
	/**
	 * check-boxes
	 */
	private PartResetClassification partResetClassification;

}
