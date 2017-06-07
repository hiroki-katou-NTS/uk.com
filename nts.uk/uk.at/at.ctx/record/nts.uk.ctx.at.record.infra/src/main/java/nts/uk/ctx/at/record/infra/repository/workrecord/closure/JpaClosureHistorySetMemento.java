/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.closure;

import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.closure.CloseName;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureDate;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistorySetMemento;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureId;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureYearMonth;
import nts.uk.ctx.at.record.infra.entity.workrecord.closure.KclmtClosureHist;
import nts.uk.ctx.at.record.infra.entity.workrecord.closure.KclmtClosureHistPK;

/**
 * The Class JpaClosureHistorySetMemento.
 */
public class JpaClosureHistorySetMemento implements ClosureHistorySetMemento{
	
	/** The kclmt closure hist. */
	@Setter
	private KclmtClosureHist kclmtClosureHist;

	/**
	 * Instantiates a new jpa closure history set memento.
	 *
	 * @param kclmtClosureHist the kclmt closure hist
	 */
	public JpaClosureHistorySetMemento(KclmtClosureHist kclmtClosureHist) {
		this.kclmtClosureHist = kclmtClosureHist;
	}

	
	/**
	 * Sets the close name.
	 *
	 * @param closeName the new close name
	 */
	@Override
	public void setCloseName(CloseName closeName) {
		this.kclmtClosureHist.setName(closeName.v());
	}

	/**
	 * Sets the closure id.
	 *
	 * @param closureId the new closure id
	 */
	@Override
	public void setClosureId(ClosureId closureId) {
		KclmtClosureHistPK pk = new KclmtClosureHistPK();
		pk.setClosureId(String.valueOf(closureId.value));
		this.kclmtClosureHist.setKclmtClosureHistPK(pk);
	}

	/**
	 * Sets the end date.
	 *
	 * @param endDate the new end date
	 */
	@Override
	public void setEndDate(ClosureYearMonth endDate) {
		this.kclmtClosureHist.setEndD(endDate.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistorySetMemento#
	 * setClosureDate(nts.uk.ctx.at.record.dom.workrecord.closure.ClosureDate)
	 */
	@Override
	public void setClosureDate(ClosureDate closureDate) {
		this.kclmtClosureHist.setCloseDay(closureDate.getDay());
		if(closureDate.getLastDayOfMonth()){
			this.kclmtClosureHist.setIsLastDay(1);
		}
		else {
			this.kclmtClosureHist.setIsLastDay(0);
		}
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	@Override
	public void setStartDate(ClosureYearMonth startDate) {
		// TODO Auto-generated method stub
		
	}

}
