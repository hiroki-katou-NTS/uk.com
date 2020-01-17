/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.employee;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface EmployeeInformationPub.
 */
public interface EmployeeInformationPub {

	/**
	 * Find.
	 *
	 * @param param the param
	 * @return the list
	 */
	// <<Public>> 社員の情報を取得する
	public List<EmployeeInformationExport> find(EmployeeInformationQueryDto param);
	
	/**
	 * 社員情報リストを取得
	 * @param 個人IDリスト＜Optional>
	 * @param 社員IDリスト
	 * @param 基準日
	 * @return 社員情報のリスト
	 */
	public List<EmployeeInformationExport> getEmployeeInfos(Optional<List<String>> pIds, List<String> sIds,
			GeneralDate baseDate, Optional<Boolean> getDepartment, Optional<Boolean> getPosition, Optional<Boolean> getEmployment);
}
