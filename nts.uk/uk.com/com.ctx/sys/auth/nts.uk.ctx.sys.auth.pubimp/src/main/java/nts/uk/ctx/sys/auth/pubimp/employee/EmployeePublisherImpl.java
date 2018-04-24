package nts.uk.ctx.sys.auth.pubimp.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmpInfoImport;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmployeeInfoAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.person.EmployeeBasicInforAuthImport;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.AffWorkplaceHistImport;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.AffiliationWorkplace;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.sys.auth.dom.algorithm.EmpReferenceRangeService;
import nts.uk.ctx.sys.auth.dom.role.EmployeeReferenceRange;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManager;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManagerRepository;
import nts.uk.ctx.sys.auth.pub.employee.EmpWithRangeLogin;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;
import nts.uk.ctx.sys.auth.pub.employee.NarrowEmpByReferenceRange;
import nts.uk.ctx.sys.auth.pub.user.UserExport;
import nts.uk.ctx.sys.auth.pub.user.UserPublisher;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.sys.auth.dom.algorithm.CanApprovalOnBaseDateService;
import nts.uk.ctx.sys.auth.dom.algorithm.DetermineEmpCanReferService;

@Stateless
public class EmployeePublisherImpl implements EmployeePublisher {

	@Inject
	private PersonAdapter personAdapter;

	@Inject
	private UserPublisher userPublisher;

	@Inject
	private EmpReferenceRangeService empReferenceRangeService;

	@Inject
	private WorkplaceManagerRepository workplaceManagerRepository;

	@Inject
	private WorkplaceAdapter workplaceAdapter;

	@Inject
	private EmployeeInfoAdapter employeeInfoAdapter;

	@Inject
	private DetermineEmpCanReferService determineEmpCanRefer;

	@Inject
	private CanApprovalOnBaseDateService canApprovalOnBaseDateService;

	@Override
	public Optional<NarrowEmpByReferenceRange> findByEmpId(List<String> sID) {
		List<String> listResultEmployeeID = new ArrayList<>();

		// imported（権限管理）「社員」を取得する
		List<EmployeeBasicInforAuthImport> listEmployeeImport = personAdapter.listPersonInfor(sID);
		List<String> listEmployeeID = listEmployeeImport.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		if (!listEmployeeImport.isEmpty()) {
			List<String> listPID = listEmployeeImport.stream().map(c -> c.getPid()).collect(Collectors.toList());
			// アルゴリズム「紐付け先個人IDからユーザを取得する」を実行するExecute algorithm "Acquire user
			// from tied personal ID"
			List<UserExport> user = userPublisher.getListUserByListAsId(listPID);
			// 終了状態：参照社員取得エラー
			if (user.isEmpty())
				return Optional.empty();
			List<UserExport> listUserExport = new ArrayList<>();
			// アルゴリズム「紐付け先個人IDからユーザを取得する」を実行する
			for (UserExport userExport : user) {
				if (userExport.isExistAssociatedPersonID(userExport.getAssociatedPersonID()))
					listUserExport.add(userExport);
			}
			if (listUserExport.isEmpty())
				return Optional.empty();
			List<String> listUserID = listUserExport.stream().map(c -> c.getUserID()).collect(Collectors.toList());
			for (String userID : listUserID) {
				Optional<Role> role = empReferenceRangeService.getByUserIDAndReferenceDate(userID, RoleType.EMPLOYMENT.value, GeneralDate.today());
				if (role.get().getEmployeeReferenceRange() == EmployeeReferenceRange.ALL_EMPLOYEE) {
					Optional<NarrowEmpByReferenceRange> data = Optional.of(new NarrowEmpByReferenceRange(listEmployeeID));
					return data;
				} else if (role.get().getEmployeeReferenceRange() == EmployeeReferenceRange.DEPARTMENT_ONLY) {
					return Optional.empty();
				} else {
					// ドメインモデル「職場管理者」をすべて取得する
					for (String employeeID : listEmployeeID) {
						// ドメインモデル「職場管理者」をすべて取得する
						List<WorkplaceManager> listWorkplaceManager = workplaceManagerRepository.findListWkpManagerByEmpIdAndBaseDate(employeeID, GeneralDate.today());
						List<String> listWorkPlaceID2 = listWorkplaceManager.stream().map(c -> c.getWorkplaceId()).collect(Collectors.toList());
						// imported（権限管理）「所属職場履歴」を取得する
						Optional<AffWorkplaceHistImport> workPlace = workplaceAdapter.findWkpByBaseDateAndEmployeeId(GeneralDate.today(), employeeID);
						String workPlaceID1 = workPlace.get().getWorkplaceId();
						List<String> listWorkPlaceID3 = new ArrayList<>();
						if (role.get().getEmployeeReferenceRange() == EmployeeReferenceRange.DEPARTMENT_AND_CHILD) {
							// 配下の職場をすべて取得する
							listWorkPlaceID3 = workplaceAdapter.findListWorkplaceIdByCidAndWkpIdAndBaseDate(AppContexts.user().companyId(), workPlaceID1, GeneralDate.today());
						}
						// 社員ID（List）と基準日から所属職場IDを取得 Lay request 227
						List<AffiliationWorkplace> lisAfiliationWorkplace = workplaceAdapter.findByListEmpIDAndDate(listEmployeeID, GeneralDate.today());
						List<String> resultWorkID = new ArrayList<>();
						resultWorkID.add(workPlaceID1);
						resultWorkID.addAll(listWorkPlaceID2);
						resultWorkID.addAll(listWorkPlaceID3);
						// 取得した所属職場履歴項目（List）を参照可能職場ID（List）で絞り込む
						listResultEmployeeID = lisAfiliationWorkplace.stream().filter(c -> resultWorkID.contains(c.getWorkplaceId())).map(x -> x.getEmployeeId()).collect(Collectors.toList());
					}
				}
			}

		}

		return Optional.of(new NarrowEmpByReferenceRange(listResultEmployeeID));
	}

