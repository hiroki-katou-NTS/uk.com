/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetRegularActualWorkMonthly;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class WkpRegularSetMonthlyActualWork.
 */
@Getter
// * 通常勤務職場別月別実績集計設定.
public class WkpRegularSetMonthlyActualWork extends AggregateRoot implements SetRegularActualWorkMonthly {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The workplace id. */
	/** 職場ID. */
	private WorkplaceId workplaceId;

	/** The legal aggr set of reg new. */
	/** 通常勤務の法定内集計設定. */
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
	 * Instantiates a new wkp regular set monthly actual work.
	 *
	 * @param memento
	 *            the memento
	 */
	public WkpRegularSetMonthlyActualWork(WkpRegularSetMonthlyActualWork memento) {
		this.companyId = memento.getCompanyId();
		this.workplaceId = memento.getWorkplaceId();
		this.legalAggrSetOfRegNew = memento.getLegalAggrSetOfRegNew();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WkpRegularSetMonthlyActualWorkSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkplaceId(this.workplaceId);
		memento.setLegalAggrSetOfRegNew(this.legalAggrSetOfRegNew);
	}

}
