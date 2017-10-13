/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrule.closure;

import java.util.List;

import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KclmtClosure;

/**
 * The Class JpaClosureGetMemento.
 */
public class JpaClosureGetMemento implements ClosureGetMemento{
	
	/** The Kclmt closure. */
	@Setter
	private KclmtClosure kclmtClosure;
	
	/**
	 * Instantiates a new jpa closure get memento.
	 *
	 * @param KclmtClosure the kclmt closure
	 */
	public JpaClosureGetMemento(KclmtClosure kclmtClosure) {
		this.kclmtClosure = kclmtClosure;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.kclmtClosure.getKclmtClosurePK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#getClosureId()
	 */
	@Override
	public Integer getClosureId() {
		return this.kclmtClosure.getKclmtClosurePK().getClosureId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#getUseClassification()
	 */
	@Override
	public UseClassification getUseClassification() {
		return UseClassification.valueOf(this.kclmtClosure.getUseClass());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#getMonth()
	 */
	@Override
	public CurrentMonth getClosureMonth() {
		return new CurrentMonth(this.kclmtClosure.getClosureMonth());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#getClosureHistories()
	 */
	@Override
	public List<ClosureHistory> getClosureHistories() {
		return null;
	}

}
