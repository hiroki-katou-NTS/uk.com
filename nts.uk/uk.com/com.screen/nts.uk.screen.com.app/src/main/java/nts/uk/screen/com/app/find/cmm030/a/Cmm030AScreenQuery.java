package nts.uk.screen.com.app.find.cmm030.a;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.employee.employeeindesignated.StatusOfEmployment;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceExportService;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceInforParam;
import nts.uk.ctx.bs.employee.pub.employee.ResultRequest600Export;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.employee.employeeindesignated.EmpInDesignatedPub;
import nts.uk.ctx.bs.employee.pub.employee.employeeindesignated.EmployeeInDesignatedExport;
import nts.uk.ctx.bs.employee.pub.workplace.AffAtWorkplaceExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.sys.auth.app.find.role.workplace.RoleWorkplaceIDFinder.SystemType;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalSettingInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.GetSelfApprovalSettingsDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require.RequireService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require.RequireService.Require;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettings;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettingsRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.OperationMode;
import nts.uk.screen.com.app.find.cmm030.a.dto.ApprovalSettingInformationDto;
import nts.uk.screen.com.app.find.cmm030.a.dto.ApproverDisplayDataDto;
import nts.uk.screen.com.app.find.cmm030.a.dto.ApproverOperationSettingsDto;
import nts.uk.screen.com.app.find.cmm030.a.dto.CurrentEmployeeInfoDto;
import nts.uk.screen.com.app.find.cmm030.a.dto.RoleDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class Cmm030AScreenQuery {

	@Inject
	private RequireService requireService;

	@Inject
	private ApproverOperationSettingsRepository approverOperationSettingsRepository;

	@Inject
	private PersonApprovalRootRepository personApprovalRootRepository;

	@Inject
	private RoleRepository roleRepository;

	@Inject
	private RoleExportRepo roleExportRepo;

	@Inject
	private EmpInDesignatedPub empInDesignatedPub;

	@Inject
	private SyEmployeePub syEmployeePub;

	@Inject
	private WorkplaceExportService workplaceExportService;

	@Inject
	private WorkplacePub workplacePub;

	/**
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.共通メニュー別OCD.参照可能な社員を取得
	 * 
	 * @param referenceDate 基準日
	 * @return 配下社員IDリスト
	 */
	public List<EmployeeInDesignatedExport> findReferenceEmployees(GeneralDate baseDate) {
		// [No.151]ロールIDから参照可能な職場リストを取得する
		List<String> workplaceIds = this.roleExportRepo
				.findWorkPlaceIdByRoleId(SystemType.EMPLOYMENT.value, baseDate, Optional.empty()).getListWorkplaceIds();
		List<Integer> empStatus = Arrays.asList(StatusOfEmployment.INCUMBENT.value,
				StatusOfEmployment.LEAVE_OF_ABSENCE.value, StatusOfEmployment.HOLIDAY.value);
		// 指定職場の指定在籍状態の社員を取得
		return workplaceIds.stream()
				.map(workplaceId -> this.empInDesignatedPub.getEmpInDesignated(workplaceId, baseDate, empStatus))
				.flatMap(List::stream).collect(Collectors.toList());
	}

	/**
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.A：自分の承認者設定.メニュー別OCD.Ａ：起動可能かチェックする
	 * 
	 * @return 自分の承認者の運用設定
	 */
	public ApproverOperationSettingsDto initScreenA() {
		String cid = AppContexts.user().companyId();
		// 1. get(ログイン会社ID)
		Optional<ApproverOperationSettings> optDomain = this.approverOperationSettingsRepository.get(cid);
		// 2. [自分の承認者の運用設定＝Empty または 運用モード＝0]
		if (!optDomain.isPresent() || optDomain.get().getOperationMode().equals(OperationMode.PERSON_IN_CHARGE)) {
			throw new BusinessException("Msg_3287");
		}
		return ApproverOperationSettingsDto.fromDomain(optDomain.get());
	}

	/**
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.A：自分の承認者設定.メニュー別OCD.Ａ：ログイン社員の情報取得する
	 * 
	 * @return
	 */
	public CurrentEmployeeInfoDto findCurrentEmployeeInfo() {
		String cid = AppContexts.user().companyId();
		String sid = AppContexts.user().employeeId();

		// [RQ600]社員ID（List）から社員コードと表示名を取得（削除社員考慮）
		List<ResultRequest600Export> employees = this.syEmployeePub.getEmpInfoLstBySids(Arrays.asList(sid), null, false,
				false);
		// 社員ID（List）と基準日から所属職場IDを取得
		List<AffAtWorkplaceExport> workplaces = this.workplacePub.findBySIdAndBaseDateV2(Arrays.asList(sid),
				GeneralDate.today());
		// [No.560]職場IDから職場の情報をすべて取得する
		List<String> workplaceIds = workplaces.stream().map(AffAtWorkplaceExport::getWorkplaceId)
				.collect(Collectors.toList());
		List<WorkplaceInforParam> workplaceInfos = this.workplaceExportService.getWorkplaceInforFromWkpIds(cid,
				workplaceIds, GeneralDate.today());
		// get(ログイン社員の就業ロールID)
		Optional<Role> optRole = this.roleRepository.findByRoleId(AppContexts.user().roles().forAttendance());
		return new CurrentEmployeeInfoDto(employees, workplaceInfos,
				optRole.map(RoleDto::fromDomain).orElse(null));
	}

	/**
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.A：自分の承認者設定.メニュー別OCD.Ａ：承認者表示データの取得する
	 * 
	 * @param sid      社員ID
	 * @param baseDate 基準日
	 * @return
	 */
	public ApproverDisplayDataDto findApproverDisplayData(String sid, GeneralDate baseDate) {
		Require require = requireService.createRequire();
		// 取得する(Require, 会社ID, 社員ID, 年月日, システム区分)
		List<ApprovalSettingInformation> approvalSettingInformations = GetSelfApprovalSettingsDomainService.get(require,
				sid);
		// [RQ600]社員ID（List）から社員コードと表示名を取得（削除社員考慮）
		List<String> approverIds = approvalSettingInformations.stream()
				.map(ApprovalSettingInformation::getApprovalPhases).flatMap(List::stream)
				.map(ApprovalPhase::getApprovers).flatMap(List::stream).map(Approver::getEmployeeId)
				.collect(Collectors.toList());
		List<ResultRequest600Export> employees = this.syEmployeePub.getEmpInfoLstBySids(approverIds, null, false,
				false);
		return new ApproverDisplayDataDto(approvalSettingInformations.stream()
				.map(ApprovalSettingInformationDto::fromDomain).collect(Collectors.toList()), employees);
	}

	/**
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.A：自分の承認者設定.メニュー別OCD.Ａ：複写のため既に設定されている社員を取得する
	 * 
	 * @param baseDate 基準日
	 * @return 設定されている社員IDリスト
	 */
	public List<String> getSetEmployeeList(GeneralDate baseDate) {
		// 1. <call>
		List<EmployeeInDesignatedExport> employees = this.findReferenceEmployees(baseDate);
		// 2. 開始日が基準日より後の社員履歴を取得する(ログイン会社ID、配下社員IDリスト、基準日、システム区分＝就業)
		List<String> employeeIds = employees.stream().map(EmployeeInDesignatedExport::getEmployeeId)
				.collect(Collectors.toList());
		List<PersonApprovalRoot> domains = this.personApprovalRootRepository
				.getEmpHistWithStartAfterBaseDate(AppContexts.user().companyId(), employeeIds, baseDate);
		return domains.stream().map(PersonApprovalRoot::getEmployeeId).distinct().collect(Collectors.toList());
	}
}
