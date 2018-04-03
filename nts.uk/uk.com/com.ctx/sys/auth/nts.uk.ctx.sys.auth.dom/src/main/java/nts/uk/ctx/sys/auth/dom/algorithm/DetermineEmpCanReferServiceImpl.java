package nts.uk.ctx.sys.auth.dom.algorithm;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;

import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmpWorkplaceHistoryAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmpWorkplaceHistoryImport;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DetermineEmpCanReferServiceImpl implements DetermineEmpCanReferService {
	@Inject
	private EmpReferenceRangeService empReferenceRangeService;

	/*@Inject
	private RoleWorkplaceIDFinder roleWorkplaceIDFinder;
*/
	@Inject
	private EmpWorkplaceHistoryAdapter empWorkplaceHistoryAdapter;

	@Override
	public boolean checkDetermineEmpCanRefer(GeneralDate date, String employeeID, List<Integer> roleType) {
		return false;
		/*List<Integer> roleTypes = Arrays.asList(RoleType.EMPLOYMENT.value, RoleType.SALARY.value, RoleType.HUMAN_RESOURCE.value, RoleType.PERSONAL_INFO.value, RoleType.OFFICE_HELPER.value, RoleType.MY_NUMBER.value);
		// アルゴリズム「社員参照範囲を取得する」を実行する
		for (int paramRoleType : roleTypes) {
			Optional<Role> role = empReferenceRangeService.getByUserIDAndReferenceDate(AppContexts.user().userId(), paramRoleType, GeneralDate.today());
			if(role.isPresent())
			// 参照可能な職場リストを取得する
			Integer referenceRange = role.get().getEmployeeReferenceRange().value;
			WorkplaceParam param = new WorkplaceParam(GeneralDate.today(), referenceRange);
			List<String> workplaceID1 = roleWorkplaceIDFinder.findListWkpId(param);
			// imported（権限管理）「所属職場履歴」を取得する - RequestList30
			Optional<EmpWorkplaceHistoryImport> empWorkplaceHistory = empWorkplaceHistoryAdapter.findBySid(employeeID, date);
			String workplaceID2 = empWorkplaceHistory.get().getWorkplaceID();
			if (workplaceID1.contains(workplaceID2) == true) {
				return true;
			} else {
				return false;
			}
		}
		return this.checkDetermineEmpCanRefer(date, employeeID, roleTypes);
		
*/
	}

}
