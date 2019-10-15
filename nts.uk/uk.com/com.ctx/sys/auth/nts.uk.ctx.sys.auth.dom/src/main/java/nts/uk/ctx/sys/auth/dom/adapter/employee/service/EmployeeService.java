package nts.uk.ctx.sys.auth.dom.adapter.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.person.EmployeeBasicInforAuthImport;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.AffWorkplaceHistImport;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.AffiliationWorkplace;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.sys.auth.dom.algorithm.EmpReferenceRangeService;
import nts.uk.ctx.sys.auth.dom.role.EmployeeReferenceRange;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManager;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManagerRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeService {
	
	@Inject
	private PersonAdapter personAdapter;

	@Inject
	private UserRepository userRepo;

	@Inject
	private EmpReferenceRangeService empReferenceRangeService;

	@Inject
	private WorkplaceManagerRepository workplaceManagerRepository;

	@Inject
	private WorkplaceAdapter workplaceAdapter;

	/** RequestList338 */
	public List<String> findByEmpId(List<String> sID, int roleType) {
		// imported（権限管理）「社員」を取得する Request No1
		// employeeID = employeeID login
		String employeeIDLogin = AppContexts.user().employeeId();
		List<String> result = new ArrayList<>();
		Optional<EmployeeBasicInforAuthImport> employeeImport = personAdapter.getPersonInfor(employeeIDLogin);
		// List<String> listEmployeeID = listEmployeeImport.stream().map(c ->
		// c.getEmployeeId()).collect(Collectors.toList());
		if (!employeeImport.isPresent()) {
			return new ArrayList<>();
		} else {
			// アルゴリズム「紐付け先個人IDからユーザを取得する」を実行する
			// Execute algorithm "Acquire user from tied personal ID"
			Optional<User> optUser = userRepo.getByAssociatedPersonId(employeeImport.get().getPid());
			if (!optUser.isPresent()) {
				return new ArrayList<>();
			} else {
				Optional<Role> role = empReferenceRangeService.getByUserIDAndReferenceDate(optUser.get().getUserID(),
						roleType, GeneralDate.today());
				if (!role.isPresent()) {
					if (sID.contains(employeeIDLogin)) {
						result.add(employeeIDLogin);
					}
					return result;
				}

				EmployeeReferenceRange referenceRange = role.get().getEmployeeReferenceRange();
				if (referenceRange == EmployeeReferenceRange.ALL_EMPLOYEE) {
					return sID;
				} else if (referenceRange == EmployeeReferenceRange.ONLY_MYSELF) {
					if (sID.contains(employeeIDLogin)) {
						result.add(employeeIDLogin);
					}
					return result;
				} else {
					// ドメインモデル「職場管理者」をすべて取得する
					// (Lấy all domain 「職場管理者」)
					List<WorkplaceManager> listWorkplaceManager = workplaceManagerRepository
							.findListWkpManagerByEmpIdAndBaseDate(employeeIDLogin, GeneralDate.today());
					List<String> listWorkPlaceID2 = listWorkplaceManager.stream().map(c -> c.getWorkplaceId())
							.collect(Collectors.toList());
					// imported（権限管理）「所属職場履歴」を取得する
					// (Lấy imported（権限管理）「所属職場履歴」) Lay RequestList No.30
					Optional<AffWorkplaceHistImport> workPlace = workplaceAdapter
							.findWkpByBaseDateAndEmployeeId(GeneralDate.today(), employeeIDLogin);
					String workPlaceID1 = workPlace.get().getWorkplaceId();
					List<String> listWorkPlaceID3 = new ArrayList<>();
					if (referenceRange == EmployeeReferenceRange.DEPARTMENT_AND_CHILD) {
						// 配下の職場をすべて取得する
						// Lay RequestList No.154
						listWorkPlaceID3 = workplaceAdapter.findListWorkplaceIdByCidAndWkpIdAndBaseDate(
								AppContexts.user().companyId(), workPlaceID1, GeneralDate.today());
					}
					// 社員ID（List）と基準日から所属職場IDを取得 Lay request 227
					List<AffiliationWorkplace> lisAfiliationWorkplace = workplaceAdapter.findByListEmpIDAndDate(sID,
							GeneralDate.today());
					// 取得した所属職場履歴項目（List）を参照可能職場ID（List）で絞り込む
					List<String> listtWorkID = new ArrayList<>();
					listtWorkID.add(workPlaceID1);
					listtWorkID.addAll(listWorkPlaceID2);
					listtWorkID.addAll(listWorkPlaceID3);
					// 取得した所属職場履歴項目（List）を参照可能職場ID（List）で絞り込む
					result = lisAfiliationWorkplace.stream().filter(c -> listtWorkID.contains(c.getWorkplaceId()))
							.map(x -> x.getEmployeeId()).collect(Collectors.toList());

				}
			}

		}
		return result;
	}


}
