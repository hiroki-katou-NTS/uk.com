/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetRegularActualWorkMonthly;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentRegularSetMonthlyActualWork.
 */
@Getter
//* 通常勤務雇用別月別実績集計設定.
public class EmploymentRegularSetMonthlyActualWork extends AggregateRoot implements SetRegularActualWorkMonthly{
	
	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;
	
	/** The employment code. */
	/** 雇用コード. */
	private EmploymentCode employmentCode;
	
	/** The legal aggr set of reg new. */
	/** 通常勤務の法定内集計設定. */
	private LegalAggrSetOfRegNew legalAggrSetOfRegNew;
	

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetRegularActualWorkMonthly#getLegalAggrSetOfRegNew()
	 */
	@Override
	public LegalAggrSetOfRegNew getLegalAggrSetOfRegNew() {
		return legalAggrSetOfRegNew;
	}
	
	/**
	 * Instantiates a new employment regular set monthly actual work.
	 *
	 * @param memento the memento
	 */
	public EmploymentRegularSetMonthlyActualWork (EmploymentRegularSetMonthlyActualWork memento) {
		this.companyId  = memento.getCompanyId();
		this.employmentCode = memento.getEmploymentCode();
		this.legalAggrSetOfRegNew = memento.getLegalAggrSetOfRegNew();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (EmploymentRegularSetMonthlyActualWorkSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employmentCode);
		memento.setLegalAggrSetOfRegNew(this.legalAggrSetOfRegNew);		
	}

	

	
}
