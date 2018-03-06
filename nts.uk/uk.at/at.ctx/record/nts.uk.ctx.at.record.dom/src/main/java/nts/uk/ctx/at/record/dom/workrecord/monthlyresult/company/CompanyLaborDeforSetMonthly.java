/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetMonthlyCalTransLabor;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CompanyLaborDeforSetMonthly.
 */
@Getter
// * 変形労働会社別月別実績集計設定
public class CompanyLaborDeforSetMonthly extends AggregateRoot implements SetMonthlyCalTransLabor {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

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
	 * Instantiates a new company labor defor set monthly.
	 *
	 * @param memento
	 *            the memento
	 */
	public CompanyLaborDeforSetMonthly(CompanyLaborDeforSetMonthly memento) {
		this.companyId = memento.getCompanyId();
		this.legalAggrSetOfIrgNew = memento.getLegalAggrSetOfIrgNew();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(CompanyLaborDeforSetMonthlySetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setLegalAggrSetOfIrgNew(this.legalAggrSetOfIrgNew);
	}

}
