/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.closure;

import java.util.List;

import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistory;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureMonth;
import nts.uk.ctx.at.record.dom.workrecord.closure.CompanyId;
import nts.uk.ctx.at.record.dom.workrecord.closure.UseClassification;
import nts.uk.ctx.at.record.infra.entity.workrecord.closure.KclmtClosure;

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
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.kclmtClosure.getKclmtClosurePK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureGetMemento#getClosureId()
	 */
	@Override
	public Integer getClosureId() {
		return this.kclmtClosure.getKclmtClosurePK().getClosureId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureGetMemento#getUseClassification()
	 */
	@Override
	public UseClassification getUseClassification() {
		return UseClassification.valueOf(this.kclmtClosure.getUseClass());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureGetMemento#getMonth()
	 */
	@Override
	public ClosureMonth getMonth() {
		return new ClosureMonth(this.kclmtClosure.getMonth());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureGetMemento#getClosureHistories()
	 */
	@Override
	public List<ClosureHistory> getClosureHistories() {
		return null;
	}

}
