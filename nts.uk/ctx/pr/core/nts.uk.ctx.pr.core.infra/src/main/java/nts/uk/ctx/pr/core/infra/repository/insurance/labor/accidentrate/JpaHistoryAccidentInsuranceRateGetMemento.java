/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.accidentrate;

import java.util.Set;

import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsu;

/**
 * The Class JpaAccidentInsuranceRateSetMemento.
 */
public class JpaHistoryAccidentInsuranceRateGetMemento implements AccidentInsuranceRateGetMemento {

	/** The type value. */
	protected QismtWorkAccidentInsu typeValue;

	/**
	 * Instantiates a new jpa accident insurance rate set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaHistoryAccidentInsuranceRateGetMemento(QismtWorkAccidentInsu typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public String getHistoryId() {
		return this.typeValue.getQismtWorkAccidentInsuPK().getHistId();
	}

	@Override
	public CompanyCode getCompanyCode() {
		return new CompanyCode(this.typeValue.getQismtWorkAccidentInsuPK().getCcd());
	}

	@Override
	public MonthRange getApplyRange() {
		return MonthRange.range(YearMonth.of(this.typeValue.getStrYm()), YearMonth.of(this.typeValue.getEndYm()));
	}

	@Override
	public Set<InsuBizRateItem> getRateItems() {
		return null;
	}
}
