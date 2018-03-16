/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthlyresult.employment;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment.EmpRegularSetMonthlyActualWorkSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentRegularSetMonthlyActualWorkDto.
 */
@Getter
public class EmploymentRegularSetMonthlyActualWorkDto implements EmpRegularSetMonthlyActualWorkSetMemento {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The employment code. */
	/** 雇用コード. */
	private EmploymentCode employmentCode;

	/** The legal aggr set of reg new. */
	/** 通常勤務の法定内集計設定. */
	private LegalAggrSetOfRegNew legalAggrSetOfRegNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment.
	 * EmploymentRegularSetMonthlyActualWorkSetMemento#setCompanyId(nts.uk.ctx.
	 * at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment.
	 * EmploymentRegularSetMonthlyActualWorkSetMemento#setEmploymentCode(nts.uk.
	 * ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.employmentCode = employmentCode;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment.
	 * EmploymentRegularSetMonthlyActualWorkSetMemento#setLegalAggrSetOfRegNew(
	 * nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew)
	 */
	@Override
	public void setLegalAggrSetOfRegNew(LegalAggrSetOfRegNew legalAggrSetOfRegNew) {
		this.legalAggrSetOfRegNew = legalAggrSetOfRegNew;

	}

}
