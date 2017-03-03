/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableName;
import nts.uk.ctx.pr.core.dom.wagetable.mode.DemensionalMode;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QwtmtWagetableCertifyG;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaCertifyGroupSetMemento.
 */
public class JpaWageTableHeadSetMemento implements WageTableHeadSetMemento {

	/** The type value. */
	protected QwtmtWagetableCertifyG typeValue;

	/**
	 * Instantiates a new jpa certify group set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableHeadSetMemento(QwtmtWagetableCertifyG typeValue) {
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
	public void setName(WageTableName name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDemensionSetting(DemensionalMode demensionSetting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMemo(Memo memo) {
		// TODO Auto-generated method stub
		
	}
}
