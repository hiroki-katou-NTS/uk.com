/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.personal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;

/**
 * The Class PersonEstablishment.
 */
// 個人目安設定
@Getter
public class PersonalEstablishment extends AggregateRoot{
	
	/** The employee id. */
	//社員ID
	private String employeeId;

	/** The target year. */
	// 対象年
	private Year targetYear;
	
	/** The advanced setting. */
	//詳細設定
	private EstimateDetailSetting advancedSetting;
	
	
	/**
	 * Instantiates a new personal establishment.
	 *
	 * @param memento the memento
	 */
	public PersonalEstablishment(PersonalEstablishmentGetMemento memento){
		this.employeeId = memento.getEmployeeId();
		this.targetYear = memento.getTargetYear();
		this.advancedSetting = memento.getAdvancedSetting();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PersonalEstablishmentSetMemento memento){
		memento.setEmployeeId(this.employeeId);
		memento.setTargetYear(this.targetYear);
		memento.setAdvancedSetting(this.advancedSetting);
	}
}
