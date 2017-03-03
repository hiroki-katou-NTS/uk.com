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
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItem;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QwtmtWagetableCertifyG;

/**
 * The Class JpaCertifyGroupSetMemento.
 */
public class JpaWageTableHistorySetMemento implements WageTableHistorySetMemento {

	/** The type value. */
	protected QwtmtWagetableCertifyG typeValue;

	/**
	 * Instantiates a new jpa certify group set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableHistorySetMemento(QwtmtWagetableCertifyG typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCode(WageTableCode code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHistoryId(String historyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setApplyRange(MonthRange applyRange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDemensionDetail(List<WageTableDemensionDetail> demensionDetail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValueItems(List<WageTableItem> elements) {
		// TODO Auto-generated method stub
		
	}

}
