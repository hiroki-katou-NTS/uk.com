/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrecord.monthcal;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.export.SettlementPeriod;

 
/**
  * The Class SettlementPeriodOfIrgNew.
  */
@Getter
// 変形労働精算期間
public class SettlementPeriodOfIrgNew {

	// 精算期間
	private List<SettlementPeriod> settlementPeriods;
	
}

