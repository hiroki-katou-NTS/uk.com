/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.closure;

import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureDate;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureId;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureName;
import nts.uk.ctx.at.record.dom.workrecord.closure.CompanyId;
import nts.uk.ctx.at.record.infra.entity.workrecord.closure.KclmtClosureHist;

/**
 * The Class JpaClosureHistoryGetMemento.
 */
public class JpaClosureHistoryGetMemento implements ClosureHistoryGetMemento{
	
	/** The Kclmt closure hist. */
	
	/**
	 * Sets the kclmt closure hist.
	 *
	 * @param KclmtClosureHist the new kclmt closure hist
	 */
	@Setter
	private KclmtClosureHist kclmtClosureHist;
	
	/**
	 * Instantiates a new jpa closure history get memento.
	 *
	 * @param KclmtClosureHist the kclmt closure hist
	 */
	public JpaClosureHistoryGetMemento(KclmtClosureHist kclmtClosureHist) {
		this.kclmtClosureHist = kclmtClosureHist;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryGetMemento#getCloseName()
	 */
	@Override
	public ClosureName getClosureName() {
		return new ClosureName(this.kclmtClosureHist.getName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryGetMemento#getClosureId()
	 */
	@Override
	public ClosureId getClosureId() {
		return ClosureId.valueOf(this.kclmtClosureHist.getKclmtClosureHistPK().getClosureId());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryGetMemento#getEndDate()
	 */
	@Override
	public YearMonth getEndDate() {
		return YearMonth.of(this.kclmtClosureHist.getEndYM());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryGetMemento#getClosureDate()
	 */
	@Override
	public ClosureDate getClosureDate() {
		return new ClosureDate(this.kclmtClosureHist.getCloseDay(),
			this.kclmtClosureHist.getIsLastDay() == 1);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryGetMemento#getStartDate()
	 */
	@Override
	public YearMonth getStartDate() {
		return YearMonth.of(this.kclmtClosureHist.getKclmtClosureHistPK().getStrYM());
	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.kclmtClosureHist.getKclmtClosureHistPK().getCid());
	}

}
