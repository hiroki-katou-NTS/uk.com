/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableName;
import nts.uk.ctx.pr.core.dom.wagetable.mode.DemensionalMode;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QwtmtWagetableCertifyG;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaWageTableHeadGetMemento.
 */
public class JpaWageTableHeadGetMemento implements WageTableHeadGetMemento {

	/** The type value. */
	protected QwtmtWagetableCertifyG typeValue;

	/**
	 * Instantiates a new jpa certify group get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableHeadGetMemento(QwtmtWagetableCertifyG typeValue) {
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
	public WageTableName getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DemensionalMode getDemensionSetting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Memo getMemo() {
		// TODO Auto-generated method stub
		return null;
	}

}
