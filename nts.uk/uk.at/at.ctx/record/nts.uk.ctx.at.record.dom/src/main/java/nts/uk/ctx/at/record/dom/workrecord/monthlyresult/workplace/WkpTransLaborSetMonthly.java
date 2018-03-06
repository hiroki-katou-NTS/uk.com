/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetMonthlyCalTransLabor;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class WkpTransLaborSetMonthly.
 */
@Getter
// 変形労働職場別月別実績集計設定
public class WkpTransLaborSetMonthly extends AggregateRoot implements SetMonthlyCalTransLabor {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The workplace id. */
	/** 職場ID. */
	private WorkplaceId workplaceId;

	/** The legal aggr set of irg new. */
	/** 変形労働時間勤務の法定内集計設定. */
	private LegalAggrSetOfIrgNew legalAggrSetOfIrgNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetMonthlyCalTransLabor
	 * #getLegalAggrSetOfIrgNew()
	 */
	@Override
	public LegalAggrSetOfIrgNew getLegalAggrSetOfIrgNew() {
		return legalAggrSetOfIrgNew;
	}

	/**
	 * Instantiates a new wkp trans labor set monthly.
	 *
	 * @param memento
	 *            the memento
	 */
	public WkpTransLaborSetMonthly(WkpTransLaborSetMonthly memento) {
		this.companyId = memento.getCompanyId();
		this.workplaceId = memento.getWorkplaceId();
		this.legalAggrSetOfIrgNew = memento.getLegalAggrSetOfIrgNew();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WkpTransLaborSetMonthlySetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkplaceId(this.workplaceId);
		memento.setLegalAggrSetOfIrgNew(this.legalAggrSetOfIrgNew);
	}

}
