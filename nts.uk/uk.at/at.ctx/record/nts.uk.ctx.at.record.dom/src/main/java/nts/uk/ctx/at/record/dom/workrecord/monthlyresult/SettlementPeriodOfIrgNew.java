package nts.uk.ctx.at.record.dom.workrecord.monthlyresult;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriod;

/**
 * 変形労働精算期間
 */
@Getter
public class SettlementPeriodOfIrgNew {

	/** 精算期間 */
	private List<SettlementPeriod> settlementPeriods;
	
}

