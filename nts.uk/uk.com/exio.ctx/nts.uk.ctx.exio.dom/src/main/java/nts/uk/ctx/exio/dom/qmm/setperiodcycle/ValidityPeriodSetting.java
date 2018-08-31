package nts.uk.ctx.exio.dom.qmm.setperiodcycle;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 
 * @author thanh.tq 有効期間設定
 *
 */

@Getter
public class ValidityPeriodSetting extends DomainObject {
	/**
	 * 有効期間設定区分
	 */
	private PeriodAtr periodAtr;

	/**
	 * 年期間
	 */
	private YearPeriod yearPeriod;

	public ValidityPeriodSetting(int periodAtr, YearPeriod yearPeriod) {
		super();
		this.periodAtr = EnumAdaptor.valueOf(periodAtr, PeriodAtr.class);
		this.yearPeriod = yearPeriod;
	}
}