	@Override
	public Optional<EmpWithRangeLogin> findByCompanyIDAndEmpCD(String companyID, String employeeCD) {
		// imported（権限管理）「社員」を取得する
		Optional<EmpInfoImport> empInfor = employeeInfoAdapter.getByComnyIDAndEmployeeCD(companyID, employeeCD);
		if (empInfor.isPresent()) {
			// 参照可能な社員かを判定する（職場）
			boolean result = determineEmpCanRefer.checkDetermineEmpCanRefer(GeneralDate.today(), empInfor.get().getEmployeeId(), RoleType.EMPLOYMENT.value);
			if (result == true) {
				return Optional.of((new EmpWithRangeLogin(
						empInfor.get().getPerName(),
						empInfor.get().getCompanyId(),
						empInfor.get().getPersonId(),
						empInfor.get().getEmployeeCode(),
						empInfor.get().getEmployeeId())));
			} else
				return Optional.empty();
		}
		return Optional.empty();
	}

	@Override
	public Optional<EmpWithRangeLogin> getByComIDAndEmpCD(String companyID, String employeeCD, GeneralDate referenceDate) {
		// imported（権限管理）「社員」を取得する Lấy request List No.18
		Optional<EmpInfoImport> empInfor = employeeInfoAdapter.getByComnyIDAndEmployeeCD(companyID, employeeCD);
		if (empInfor.isPresent()) {
			// 指定社員が基準日に承認権限を持っているかチェックする Lay request 305 tu domain service
			boolean result = canApprovalOnBaseDateService.canApprovalOnBaseDate(empInfor.get().getCompanyId(), empInfor.get().getEmployeeId(), referenceDate);
			if (result == true) {
				return Optional.of((new EmpWithRangeLogin(
						empInfor.get().getPerName(),
						empInfor.get().getCompanyId(),
						empInfor.get().getPersonId(),
						empInfor.get().getEmployeeCode(),
						empInfor.get().getEmployeeId())));
			} else
				return Optional.empty();
		}
		return Optional.empty();
	}

	
	
}
