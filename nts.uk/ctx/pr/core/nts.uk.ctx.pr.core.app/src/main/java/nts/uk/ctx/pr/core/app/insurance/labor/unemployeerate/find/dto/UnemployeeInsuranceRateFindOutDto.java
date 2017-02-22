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
	private HistoryUnemployeeInsuranceFindOutDto historyInsurance;

	// private MonthRange applyRange;

	/** The rate items. */
	private List<UnemployeeInsuranceRateItemFindOutDto> rateItems;

	@Override
	public void setHistoryId(String historyId) {
		if (this.historyInsurance == null)
			this.historyInsurance = new HistoryUnemployeeInsuranceFindOutDto();
		this.historyInsurance.setHistoryId(historyId);

	}

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setApplyRange(MonthRange applyRange) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRateItems(Set<UnemployeeInsuranceRateItem> rateItems) {
		// TODO Auto-generated method stub
		this.rateItems = new ArrayList<>();
		for (UnemployeeInsuranceRateItem unemployeeInsuranceRateItem : rateItems) {
			UnemployeeInsuranceRateItemFindOutDto dto = new UnemployeeInsuranceRateItemFindOutDto();
			unemployeeInsuranceRateItem.saveToMemento(dto);
			this.rateItems.add(dto);
		}
	}
}
