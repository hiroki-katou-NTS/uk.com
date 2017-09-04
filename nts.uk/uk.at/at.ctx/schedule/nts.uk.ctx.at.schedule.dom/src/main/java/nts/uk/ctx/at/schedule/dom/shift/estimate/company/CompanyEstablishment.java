/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.company;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CompanyEstablishment.
 */
// 全社目安設定

@Getter
public class CompanyEstablishment extends AggregateRoot{
	
	/** The company id. */
	//会社ID
	private CompanyId companyId;

	/** The target year. */
	// 対象年
	private Year targetYear;
	
	/** The advanced setting. */
	//詳細設定
	private EstimateDetailSetting advancedSetting;
	
	
	/**
	 * Instantiates a new company establishment.
	 *
	 * @param memento the memento
	 */
	public CompanyEstablishment(CompanyEstablishmentGetMemento memento){
		this.companyId = memento.getCompanyId();
		this.targetYear = memento.getTargetYear();
		this.advancedSetting = memento.getAdvancedSetting();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(CompanyEstablishmentSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setTargetYear(this.targetYear);
		memento.setAdvancedSetting(this.advancedSetting);
	}
	
}
