/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.employee;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class ShainDeforLaborMonthActCalSet.
 */
@Getter
// 変形労働社員別月別実績集計設定
public class ShaDeforLaborMonthActCalSet extends AggregateRoot
		implements DeforLaborMonthActCalSet {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The employee id. */
	// 社員ID
	private EmployeeId employeeId;

	/** The aggr setting. */
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
	 * Instantiates a new shain defor labor month act cal set.
	 *
	 * @param memento the memento
	 */
	public ShaDeforLaborMonthActCalSet(ShaDeforLaborMonthActCalSetGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employeeId = memento.getEmployeeId();
		this.aggrSetting = memento.getDeforAggrSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ShaDeforLaborMonthActCalSetSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setAggrSetting(this.aggrSetting);
	}

}
