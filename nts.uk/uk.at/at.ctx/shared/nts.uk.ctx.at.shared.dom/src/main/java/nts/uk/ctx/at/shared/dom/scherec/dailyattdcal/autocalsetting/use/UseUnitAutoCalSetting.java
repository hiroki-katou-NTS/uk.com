/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.use;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;

/**
 * The Class AutoCalUseUnitSetting.
 */
// 時間外の自動計算の利用単位設定
@Getter
public class UseUnitAutoCalSetting extends DomainObject {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The job. */
	// 職位の自動計算設定をする
	private ApplyAtr useJobSet;

	/** The workplace. */
	// 職場の自動計算設定をする
	private ApplyAtr useWkpSet;

	/** The jobwkp. */
	// 職場・職位の自動計算設定を行う
	private ApplyAtr useJobwkpSet;
	
	/**
	 * Instantiates a new use unit auto cal setting.
	 *
	 * @param memento the memento
	 */
	public UseUnitAutoCalSetting(UseUnitAutoCalSettingGetMemento memento) {
		super();
		this.companyId = memento.getCompanyId();
		this.useJobSet = memento.getUseJobSet();
		this.useWkpSet = memento.getUseWkpSet();
		this.useJobwkpSet = memento.getUseJobwkpSet();
	}
	
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(UseUnitAutoCalSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setUseJobSet(useJobSet);
		memento.setUseJobwkpSet(useJobwkpSet);
		memento.setUseWkpSet(useWkpSet);
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
		UseUnitAutoCalSetting other = (UseUnitAutoCalSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}

	
}
