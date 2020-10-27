/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrule.closure;

import java.util.List;

import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KshmtClosure;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KshmtClosurePK;

/**
 * The Class JpaClosureSetMemento.
 */
public class JpaClosureSetMemento implements ClosureSetMemento {

	
	/** The kclmt closure. */
	@Setter
	private KshmtClosure kshmtClosure;

	/**
	 * Instantiates a new jpa closure set memento.
	 *
	 * @param kshmtClosure
	 *            the kclmt closure
	 */
	public JpaClosureSetMemento(KshmtClosure kshmtClosure) {
		if (kshmtClosure.getKshmtClosurePK() == null) {
			kshmtClosure.setKshmtClosurePK(new KshmtClosurePK());
		}
		this.kshmtClosure = kshmtClosure;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento#setCompanyId(
	 * nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.kshmtClosure.getKshmtClosurePK().setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento#setClosureId(
	 * java.lang.Integer)
	 */
	@Override
	public void setClosureId(ClosureId closureId) {
		this.kshmtClosure.getKshmtClosurePK().setClosureId(closureId.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento#
	 * setUseClassification(nts.uk.ctx.at.shared.dom.workrule.closure.
	 * UseClassification)
	 */
	@Override
	public void setUseClassification(UseClassification useClassification) {
		this.kshmtClosure.setUseClass(useClassification.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento#
	 * setClosureMonth(nts.uk.ctx.at.shared.dom.workrule.closure.ClosureMonth)
	 */
	@Override
	public void setClosureMonth(CurrentMonth month) {
		if (this.kshmtClosure.getUseClass() == 1) {
			this.kshmtClosure.setClosureMonth(month.getProcessingYm().v());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento#
	 * setClosureHistories(java.util.List)
	 */
	@Override
	public void setClosureHistories(List<ClosureHistory> closureHistories) {
		// No thing code
	}

}
