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
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KclmtClosure;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KclmtClosurePK;

/**
 * The Class JpaClosureSetMemento.
 */
public class JpaClosureSetMemento implements ClosureSetMemento {

	
	/** The kclmt closure. */
	@Setter
	private KclmtClosure kclmtClosure;

	/**
	 * Instantiates a new jpa closure set memento.
	 *
	 * @param kclmtClosure
	 *            the kclmt closure
	 */
	public JpaClosureSetMemento(KclmtClosure kclmtClosure) {
		if (kclmtClosure.getKclmtClosurePK() == null) {
			kclmtClosure.setKclmtClosurePK(new KclmtClosurePK());
		}
		this.kclmtClosure = kclmtClosure;
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
		this.kclmtClosure.getKclmtClosurePK().setCid(companyId.v());
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
		this.kclmtClosure.getKclmtClosurePK().setClosureId(closureId.value);
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
		this.kclmtClosure.setUseClass(useClassification.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento#
	 * setClosureMonth(nts.uk.ctx.at.shared.dom.workrule.closure.ClosureMonth)
	 */
	@Override
	public void setClosureMonth(CurrentMonth month) {
		if (this.kclmtClosure.getUseClass() == 1) {
			this.kclmtClosure.setClosureMonth(month.getProcessingYm().v());
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
