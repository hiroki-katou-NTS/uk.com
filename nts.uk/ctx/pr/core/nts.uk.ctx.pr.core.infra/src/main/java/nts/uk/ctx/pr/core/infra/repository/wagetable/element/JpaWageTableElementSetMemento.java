/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.element;

import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.RefMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElementSetMemento;
import nts.uk.ctx.pr.core.infra.entity.wagetable.element.QwtmtWagetableElement;
import nts.uk.ctx.pr.core.infra.entity.wagetable.element.QwtmtWagetableElementPK;

/**
 * The Class JpaWageTableElementSetMemento.
 */
public class JpaWageTableElementSetMemento implements WageTableElementSetMemento {

	/** The type value. */
	protected QwtmtWagetableElement typeValue;

	/**
	 * Instantiates a new jpa wage table element set memento.
	 *
	 * @param companyCode
	 *            the company code
	 * @param wageTableCode
	 *            the wage table code
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableElementSetMemento(String companyCode, String wageTableCode,
			QwtmtWagetableElement typeValue) {
		QwtmtWagetableElementPK qwtmtWagetableElementPK = new QwtmtWagetableElementPK();
		qwtmtWagetableElementPK.setCcd(companyCode);
		qwtmtWagetableElementPK.setWageTableCd(wageTableCode);
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElementSetMemento#
	 * setDemensionNo(nts.uk.ctx.pr.core.dom.wagetable.DemensionNo)
	 */
	@Override
	public void setDemensionNo(DemensionNo demensionNo) {
		QwtmtWagetableElementPK qwtmtWagetableElementPK = new QwtmtWagetableElementPK();
		qwtmtWagetableElementPK.setDemensionNo(demensionNo.value);
		this.typeValue.setQwtmtWagetableElementPK(qwtmtWagetableElementPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElementSetMemento#
	 * setElementModeSetting(nts.uk.ctx.pr.core.dom.wagetable.element.
	 * ElementMode)
	 */
	@Override
	public void setElementModeSetting(ElementMode elementModeSetting) {
		this.typeValue.setDemensionType(elementModeSetting.getElementType().value);

		if (elementModeSetting.getElementType().isCodeMode) {
			this.typeValue.setDemensionRefNo(((RefMode) elementModeSetting).getRefNo().v());
		}

		if (elementModeSetting.getElementType().isRangeMode) {
			this.typeValue.setDemensionRefNo("000");
		}
	}

}
