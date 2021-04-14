package nts.uk.ctx.at.auth.dom.employmentrole;

import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 実績工数で参照可能社員を取得する
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.権限管理.就業ロール.アルゴリズム.実績工数で参照可能社員を取得する
 * @author ThanhPV
 *
 */
public class GetEmployeesService {
	
//■Public
	/**
	 * @name [1] 取得する
	 * @param require
	 * @param cID 会社ID
	 * @param userID ユーザID
	 * @param employeeID 社員ID
	 * @param roleID ロールID
	 * @param baseDate 基準日
	 */
	public static Map<String, String> get(Require require, String companyId, String userID, String employeeID, String roleID, GeneralDate baseDate) {
		//$ロール = require.ロールを取得する(ログイン社員の就業ロールID)
		Optional<EmploymentRole> employmentRole = require.getEmploymentRoleById(companyId, roleID);
		//	if $ロール.実績工数社員参照 == 社員参照範囲と同じ		
		if(employmentRole.isPresent() && employmentRole.get().getScheduleEmployeeRef() == ScheduleEmployeeRef.SAME_EMPLOYEE_REF_RANGE) {
			//return 参照可能社員を取得する(ログインのユーザID,ログインの社員ID,$今日)	
			return require.getReferenceableEmployees(userID, employeeID, GeneralDate.today());
		}
		//	return 全社員を取得する(会社ID,基準日)	
		return require.getemployeesAllWorkplaces(companyId, baseDate);
	}

//■Require	
	public static interface Require {
		 
		//[R-1] ロールを取得する
		Optional<EmploymentRole> getEmploymentRoleById(String companyId, String roleId);
		
		//[R-2] 社員の締めを取得する
		//name 参照可能社員の所属職場を取得するAdapter
		Map<String, String> getReferenceableEmployees(String userID, String employeeID, GeneralDate date);
		
		//[R-3] 全社員を取得する
		// name 全ての職場の所属社員を取得するAdapter
		Map<String, String> getemployeesAllWorkplaces(String companyId, GeneralDate baseDate);
		
	}
}
