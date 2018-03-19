/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.employment;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpCalMonthlyFlexSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentCalMonthlyFlexDto.
 */
@Getter
public class EmploymentCalMonthlyFlexDto implements EmpCalMonthlyFlexSetMemento {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The employment code. */
	/** 雇用コード. */
	private EmploymentCode employmentCode;

	/** The aggr setting monthly of flx new. */
	/** フレックス時間勤務の月の集計設定. */
	private AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmploymentCalMonthlyFlexSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmploymentCalMonthlyFlexSetMemento#setEmploymentCode(nts.uk.ctx.at.shared
	 * .dom.vacation.setting.compensatoryleave.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.employmentCode = employmentCode;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmploymentCalMonthlyFlexSetMemento#setAggrSettingMonthlyOfFlxNew(nts.uk.
	 * ctx.at.record.dom.workrecord.monthcal.AggrSettingMonthlyOfFlxNew)
	 */
	@Override
	public void setAggrSettingMonthlyOfFlxNew(AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew) {
		this.aggrSettingMonthlyOfFlxNew = aggrSettingMonthlyOfFlxNew;

	}

}
