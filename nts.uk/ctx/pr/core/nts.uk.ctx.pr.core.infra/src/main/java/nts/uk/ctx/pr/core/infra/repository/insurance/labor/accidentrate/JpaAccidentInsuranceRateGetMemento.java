/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.accidentrate;

import java.util.Set;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate.QismtWorkAccidentInsu;

/**
 * The Class JpaAccidentInsuranceRateGetMemento.
 */
public class JpaAccidentInsuranceRateGetMemento implements AccidentInsuranceRateGetMemento {

	/** The type value. */
	protected QismtWorkAccidentInsu typeValue;

	/**
	 * Instantiates a new jpa accident insurance rate get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaAccidentInsuranceRateGetMemento(QismtWorkAccidentInsu typeValue) {
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

}
