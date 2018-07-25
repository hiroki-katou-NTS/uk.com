/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.pub.workplace;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkplaceListPub.
 */
public interface WorkplaceListPub {
	
	/**
	 * Gets the workplace list id.
	 *
	 * @param referenceDate the reference date
	 * @param employeeID the employee ID
	 * @param referEmployee the refer employee
	 * @return the workplace list id
	 */
	// RequestList478: 基準日、指定社員から参照可能な職場リストを取得する（時間外労働用）
	List<String> getWorkplaceListId(GeneralDate referenceDate, String employeeID, boolean referEmployee);
}

