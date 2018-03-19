/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.CalcSettingOfIrregular;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriodOfIrg;

/**
 * The Class LegalAggrSetOfIrgNew.
 */
@Getter
// 変形労働時間勤務の法定内集計設定
public class DeforWorkTimeAggrSet extends DomainObject {

	/** The aggregate time set. */
	// 集計時間設定
	private ExcessOutsideTimeSetReg aggregateTimeSet;

	/** The excess outside time set. */
	// 時間外超過設定
	private ExcessOutsideTimeSetReg excessOutsideTimeSet;

	/** The calc set of irregular. */
	// 変形労働計算の設定
	private CalcSettingOfIrregular calcSetOfIrregular;

	/** The settlement period. */
	// 精算期間
	private SettlementPeriodOfIrg settlementPeriod;

	/**
	 * Gets the acqui of defor labor acc period.
	 *
	 * @return the acqui of defor labor acc period
	 */
	public SettlementPeriodOfIrg getDeforLaborAccPeriod() {
		return this.settlementPeriod;
	}

}
