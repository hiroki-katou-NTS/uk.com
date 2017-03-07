/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.history;

import java.math.BigDecimal;

import nts.uk.ctx.pr.core.dom.wagetable.ElementId;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemSetMemento;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableMny;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableMnyPK;

/**
 * The Class JpaWageTableItemSetMemento.
 */
public class JpaWageTableItemSetMemento implements WageTableItemSetMemento {

	/** The type value. */
	protected QwtmtWagetableMny typeValue;

	/**
	 * Instantiates a new jpa wage table item set memento.
	 *
	 * @param ccd
	 *            the ccd
	 * @param wageTableCd
	 *            the wage table cd
	 * @param histId
	 *            the hist id
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableItemSetMemento(String ccd, String wageTableCd, String histId,
			QwtmtWagetableMny typeValue) {
		this.typeValue = typeValue;
		QwtmtWagetableMnyPK qwtmtWagetableMnyPK = new QwtmtWagetableMnyPK();
		qwtmtWagetableMnyPK.setCcd(ccd);
		qwtmtWagetableMnyPK.setWageTableCd(wageTableCd);
		qwtmtWagetableMnyPK.setHistId(histId);
		this.typeValue.setQwtmtWagetableMnyPK(qwtmtWagetableMnyPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemSetMemento#
	 * setElement1Id(nts.uk.ctx.pr.core.dom.wagetable.ElementId)
	 */
	@Override
	public void setElement1Id(ElementId element1Id) {
		QwtmtWagetableMnyPK qwtmtWagetableMnyPK = this.typeValue.getQwtmtWagetableMnyPK();
		qwtmtWagetableMnyPK.setElement1Id(element1Id.v());
		this.typeValue.setQwtmtWagetableMnyPK(qwtmtWagetableMnyPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemSetMemento#
	 * setElement2Id(nts.uk.ctx.pr.core.dom.wagetable.ElementId)
	 */
	@Override
	public void setElement2Id(ElementId element2Id) {
		QwtmtWagetableMnyPK qwtmtWagetableMnyPK = this.typeValue.getQwtmtWagetableMnyPK();
		qwtmtWagetableMnyPK.setElement2Id(element2Id.v());
		this.typeValue.setQwtmtWagetableMnyPK(qwtmtWagetableMnyPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemSetMemento#
	 * setElement3Id(nts.uk.ctx.pr.core.dom.wagetable.ElementId)
	 */
	@Override
	public void setElement3Id(ElementId element3Id) {
		QwtmtWagetableMnyPK qwtmtWagetableMnyPK = this.typeValue.getQwtmtWagetableMnyPK();
		qwtmtWagetableMnyPK.setElement3Id(element3Id.v());
		this.typeValue.setQwtmtWagetableMnyPK(qwtmtWagetableMnyPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemSetMemento#
	 * setAmount(java.math.BigDecimal)
	 */
	@Override
	public void setAmount(BigDecimal amount) {
		this.typeValue.setValueMny(amount.longValue());
	}

}
