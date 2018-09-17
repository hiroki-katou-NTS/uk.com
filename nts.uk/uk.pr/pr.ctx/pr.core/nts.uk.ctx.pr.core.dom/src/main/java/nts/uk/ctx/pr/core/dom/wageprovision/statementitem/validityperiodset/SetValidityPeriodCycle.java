package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author thanh.tq 有効期間とサイクルの設定
 *
 */

@Getter
public class SetValidityPeriodCycle extends AggregateRoot {

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;
	/**
	 * サイクル設定
	 */
	private CycleSetting cycleSetting;

	/**
	 * 有効期間設定
	 */
	private ValidityPeriodSet validityPeriodSetting;

	public SetValidityPeriodCycle(String salaryItemId, int cycleSettingAtr, Integer january, Integer february, Integer march, Integer april,
			Integer may, Integer june, Integer july, Integer august, Integer september, Integer october, Integer november, Integer december,
			int periodAtr, Integer startYear, Integer endYear) {
		super();
		this.salaryItemId = salaryItemId;
		this.cycleSetting = new CycleSetting(cycleSettingAtr, january, february, march, april, may, june, july, august,
				september, october, november, december);
		this.validityPeriodSetting = new ValidityPeriodSet(periodAtr, startYear, endYear);
	}

}
