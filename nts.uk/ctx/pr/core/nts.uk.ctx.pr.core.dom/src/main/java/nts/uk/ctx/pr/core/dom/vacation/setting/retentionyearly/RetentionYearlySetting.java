/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.retentionyearly;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class CumulationYearlySetting.
 */
@Getter
public class RetentionYearlySetting extends DomainObject{
	
	/** The company id. */
	private String companyId;
	
	/** The upper limit setting. */
	private UpperLimitSetting upperLimitSetting;
	
	/** The can add to cumulation yearly as normal work day. 積立年休を出勤日数として加算する*/
	// TODO: can't find this field in screen.
	private Boolean canAddToCumulationYearlyAsNormalWorkDay;
	
	/**
	 * Instantiates a new retention yearly setting.
	 *
	 * @param memento the memento
	 */
	public RetentionYearlySetting(RetentionYearlySettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.upperLimitSetting = memento.getUpperLimitSetting();
		this.canAddToCumulationYearlyAsNormalWorkDay = memento
				.getCanAddToCumulationYearlyAsNormalWorkDay();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(RetentionYearlySettingSetMemento memento){
		memento.setcanAddToCumulationYearlyAsNormalWorkDay(this.canAddToCumulationYearlyAsNormalWorkDay);
		memento.setCompanyId(this.companyId);
		memento.setUpperLimitSetting(this.upperLimitSetting);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RetentionYearlySetting other = (RetentionYearlySetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}
	
}
