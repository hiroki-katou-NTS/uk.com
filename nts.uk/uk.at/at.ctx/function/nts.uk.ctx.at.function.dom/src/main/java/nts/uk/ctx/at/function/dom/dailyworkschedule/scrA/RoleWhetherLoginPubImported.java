/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule.scrA;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The Class RoleWhetherLoginPubImported.
 */
// @author HoangDD
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleWhetherLoginPubImported {
	/**就業担当者か*/
	boolean isEmployeeCharge;
	/**給与担当者か*/
	boolean isSalaryProfessional;
	/**人事担当者か*/
	boolean isHumanResOfficer;
	/**オフィスヘルパー担当者か*/
	boolean isOfficeHelperPersonne;
	/**個人情報担当者か*/
	boolean isPersonalInformation;
}
