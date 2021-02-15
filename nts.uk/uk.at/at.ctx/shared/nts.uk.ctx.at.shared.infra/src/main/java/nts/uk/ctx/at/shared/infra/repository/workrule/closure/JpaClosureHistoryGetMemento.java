/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrule.closure;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistoryGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureName;
import nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KclmtClosureHist;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * The Class JpaClosureHistoryGetMemento.
 */
public class JpaClosureHistoryGetMemento implements ClosureHistoryGetMemento {

	private KclmtClosureHist kclmtClosureHist;

	/**
	 * Instantiates a new jpa closure history get memento.
	 *
	 * @param kclmtClosureHist
	 *            the kclmt closure hist
	 */
	public JpaClosureHistoryGetMemento(KclmtClosureHist kclmtClosureHist) {
		this.kclmtClosureHist = kclmtClosureHist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistoryGetMemento#
	 * getClosureName()
	 */
	@Override
	public ClosureName getClosureName() {
		return new ClosureName(this.kclmtClosureHist.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistoryGetMemento#
	 * getClosureId()
	 */
	@Override
	public ClosureId getClosureId() {
		return ClosureId.valueOf(this.kclmtClosureHist.getKclmtClosureHistPK().getClosureId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistoryGetMemento#
	 * getEndDate()
	 */
	@Override
	public YearMonth getEndDate() {
		return YearMonth.of(this.kclmtClosureHist.getEndYM());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistoryGetMemento#
	 * getClosureDate()
	 */
	@Override
	public ClosureDate getClosureDate() {
		return new ClosureDate(this.kclmtClosureHist.getCloseDay(),
				this.kclmtClosureHist.getIsLastDay() == 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistoryGetMemento#
	 * getStartDate()
	 */
	@Override
	public YearMonth getStartDate() {
		return YearMonth.of(this.kclmtClosureHist.getKclmtClosureHistPK().getStrYM());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistoryGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.kclmtClosureHist.getKclmtClosureHistPK().getCid());
	}

}
