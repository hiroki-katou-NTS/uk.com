/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.employment;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpLaborDeforSetTemporarySetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentLaborDeforSetTemporaryDto.
 */
@Getter
public class EmploymentLaborDeforSetTemporaryDto implements EmpLaborDeforSetTemporarySetMemento {

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
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmploymentLaborDeforSetTemporarySetMemento#setCompanyId(nts.uk.ctx.at.
	 * shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmploymentLaborDeforSetTemporarySetMemento#setEmploymentCode(nts.uk.ctx.
	 * at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.employmentCode = employmentCode;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmploymentLaborDeforSetTemporarySetMemento#setLegalAggrSetOfIrgNew(nts.uk
	 * .ctx.at.record.dom.workrecord.monthcal.LegalAggrSetOfIrgNew)
	 */
	@Override
	public void setLegalAggrSetOfIrgNew(LegalAggrSetOfIrgNew legalAggrSetOfIrgNew) {
		this.legalAggrSetOfIrgNew = legalAggrSetOfIrgNew;

	}

}
