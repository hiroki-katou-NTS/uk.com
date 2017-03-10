/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessType;

/**
 * The Class AccidentInsuranceRateFindOutDto.
 */
@Data
public class AccidentInsuranceRateFindOutDto implements AccidentInsuranceRateSetMemento {

	/** The history insurance. */
	// historyId
	private AccidentInsuranceRateHistoryFindOutDto historyInsurance;

	/** The rate items. */
	private List<InsuBizRateItemFindOutDto> rateItems;

	/**
	 * Sets the dto to.
	 *
	 * @param lstDomain the new dto to
	 */
	public void setDtoTo(List<InsuranceBusinessType> lstDomain) {
		for (InsuranceBusinessType itemDomain : lstDomain) {
			for (InsuBizRateItemFindOutDto itemDto : this.rateItems) {
				if (itemDto.getInsuBizType() == itemDomain.getBizOrder().value) {
					itemDto.setInsuranceBusinessType(itemDomain.getBizName().v());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateSetMemento#setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		this.historyInsurance = new AccidentInsuranceRateHistoryFindOutDto();
		this.historyInsurance.setHistoryId(historyId);
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
		this.historyInsurance.setStartMonth(applyRange.getStartMonth().v());
		this.historyInsurance.setEndMonth(applyRange.getEndMonth().v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateSetMemento#setRateItems(java.util.Set)
	 */
	@Override
	public void setRateItems(Set<InsuBizRateItem> items) {
		this.rateItems = new ArrayList<>();
		for (InsuBizRateItem insuBizRateItem : items) {
			InsuBizRateItemFindOutDto insuBizRateItemFindOutDto = new InsuBizRateItemFindOutDto(insuBizRateItem);
			this.rateItems.add(insuBizRateItemFindOutDto);
		}
	}

}
