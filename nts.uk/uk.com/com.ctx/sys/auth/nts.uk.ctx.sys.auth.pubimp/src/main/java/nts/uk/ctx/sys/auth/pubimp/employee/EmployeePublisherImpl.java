package nts.uk.ctx.sys.auth.pubimp.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
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
import nts.uk.ctx.sys.auth.dom.algorithm.AcquireListWorkplaceByEmpIDService;
import nts.uk.ctx.sys.auth.dom.algorithm.AcquireUserIDFromEmpIDService;
import nts.uk.ctx.sys.auth.dom.algorithm.CanApprovalOnBaseDateService;
import nts.uk.ctx.sys.auth.dom.algorithm.DetermineEmpCanReferService;
import nts.uk.ctx.sys.auth.dom.algorithm.EmpReferenceRangeService;
import nts.uk.ctx.sys.auth.dom.grant.service.RoleIndividualService;
import nts.uk.ctx.sys.auth.dom.role.EmployeeReferenceRange;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManager;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManagerRepository;
import nts.uk.ctx.sys.auth.pub.employee.EmpWithRangeLogin;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;
import nts.uk.ctx.sys.auth.pub.employee.NarrowEmpByReferenceRange;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.auth.pub.user.UserExport;
import nts.uk.ctx.sys.auth.pub.user.UserPublisher;
import nts.uk.shr.com.context.AppContexts;

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

	@Inject
	private AcquireListWorkplaceByEmpIDService acquireListWorkplace;
	
	@Inject
	private AcquireUserIDFromEmpIDService acquireUserIDFromEmpIDService;
	
	@Inject
	private RoleIndividualService roleIndividualService;

	@Inject
	private RoleExportRepo roleExportRepo;
	
	@Override
	public Optional<NarrowEmpByReferenceRange> findByEmpId(List<String> sID, int roleType) {
		// imported（権限管理）「社員」を取得する Request No1
		// employeeID = employeeID login
		String employeeIDLogin = AppContexts.user().employeeId();
		List<String> result = new ArrayList<>();
		Optional<EmployeeBasicInforAuthImport> employeeImport = personAdapter.getPersonInfor(employeeIDLogin);
		// List<String> listEmployeeID = listEmployeeImport.stream().map(c ->
		// c.getEmployeeId()).collect(Collectors.toList());
		if (!employeeImport.isPresent()) {
			return Optional.empty();
		} else {
			// アルゴリズム「紐付け先個人IDからユーザを取得する」を実行する
			// Execute algorithm "Acquire user from tied personal ID"
			Optional<UserExport> useExport = userPublisher.getUserByAssociateId(employeeImport.get().getPid());
			if (!useExport.isPresent()) {
				return Optional.empty();
			} else {
				Optional<Role> role = empReferenceRangeService.getByUserIDAndReferenceDate(useExport.get().getUserID(), roleType, GeneralDate.today());
				if (role.isPresent() && role.get().getEmployeeReferenceRange() == EmployeeReferenceRange.ALL_EMPLOYEE) {
					return Optional.of(new NarrowEmpByReferenceRange(sID));
				} else if (role.isPresent() && role.get().getEmployeeReferenceRange() == EmployeeReferenceRange.ONLY_MYSELF) {
					sID.remove(employeeIDLogin);
					return Optional.of(new NarrowEmpByReferenceRange(sID));
				} else {
					// ドメインモデル「職場管理者」をすべて取得する
					// (Lấy all domain 「職場管理者」)
					List<WorkplaceManager> listWorkplaceManager = workplaceManagerRepository.findListWkpManagerByEmpIdAndBaseDate(employeeIDLogin, GeneralDate.today());
					List<String> listWorkPlaceID2 = listWorkplaceManager.stream().map(c -> c.getWorkplaceId()).collect(Collectors.toList());
					// imported（権限管理）「所属職場履歴」を取得する
					// (Lấy imported（権限管理）「所属職場履歴」) Lay RequestList No.30
					Optional<AffWorkplaceHistImport> workPlace = workplaceAdapter.findWkpByBaseDateAndEmployeeId(GeneralDate.today(), employeeIDLogin);
					String workPlaceID1 = workPlace.get().getWorkplaceId();
					List<String> listWorkPlaceID3 = new ArrayList<>();
					if (role.isPresent() && role.get().getEmployeeReferenceRange() == EmployeeReferenceRange.DEPARTMENT_AND_CHILD) {
						// 配下の職場をすべて取得する
						// Lay RequestList No.154
						listWorkPlaceID3 = workplaceAdapter.findListWorkplaceIdByCidAndWkpIdAndBaseDate(AppContexts.user().companyId(), workPlaceID1, GeneralDate.today());
					}
					// 社員ID（List）と基準日から所属職場IDを取得 Lay request 227
					List<AffiliationWorkplace> lisAfiliationWorkplace = workplaceAdapter.findByListEmpIDAndDate(sID, GeneralDate.today());
					// 取得した所属職場履歴項目（List）を参照可能職場ID（List）で絞り込む
					List<String> listtWorkID = new ArrayList<>();
					listtWorkID.add(workPlaceID1);
					listtWorkID.addAll(listWorkPlaceID2);
					listtWorkID.addAll(listWorkPlaceID3);
					// 取得した所属職場履歴項目（List）を参照可能職場ID（List）で絞り込む
					result = lisAfiliationWorkplace.stream().filter(c -> listtWorkID.contains(c.getWorkplaceId())).map(x -> x.getEmployeeId()).collect(Collectors.toList());
					
				}
			}

		}
		return Optional.of(new NarrowEmpByReferenceRange(result));
	}

	@Override
	public Optional<EmpWithRangeLogin> findByCompanyIDAndEmpCD(String companyID, String employeeCD) {
		// imported（権限管理）「社員」を取得する
		Optional<EmpInfoImport> empInfor = employeeInfoAdapter.getByComnyIDAndEmployeeCD(companyID, employeeCD);
		if (empInfor.isPresent()) {
			// 参照可能な社員かを判定する（職場）
			boolean result = determineEmpCanRefer.checkDetermineEmpCanRefer(GeneralDate.today(), empInfor.get().getEmployeeId(), RoleType.EMPLOYMENT.value);
			if (result == true) {
				return Optional.of((new EmpWithRangeLogin(empInfor.get().getPerName(), empInfor.get().getCompanyId(), empInfor.get().getPersonId(), empInfor.get().getEmployeeCode(), empInfor.get().getEmployeeId())));
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
				return Optional.of((new EmpWithRangeLogin(empInfor.get().getPerName(), empInfor.get().getCompanyId(), empInfor.get().getPersonId(), empInfor.get().getEmployeeCode(), empInfor.get().getEmployeeId())));
			} else
				return Optional.empty();
		}
		return Optional.empty();
	}

	@Override
	public List<String> getListWorkPlaceID(String employeeID, GeneralDate referenceDate) {
		// 社員IDからユーザIDを取得する
		// (Lấy userID từ employeeID)
		Optional<String> userID = acquireUserIDFromEmpIDService.getUserIDByEmpID(employeeID);
		if (!userID.isPresent()) {
			return new ArrayList<>();
		} else {
			// ユーザIDからロールを取得する
			// (lấy role từ userID)
			String roleID = roleIndividualService.getRoleFromUserId(userID.get(), RoleType.EMPLOYMENT.value, referenceDate);
			// 社員参照範囲を取得する
			// (Lấy phạm vi tham chiếu employee)
			OptionalInt optEmpRange = roleExportRepo.findEmpRangeByRoleID(roleID);
			// 指定社員が参照可能な職場リストを取得する
			// (Lấy list workplace của employee chỉ định)
			List<String> listWorkPlaceID = acquireListWorkplace.getListWorkPlaceID(employeeID, optEmpRange.getAsInt(), referenceDate);
			if (listWorkPlaceID.isEmpty()) {
				return new ArrayList<>();
			} else {
				return listWorkPlaceID;
			}
		}
	}
	
}
