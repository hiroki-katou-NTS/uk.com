/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.unemployeerate;

import java.util.Set;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateSetMemento;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.unemployeerate.QismtEmpInsuRate;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.unemployeerate.QismtEmpInsuRatePK;

/**
 * The Class JpaUnemployeeInsuranceRateSetMemento.
 */
public class JpaUnemployeeInsuranceRateSetMemento implements UnemployeeInsuranceRateSetMemento {

	/** The type value. */
	protected QismtEmpInsuRate typeValue;

	/**
	 * Instantiates a new jpa unemployee insurance rate set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaUnemployeeInsuranceRateSetMemento(QismtEmpInsuRate typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateSetMemento#setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		QismtEmpInsuRatePK pk = new QismtEmpInsuRatePK();
		pk.setHistId(historyId);
		this.typeValue.setQismtEmpInsuRatePK(pk);

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
		QismtEmpInsuRatePK pk = this.typeValue.getQismtEmpInsuRatePK();
		pk.setCcd(companyCode.v());
		this.typeValue.setQismtEmpInsuRatePK(pk);
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
		this.typeValue.setStrYm(applyRange.getEndMonth().v());
		this.typeValue.setEndYm(applyRange.getStartMonth().v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateSetMemento#setRateItems(java.util.Set)
	 */
	@Override
	public void setRateItems(Set<UnemployeeInsuranceRateItem> rateItems) {
		// TODO Auto-generated method stub

	}

}
