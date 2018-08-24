package nts.uk.ctx.at.function.app.find.holidaysremaining;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.PermissionOfEmploymentForm;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.HolidaysRemainingManagementRepository;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.PermissionOfEmploymentFormRepository;
import nts.uk.ctx.at.shared.app.find.specialholiday.SpecialHolidayFinder;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
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
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepository;
	@Inject
	private RetentionYearlySettingRepository retentionYearlySettingRepository;
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;
	@Inject
	private ComSubstVacationRepository comSubstVacationRepository;
	@Inject
	private NursingLeaveSettingRepository nursingLeaveSettingRepository;
	@Inject
	private SpecialHolidayFinder specialHolidayFinder;

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

	public VariousVacationControlDto getVariousVacationControl() {
		boolean annualHolidaySetting = false;
		boolean yearlyReservedSetting = false;
		boolean substituteHolidaySetting = false;
		boolean pauseItemHolidaySetting = false;
		boolean childNursingSetting = false;
		boolean nursingCareSetting = false;
		String companyId = AppContexts.user().companyId();
		// 年休設定
		val annualPaidLeave = annualPaidLeaveSettingRepository.findByCompanyId(companyId);
		if (annualPaidLeave != null && annualPaidLeave.getYearManageType() == ManageDistinct.YES) {
			annualHolidaySetting = true;
		}

		// 積立年休設定
		val retentionYearly = retentionYearlySettingRepository.findByCompanyId(companyId);
		if (retentionYearly.isPresent() && retentionYearly.get().getManagementCategory() == ManageDistinct.YES) {
			yearlyReservedSetting = true;
		}

		// 代休管理区分
		val compLeaveCom = compensLeaveComSetRepository.find(companyId);
		if (compLeaveCom != null && compLeaveCom.getIsManaged() == ManageDistinct.YES) {
			substituteHolidaySetting = true;
		}

		// 振休管理区分
		val substVacation = comSubstVacationRepository.findById(companyId);
		if (substVacation.isPresent() && substVacation.get().getSetting().getIsManage() == ManageDistinct.YES) {
			pauseItemHolidaySetting = true;
		}
		val listNursingLeaveSetting = nursingLeaveSettingRepository.findByCompanyId(companyId);
		// 子の看護
		val childNursing = listNursingLeaveSetting.stream()
				.filter(i -> i.getNursingCategory() == NursingCategory.ChildNursing).findFirst();
		if (childNursing.isPresent() && childNursing.get().getManageType() == ManageDistinct.YES) {
			childNursingSetting = true;
		}

		// 介護
		val nursingCare = listNursingLeaveSetting.stream()
				.filter(i -> i.getNursingCategory() == NursingCategory.Nursing).findFirst();
		if (nursingCare.isPresent() && nursingCare.get().getManageType() == ManageDistinct.YES) {
			nursingCareSetting = true;
		}

		val listSpecialHoliday = specialHolidayFinder.findByCompanyId();

		return new VariousVacationControlDto(annualHolidaySetting, yearlyReservedSetting, substituteHolidaySetting,
				pauseItemHolidaySetting, childNursingSetting, nursingCareSetting, listSpecialHoliday);
	}

}
