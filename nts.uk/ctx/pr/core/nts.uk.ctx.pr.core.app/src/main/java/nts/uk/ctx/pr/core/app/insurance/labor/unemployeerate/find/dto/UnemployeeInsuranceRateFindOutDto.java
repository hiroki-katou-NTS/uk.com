/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateSetMemento;

/**
 * The Class UnemployeeInsuranceRateDto.
 */
@Data
public class UnemployeeInsuranceRateFindOutDto implements UnemployeeInsuranceRateSetMemento {

	/** The history insurance. */
	private UnemployeeInsuranceHistoryFindOutDto historyInsurance;

	/** The rate items. */
	private List<UnemployeeInsuranceRateItemFindOutDto> rateItems;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateSetMemento#setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		if (this.historyInsurance == null)
			this.historyInsurance = new UnemployeeInsuranceHistoryFindOutDto();
		this.historyInsurance.setHistoryId(historyId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateSetMemento#setCompanyCode(nts.uk.ctx.core.dom.
	 * company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// Do nothing.

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateSetMemento#setApplyRange(nts.uk.ctx.pr.core.dom.
	 * insurance.MonthRange)
	 */
	@Override
	public void setApplyRange(MonthRange applyRange) {
		this.historyInsurance.setEndMonth(applyRange.getEndMonth().v());
		this.historyInsurance.setStartMonth(applyRange.getStartMonth().v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateSetMemento#setRateItems(java.util.Set)
	 */
	@Override
	public void setRateItems(Set<UnemployeeInsuranceRateItem> rateItems) {
		this.rateItems = new ArrayList<>();
		for (UnemployeeInsuranceRateItem unemployeeInsuranceRateItem : rateItems) {
			UnemployeeInsuranceRateItemFindOutDto dto = new UnemployeeInsuranceRateItemFindOutDto();
			unemployeeInsuranceRateItem.saveToMemento(dto);
			this.rateItems.add(dto);
		}
	}
}
