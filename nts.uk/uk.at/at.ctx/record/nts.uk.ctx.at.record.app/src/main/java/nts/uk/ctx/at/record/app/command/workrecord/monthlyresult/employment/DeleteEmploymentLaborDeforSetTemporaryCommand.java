/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employment;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment.EmpLaborDeforSetTemporaryGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class DeleteEmploymentLaborDeforSetTemporaryCommand.
 */
@Getter
@Setter
public class DeleteEmploymentLaborDeforSetTemporaryCommand implements EmpLaborDeforSetTemporaryGetMemento{
	
	/** The company id. */
	/* 会社ID. */
	private CompanyId companyId;

	/** The employment code. */
	
	/** The employment code. */
	/* 雇用コード. */
	private EmploymentCode employmentCode;

	/** The legal aggr set of irg new. */
	/* 集計設定. */
	private LegalAggrSetOfIrgNew legalAggrSetOfIrgNew;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment.EmploymentLaborDeforSetTemporaryGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment.EmploymentLaborDeforSetTemporaryGetMemento#getEmploymentCode()
	 */
	@Override
	public EmploymentCode getEmploymentCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment.EmploymentLaborDeforSetTemporaryGetMemento#getLegalAggrSetOfIrgNew()
	 */
	@Override
	public LegalAggrSetOfIrgNew getLegalAggrSetOfIrgNew() {
		// TODO Auto-generated method stub
		return null;
	}

}
