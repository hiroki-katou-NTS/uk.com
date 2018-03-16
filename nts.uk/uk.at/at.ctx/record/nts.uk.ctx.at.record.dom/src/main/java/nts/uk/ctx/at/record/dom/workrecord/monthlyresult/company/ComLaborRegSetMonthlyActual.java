/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetRegularActualWorkMonthly;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CompanyLaborRegSetMonthlyActual.
 */
@Getter
// 通常勤務労働会社別月別実績集計設定.
public class ComLaborRegSetMonthlyActual extends AggregateRoot implements SetRegularActualWorkMonthly {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The legal aggr set of reg new. */
	// 通常勤務の法定内集計設定
	private LegalAggrSetOfRegNew legalAggrSetOfRegNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.
	 * SetRegularActualWorkMonthly#getLegalAggrSetOfRegNew()
	 */
	@Override
	public LegalAggrSetOfRegNew getLegalAggrSetOfRegNew() {
		return legalAggrSetOfRegNew;
	}

	/**
	 * Instantiates a new company labor reg set monthly actual.
	 *
	 * @param memento
	 *            the memento
	 */
	public ComLaborRegSetMonthlyActual(ComLaborRegSetMonthlyActual memento) {
		this.companyId = memento.getCompanyId();
		this.legalAggrSetOfRegNew = memento.getLegalAggrSetOfRegNew();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ComLaborRegSetMonthlyActualSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setLegalAggrSetOfRegNew(this.legalAggrSetOfRegNew);
	}
}
