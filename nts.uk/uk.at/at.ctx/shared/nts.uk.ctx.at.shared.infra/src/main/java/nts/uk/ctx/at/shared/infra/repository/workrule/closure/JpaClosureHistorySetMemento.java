/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrule.closure;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureName;
import nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KclmtClosureHist;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KclmtClosureHistPK;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * The Class JpaClosureHistorySetMemento.
 */
public class JpaClosureHistorySetMemento implements ClosureHistorySetMemento {

	/** The kclmt closure hist. */
	private KclmtClosureHist kclmtClosureHist;

	/**
	 * Instantiates a new jpa closure history set memento.
	 *
	 * @param kclmtClosureHist
	 *            the kclmt closure hist
	 */
	public JpaClosureHistorySetMemento(KclmtClosureHist kclmtClosureHist) {
		if (kclmtClosureHist.getKclmtClosureHistPK() == null) {
			kclmtClosureHist.setKclmtClosureHistPK(new KclmtClosureHistPK());
		}
		this.kclmtClosureHist = kclmtClosureHist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setClosureName(nts.uk.ctx.at.shared.dom.workrule.closure.ClosureName)
	 */
	@Override
	public void setClosureName(ClosureName closeName) {
		this.kclmtClosureHist.setName(closeName.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setClosureId(nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId)
	 */
	@Override
	public void setClosureId(ClosureId closureId) {
		this.kclmtClosureHist.getKclmtClosureHistPK().setClosureId(closureId.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setEndDate(nts.arc.time.YearMonth)
	 */
	@Override
	public void setEndDate(YearMonth endDate) {
		this.kclmtClosureHist.setEndYM(endDate.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setClosureDate(nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate)
	 */
	@Override
	public void setClosureDate(ClosureDate closureDate) {
		this.kclmtClosureHist.setCloseDay(closureDate.getClosureDay().v());
		if (closureDate.getLastDayOfMonth()) {
			this.kclmtClosureHist.setIsLastDay(1);
			return;
		}
		this.kclmtClosureHist.setIsLastDay(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setStartDate(nts.arc.time.YearMonth)
	 */
	@Override
	public void setStartDate(YearMonth startDate) {
		this.kclmtClosureHist.getKclmtClosureHistPK().setStrYM(startDate.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.kclmtClosureHist.getKclmtClosureHistPK().setCid(companyId.v());
	}

}
