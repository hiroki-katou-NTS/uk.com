/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.employment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentLaborDeforSetTemporary.
 */
@Getter
// 変形労働雇用別月別実績集計設定.
public class EmpDeforLaborMonthActCalSet extends AggregateRoot implements DeforLaborMonthActCalSet {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The employment code. */
	//  雇用コード
	private EmploymentCode employmentCode;

	/** The legal aggr set of irg new. */
	// 集計設定
	private DeforWorkTimeAggrSet aggrSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.monthcal.SetMonthlyCalTransLabor
	 * #getLegalAggrSetOfIrgNew()
	 */
	@Override
	public DeforWorkTimeAggrSet getAggregateSetting() {
		return aggrSetting;
	}

	/**
	 * Instantiates a new employment labor defor set temporary.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmpDeforLaborMonthActCalSet(EmpDeforLaborMonthActCalSetGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employmentCode = memento.getEmploymentCode();
		this.aggrSetting = memento.getDeforAggrSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmpDeforLaborMonthActCalSetSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employmentCode);
		memento.setAggrSetting(this.aggrSetting);
	}

}
