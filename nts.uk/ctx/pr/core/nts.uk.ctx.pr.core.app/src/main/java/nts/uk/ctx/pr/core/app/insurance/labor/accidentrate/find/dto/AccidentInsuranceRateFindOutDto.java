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
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessType;

/**
 * The Class AccidentInsuranceRateFindOutDto.
 */
@Data
public class AccidentInsuranceRateFindOutDto implements AccidentInsuranceRateSetMemento {

	/** The history insurance. */
	// historyId
	private HistoryAccidentInsuranceRateFindOutDto historyInsurance;

	/** The rate items. */
	private List<InsuBizRateItemFindOutDto> rateItems;

	public void setDomainTo(AccidentInsuranceRate domain) {
		domain.saveToMemento(this.historyInsurance);
	}

	public void setDtoTo(List<InsuranceBusinessType> lstDomain) {
		for (InsuranceBusinessType itemDomain : lstDomain) {
			for (InsuBizRateItemFindOutDto itemDto : this.rateItems) {
				if (itemDto.getInsuBizType() == itemDomain.getBizOrder().value) {
					itemDto.setInsuranceBusinessType(itemDomain.getBizName().v());
				}
			}
		}
	}

	@Override
	public void setHistoryId(String historyId) {
		// TODO Auto-generated method stub

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
	public void setRateItems(Set<InsuBizRateItem> items) {
		this.rateItems = new ArrayList<>();
		for (InsuBizRateItem insuBizRateItem : items) {
			InsuBizRateItemFindOutDto insuBizRateItemFindOutDto = new InsuBizRateItemFindOutDto(insuBizRateItem);
			this.rateItems.add(insuBizRateItemFindOutDto);
		}
	}

}
