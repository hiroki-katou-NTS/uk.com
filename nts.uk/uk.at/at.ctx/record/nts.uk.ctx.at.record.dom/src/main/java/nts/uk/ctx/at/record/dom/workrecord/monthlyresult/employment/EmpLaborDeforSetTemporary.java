/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetMonthlyCalTransLabor;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentLaborDeforSetTemporary.
 */
@Getter
// * 変形労働雇用別月別実績集計設定.
public class EmpLaborDeforSetTemporary extends AggregateRoot implements SetMonthlyCalTransLabor {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The employment code. */
	/** 雇用コード. */
	private EmploymentCode employmentCode;

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
	 * Instantiates a new employment labor defor set temporary.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmpLaborDeforSetTemporary(EmpLaborDeforSetTemporary memento) {
		this.companyId = memento.getCompanyId();
		this.employmentCode = memento.getEmploymentCode();
		this.legalAggrSetOfIrgNew = memento.getLegalAggrSetOfIrgNew();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmpLaborDeforSetTemporarySetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employmentCode);
		memento.setLegalAggrSetOfIrgNew(this.legalAggrSetOfIrgNew);
	}

}
