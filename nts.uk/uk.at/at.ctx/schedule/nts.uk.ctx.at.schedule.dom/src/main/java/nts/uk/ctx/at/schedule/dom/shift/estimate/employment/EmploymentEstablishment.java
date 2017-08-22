/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.employment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentEstablishment.
 */
// 雇用目安設定
@Getter
public class EmploymentEstablishment extends AggregateRoot{
	/** The company id. */
	//会社ID
	private CompanyId companyId;

	/** The target year. */
	// 対象年
	private Year targetYear;
	
	/** The advanced setting. */
	//詳細設定
	private EstimateDetailSetting advancedSetting;
	
	/** The employment code. */
	//雇用コード
	private EmploymentCode employmentCode;
	
	/**
	 * Instantiates a new employment establishment.
	 *
	 * @param memento the memento
	 */
	public EmploymentEstablishment(EmploymentEstablishmentGetMemento memento){
		this.companyId = memento.getCompanyId();
		this.targetYear = memento.getTargetYear();
		this.advancedSetting = memento.getAdvancedSetting();
		this.employmentCode = memento.getEmploymentCode();
	}
	
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EmploymentEstablishmentSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setTargetYear(this.targetYear);
		memento.setAdvancedSetting(this.advancedSetting);
		memento.setEmploymentCode(this.employmentCode);
	}
}
