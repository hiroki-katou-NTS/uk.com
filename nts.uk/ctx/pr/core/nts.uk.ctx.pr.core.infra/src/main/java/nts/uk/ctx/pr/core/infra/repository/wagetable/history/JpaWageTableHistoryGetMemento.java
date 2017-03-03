/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.history;

import java.util.List;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDemensionDetail;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItem;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QwtmtWagetableCertifyG;

/**
 * The Class JpaWageTableHistoryGetMemento.
 */
public class JpaWageTableHistoryGetMemento implements WageTableHistoryGetMemento {

	/** The type value. */
	protected QwtmtWagetableCertifyG typeValue;

	/**
	 * Instantiates a new jpa certify group get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableHistoryGetMemento(QwtmtWagetableCertifyG typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public CompanyCode getCompanyCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WageTableCode getCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHistoryId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonthRange getApplyRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WageTableItem> getValueItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WageTableDemensionDetail> getDemensionDetail() {
		// TODO Auto-generated method stub
		return null;
	}

}
