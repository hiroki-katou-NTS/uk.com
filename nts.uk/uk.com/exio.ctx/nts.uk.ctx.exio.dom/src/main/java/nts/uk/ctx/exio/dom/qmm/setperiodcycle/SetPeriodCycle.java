package nts.uk.ctx.exio.dom.qmm.setperiodcycle;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author thanh.tq 有効期間とサイクルの設定
 *
 */

@Getter
public class SetPeriodCycle extends AggregateRoot {

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
	private ValidityPeriodSetting validityPeriodSetting;

	public SetPeriodCycle(String salaryItemId, int cycleSettingAtr, int january, int february, int march, int april,
			int may, int june, int july, int august, int september, int october, int november, int december,
			int periodAtr, int endYear, int startYear) {
		super();
		this.salaryItemId = salaryItemId;
		this.cycleSetting = new CycleSetting(cycleSettingAtr, (new MonthlyList(january, february, march, april, may,
				june, july, august, september, october, november, december)));
		this.validityPeriodSetting = new ValidityPeriodSetting(periodAtr, (new YearPeriod(endYear, startYear)));
	}

}
