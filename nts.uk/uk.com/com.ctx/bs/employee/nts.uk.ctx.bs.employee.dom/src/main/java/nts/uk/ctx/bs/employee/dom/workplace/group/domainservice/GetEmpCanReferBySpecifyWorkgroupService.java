package nts.uk.ctx.bs.employee.dom.workplace.group.domainservice;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.EmployeeAffiliation;

/**
 * 職場グループを指定して参照可能な社員を取得する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".基幹.社員.職場.職場グループ
 * 
 * @author HieuLt
 *
 */
public class GetEmpCanReferBySpecifyWorkgroupService {
	/**
	 * [1] 取得する
	 * @param require
	 * @param date
	 * @param empId
	 * @param workplaceGroupId
	 * @return
	 */
	public static List<String> getEmpCanRefer(Require require, GeneralDate date, String empId,
			String workplaceGroupId) {
		//	$参照可能範囲 = 社員が参照可能な職場グループと社員参照範囲を取得する#取得する( require, 基準日, 社員ID )
		Map<String, ScopeReferWorkplaceGroup> data = GetWorkplaceGroupsAndEmpService.getWorkplaceGroup(require, date,
				empId);
		/*if $参照可能範囲.containsKey( 職場グループID )																			
		return List.empty*/
		if (!data.containsKey(workplaceGroupId)) {
			// ---QA--_
			return Collections.emptyList();
		}
		switch (data.get(workplaceGroupId)) {
		case ONLY_ME:
			return Arrays.asList(empId);
		default:// ALL_EMPLOYEE
			List<EmployeeAffiliation> lstEmpAffiliation = GetAllEmpWhoBelongWorkplaceGroupService.getAllEmp(require,
					date, workplaceGroupId);
			return lstEmpAffiliation.stream().map(c -> c.getEmployeeID()).collect(Collectors.toList());
		}

	}
	public static interface Require
			extends GetWorkplaceGroupsAndEmpService.Require, GetAllEmpWhoBelongWorkplaceGroupService.Require {
	}
}
