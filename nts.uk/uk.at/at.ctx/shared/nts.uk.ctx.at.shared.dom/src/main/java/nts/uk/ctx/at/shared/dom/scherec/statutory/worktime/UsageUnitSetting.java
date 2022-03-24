/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime;


import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class UsageUnitSetting.
 */
// 労働時間と日数の設定の利用単位の設定
@Getter
public class UsageUnitSetting extends AggregateRoot {
	
	/** The company id. */
	///** 会社ID. */
	private CompanyId companyId;

	/** The employee. */
	// 社員の労働時間と日数の管理をする
	private boolean employee;

	/** The work place. */
	// 職場の労働時間と日数の管理をする
	private boolean workPlace;

	/** The employment. */
	// 雇用の労働時間と日数の管理をする
	private boolean employment;

	/**
	 * Instantiates a new usage unit setting.
	 *
	 * @param memento the memento
	 */
	public UsageUnitSetting(UsageUnitSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employee = memento.getEmployee();
		this.workPlace = memento.getWorkPlace();
		this.employment = memento.getEmployment();
	}
	
	//テスト用に作成
	public UsageUnitSetting(CompanyId companyId,boolean employee,boolean workPlace,boolean employment) {
		this.companyId = companyId;
		this.employee = employee;
		this.workPlace = workPlace;
		this.employment = employment;
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(UsageUnitSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployee(this.employee);
		memento.setWorkPlace(this.workPlace);
		memento.setEmployment(this.employment);
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
		UsageUnitSetting other = (UsageUnitSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}
	
	public static interface Require {
		Optional<UsageUnitSetting> usageUnitSetting(String companyId);
	}
}
