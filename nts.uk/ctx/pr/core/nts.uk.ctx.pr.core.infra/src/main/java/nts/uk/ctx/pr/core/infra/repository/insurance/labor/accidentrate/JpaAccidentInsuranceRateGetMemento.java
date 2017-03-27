/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.accidentrate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsu;

/**
 * The Class JpaAccidentInsuranceRateGetMemento.
 */
public class JpaAccidentInsuranceRateGetMemento implements AccidentInsuranceRateGetMemento {

	/** The type value. */
	protected List<QismtWorkAccidentInsu> typeValue;

	/**
	 * Instantiates a new jpa accident insurance rate get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaAccidentInsuranceRateGetMemento(List<QismtWorkAccidentInsu> typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		if (this.typeValue != null && this.typeValue.size() > 0) {
			return this.typeValue.get(0).getQismtWorkAccidentInsuPK().getHistId();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateGetMemento#getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		if (this.typeValue != null && this.typeValue.size() > 0) {
			return new CompanyCode(this.typeValue.get(0).getQismtWorkAccidentInsuPK().getCcd());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateGetMemento#getApplyRange()
	 */
	@Override
	public MonthRange getApplyRange() {
		if (this.typeValue != null && this.typeValue.size() > 0) {
			return MonthRange.range(YearMonth.of(this.typeValue.get(0).getStrYm()),
					YearMonth.of(this.typeValue.get(0).getEndYm()));
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.
	 * AccidentInsuranceRateGetMemento#getRateItems()
	 */
	@Override
	public Set<InsuBizRateItem> getRateItems() {
		Set<InsuBizRateItem> setInsuBizRateItem = new HashSet<>();
		for (QismtWorkAccidentInsu item : this.typeValue) {
			InsuBizRateItem itemInsuBizRateItem = new InsuBizRateItem();
			itemInsuBizRateItem
					.setInsuBizType(BusinessTypeEnum.valueOf(item.getQismtWorkAccidentInsuPK().getWaInsuCd()));
			itemInsuBizRateItem.setInsuRate(Double.valueOf(String.valueOf(item.getWaInsuRate())));
			itemInsuBizRateItem.setInsuRound(RoundingMethod.valueOf(item.getWaInsuRound()));
			setInsuBizRateItem.add(itemInsuBizRateItem);
		}
		return setInsuBizRateItem;
	}

}
