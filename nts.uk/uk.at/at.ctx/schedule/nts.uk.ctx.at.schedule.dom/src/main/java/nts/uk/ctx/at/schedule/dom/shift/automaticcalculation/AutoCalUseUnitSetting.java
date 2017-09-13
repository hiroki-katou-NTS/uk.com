/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.automaticcalculation;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class AutoCalUseUnitSetting.
 */

/**
 * Gets the jobwkp.
 *
 * @return the jobwkp
 */
@Getter
public class AutoCalUseUnitSetting extends DomainObject {

	private CompanyId companyId;
	
	/** The job. */
	//職位の自動計算設定をする
	private Integer job;
	
	/** The workplace. */
	//職場の自動計算設定をする
	private Integer workplace;
	
	/** The jobwkp. */
	//職場・職位の自動計算設定を行う
	private Integer jobwkp;

	public AutoCalUseUnitSetting(CompanyId companyId, Integer job, Integer workplace, Integer jobwkp) {
		super();
		this.companyId = companyId;
		this.job = job;
		this.workplace = workplace;
		this.jobwkp = jobwkp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AutoCalUseUnitSetting other = (AutoCalUseUnitSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}

	
	
}
