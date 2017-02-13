/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate;

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
public class UnemployeeInsuranceRateDto implements UnemployeeInsuranceRateSetMemento {

	/** The history insurance. */
	private HistoryUnemployeeInsuranceDto historyInsurance;

	// private MonthRange applyRange;

	/** The rate items. */
	private List<UnemployeeInsuranceRateItemDto> rateItems;

	/** The version. */
	private long version;

	@Override
	public void setHistoryId(String historyId) {
		if (this.historyInsurance == null)
			this.historyInsurance = new HistoryUnemployeeInsuranceDto();
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
			UnemployeeInsuranceRateItemDto dto = new UnemployeeInsuranceRateItemDto();
			unemployeeInsuranceRateItem.saveToMemento(dto);
			this.rateItems.add(dto);
		}
	}

	@Override
	public void setVersion(Long version) {
		// TODO Auto-generated method stub
		this.version = version;
	}


}
