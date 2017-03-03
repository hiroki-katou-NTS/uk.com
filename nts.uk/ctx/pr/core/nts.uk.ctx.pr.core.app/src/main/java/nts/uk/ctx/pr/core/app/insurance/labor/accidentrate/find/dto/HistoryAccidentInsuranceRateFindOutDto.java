/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find.dto;

import java.util.Set;

import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;

/**
 * The Class HistoryAccidentInsuranceRateFindOutDto.
 */
@Data
public class HistoryAccidentInsuranceRateFindOutDto implements AccidentInsuranceRateSetMemento {

	/** The history id. */
	private String historyId;

	/** The start month rage. */
	private int startMonthRage;

	/** The end month rage. */
	private int endMonthRage;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateSetMemento#setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		this.historyId = historyId;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateSetMemento#setCompanyCode(nts.uk.ctx.core.dom.
	 * company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateSetMemento#setApplyRange(nts.uk.ctx.pr.core.dom.
	 * insurance.MonthRange)
	 */
	@Override
	public void setApplyRange(MonthRange applyRange) {
		this.startMonthRage = applyRange.getStartMonth().v();
		this.endMonthRage = applyRange.getEndMonth().v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateSetMemento#setRateItems(java.util.Set)
	 */
	@Override
	public void setRateItems(Set<InsuBizRateItem> items) {
		// TODO Auto-generated method stub

	}

}
