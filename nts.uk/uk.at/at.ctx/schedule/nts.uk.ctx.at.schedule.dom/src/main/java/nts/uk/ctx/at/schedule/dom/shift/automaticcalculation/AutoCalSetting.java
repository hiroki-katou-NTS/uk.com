/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.automaticcalculation;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * Gets the cal atr.
 *
 * @return the cal atr
 */
//自動計算設定
@Getter
public class AutoCalSetting extends DomainObject {
	
	private CompanyId companyID;

	//上限残業時間の設定
	private TimeLimitUpperLimitSetting SetUpLimitOT;
	
	///計算区分
	private AutoCalAtrOvertime CalAtr;

	public AutoCalSetting(CompanyId companyID, TimeLimitUpperLimitSetting setUpLimitOT, AutoCalAtrOvertime calAtr) {
		super();
		this.companyID = companyID;
		SetUpLimitOT = setUpLimitOT;
		CalAtr = calAtr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyID == null) ? 0 : companyID.hashCode());
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
		AutoCalSetting other = (AutoCalSetting) obj;
		if (companyID == null) {
			if (other.companyID != null)
				return false;
		} else if (!companyID.equals(other.companyID))
			return false;
		return true;
	}
}
