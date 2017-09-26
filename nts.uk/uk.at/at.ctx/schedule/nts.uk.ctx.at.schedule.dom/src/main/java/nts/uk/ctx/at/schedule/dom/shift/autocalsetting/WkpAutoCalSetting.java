/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class WkpAutoCalSetting.
 */

/**
 * Gets the rest time.
 *
 * @return the rest time
 */

/**
 * Gets the rest time.
 *
 * @return the rest time
 */
@Getter
public class WkpAutoCalSetting extends AggregateRoot {

	/** The company id. */
	//会社ID
	private CompanyId companyId;
	
	/** The wkp id. */
	//職場ID
	private WorkplaceId wkpId;

	/** The normal OT time. */
	//残業時間
	private AutoCalOvertimeSetting normalOTTime;
	
	/** The flex OT time. */
	//フレックス超過時間
	private AutoCalFlexOvertimeSetting flexOTTime;
	
	/** The rest time. */
	//休出時間
	private AutoCalRestTimeSetting restTime;
	
	
	/**
	 * Instantiates a new wkp auto cal setting.
	 *
	 * @param memento the memento
	 */
	public WkpAutoCalSetting(WkpAutoCalSettingGetMemento memento ) {
		super();
		this.companyId = memento.getCompanyId();
		this.wkpId = memento.getWkpId();
		this.normalOTTime = memento.getNormalOTTime();
		this.flexOTTime = memento.getFlexOTTime();
		this.restTime = memento.getRestTime();
	}
	

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WkpAutoCalSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWkpId(this.wkpId);
		memento.setFlexOTTime(this.flexOTTime);
		memento.setNormalOTTime(this.normalOTTime);
		memento.setRestTime(this.restTime);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((wkpId == null) ? 0 : wkpId.hashCode());
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
		WkpAutoCalSetting other = (WkpAutoCalSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (wkpId == null) {
			if (other.wkpId != null)
				return false;
		} else if (!wkpId.equals(other.wkpId))
			return false;
		return true;
	}
}
