package nts.uk.ctx.at.function.app.find.holidaysremaining;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.role.RoleExportRepoAdapter;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.PermissionOfEmploymentForm;
import nts.uk.ctx.at.function.dom.holidaysremaining.VariousVacationControlService;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.HolidaysRemainingManagementRepository;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.PermissionOfEmploymentFormRepository;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 休暇残数管理表の出力項目設定
 */

@Stateless
public class HdRemainManageFinder {

	@Inject
	private HolidaysRemainingManagementRepository hdRemainingManagementRepo;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	@Inject
	private ClosureRepository closureRepository;
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	@Inject
	private PermissionOfEmploymentFormRepository permissionOfEmploymentFormRepository;
	@Inject
	private VariousVacationControlService variousVacationControlService;
	@Inject
	private RoleExportRepoAdapter roleExportRepoAdapter;

	public List<HdRemainManageDto> findAll() {
		return this.hdRemainingManagementRepo.getHolidayManagerLogByCompanyId(AppContexts.user().companyId()).stream()
				.map(HdRemainManageDto::fromDomain).collect(Collectors.toList());
	}

	public Optional<HolidaysRemainingManagement> findByCode(String code) {

		return this.hdRemainingManagementRepo.getHolidayManagerByCidAndExecCd(AppContexts.user().companyId(), code);
	}

	public HdRemainManageDto findDtoByCode(String code) {
		Optional<HolidaysRemainingManagement> hdManagement = this.hdRemainingManagementRepo
				.getHolidayManagerByCidAndExecCd(AppContexts.user().companyId(), code);
		return hdManagement.map(HdRemainManageDto::fromDomain).orElse(null);
	}

	// 当月を取得
	public Optional<YearMonth> getCurrentMonth(String companyId, String employeeId, GeneralDate systemDate) {
		// ドメインモデル「所属雇用履歴」を取得する
		Optional<BsEmploymentHistoryImport> bsEmploymentHistOpt = shareEmploymentAdapter
				.findEmploymentHistory(companyId, employeeId, systemDate);
		if (!bsEmploymentHistOpt.isPresent()) {
			return Optional.empty();
		}

		String employmentCode = bsEmploymentHistOpt.get().getEmploymentCode();
		// ドメインモデル「雇用に紐づく就業締め」を取得する
		Optional<ClosureEmployment> closureEmploymentOpt = closureEmploymentRepository.findByEmploymentCD(companyId,
				employmentCode);

		// 雇用に紐づく締めを取得する
		Integer closureId = 1;
		if (closureEmploymentOpt.isPresent()) {
			closureId = closureEmploymentOpt.get().getClosureId();
		}

		// 当月の年月を取得する
		Optional<Closure> closureOpt = closureRepository.findById(companyId, closureId);
		if (closureOpt.isPresent()) {
			return Optional.of(closureOpt.get().getClosureMonth().getProcessingYm());
		}
		return Optional.empty();
	}

	public DateHolidayRemainingDto getDate() {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		GeneralDate baseDate = GeneralDate.today(); //

		Optional<YearMonth> currentMonthOpt = this.getCurrentMonth(companyId, employeeId, baseDate);

		if (!currentMonthOpt.isPresent()) {
			return null;
		}
		GeneralDate endDate = GeneralDate.ymd(currentMonthOpt.get().year(), currentMonthOpt.get().month() + 1, 1);
		GeneralDate startDate = endDate.addYears(-1);

		return new DateHolidayRemainingDto(startDate.toString(), endDate.toString());
	}

	public PermissionOfEmploymentFormDto getPermissionOfEmploymentForm() {
		String companyId = AppContexts.user().companyId();
		String employeeRoleId = AppContexts.user().roles().forAttendance();

		Optional<PermissionOfEmploymentForm> permission = this.permissionOfEmploymentFormRepository.find(companyId,
				employeeRoleId, 1);
		return permission
				.map(permissionOfEmploymentForm -> new PermissionOfEmploymentFormDto(
						permissionOfEmploymentForm.getCompanyId(), permissionOfEmploymentForm.getRoleId(),
						permissionOfEmploymentForm.getFunctionNo(), permissionOfEmploymentForm.isAvailable()))
				.orElseGet(() -> new PermissionOfEmploymentFormDto(companyId, employeeRoleId, 1, false));

	}
	
	public RoleWhetherLoginDto getCurrentLoginerRole() {
		RoleWhetherLoginDto role = new RoleWhetherLoginDto();
		role.setEmployeeCharge(roleExportRepoAdapter.getCurrentLoginerRole().isEmployeeCharge());
		return role;
	}

	public VariousVacationControlDto getVariousVacationControl() {
		return VariousVacationControlDto.fromDomain(variousVacationControlService.getVariousVacationControl());
	}

}
