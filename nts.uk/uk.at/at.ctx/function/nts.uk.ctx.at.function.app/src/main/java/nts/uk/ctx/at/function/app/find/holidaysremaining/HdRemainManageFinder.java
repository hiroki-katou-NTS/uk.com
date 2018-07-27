package nts.uk.ctx.at.function.app.find.holidaysremaining;

//import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.employment.EmploymentAdapter;
import nts.uk.ctx.at.function.dom.adapter.employment.EmploymentHistoryImported;
/*
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.employment.EmploymentAdapter;
import nts.uk.ctx.at.function.dom.adapter.employment.EmploymentHistoryImported;

import nts.uk.ctx.at.function.dom.holidaysremaining.PermissionOfEmploymentForm;
*/
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.PermissionOfEmploymentForm;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.HolidaysRemainingManagementRepository;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.PermissionOfEmploymentFormRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
/*import nts.uk.ctx.at.function.dom.holidaysremaining.repository.PermissionOfEmploymentFormRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
*/
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
/**
 * 休暇残数管理表の出力項目設定
 */
public class HdRemainManageFinder {

	@Inject
	private HolidaysRemainingManagementRepository hdRemainingManagementRepo;
	
	@Inject private ClosureEmploymentRepository closureEmploymentRepository;
	 
	@Inject private EmploymentAdapter employmentAdapter;
	 
	@Inject private PublicHolidaySettingRepository	publicHolidaySettingRepository;

	@Inject private ClosureService closureService;
 
	@Inject private ClosureRepository closureRepository;
	
	@Inject
	private PermissionOfEmploymentFormRepository permissionOfEmploymentFormRepository;

	public List<HdRemainManageDto> findAll() {
		return this.hdRemainingManagementRepo.getHolidayManagerLogByCompanyId(AppContexts.user().companyId()).stream()
				.map(a -> {
					HdRemainManageDto dto = HdRemainManageDto.fromDomain(a);
					return dto;
				}).collect(Collectors.toList());
	}

	public Optional<HolidaysRemainingManagement> findByCode(String code) {

		return this.hdRemainingManagementRepo.getHolidayManagerByCidAndExecCd(AppContexts.user().companyId(), code);
	}

	public HdRemainManageDto findDtoByCode(String code) {
		Optional<HolidaysRemainingManagement> hdManagement = this.hdRemainingManagementRepo
				.getHolidayManagerByCidAndExecCd(AppContexts.user().companyId(), code);
		if (hdManagement.isPresent()) {
			return HdRemainManageDto.fromDomain(hdManagement.get());
		}
		return null;
	}

	/**
	 * @return
	 */
	public DateHolidayRemainingDto getDate() {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		DateHolidayRemainingDto retDateHoliday = null;
		GeneralDate baseDate = GeneralDate.today(); //
		//Imported（就業）「所属雇用履歴」を取得する 
		Optional<EmploymentHistoryImported> employmentHisOptional = this.employmentAdapter.getEmpHistBySid(companyId, employeeId, baseDate);
		if (!employmentHisOptional.isPresent()) {
			return retDateHoliday;
		}
		// 対応するドメインモデル「雇用に紐づく就業締め」を取得する (Lấy domain 「雇用に紐づく就業締め」)
		Optional<ClosureEmployment> closureEmployment =	closureEmploymentRepository.findByEmploymentCD(companyId,
				employmentHisOptional.get().getEmploymentCode()); //
		//アルゴリズム「当月の年月を取得する」を実行する (thực hiện thuật toán 「当月の年月を取得する」)
		if (!closureEmployment.isPresent()) {
			return retDateHoliday;
		}
		
		Optional<Closure> optClosure = closureRepository.findById(companyId, closureEmployment.get().getClosureId());
		if (!optClosure.isPresent()) {
			return retDateHoliday;
		}
		YearMonth ym = optClosure.get().getClosureMonth().getProcessingYm(); // 当月の期間を算出する (Tính thời gian của tháng này 当月)
		DatePeriod datePeriod = closureService.getClosurePeriod(closureEmployment.get().getClosureId().intValue(), ym);
		return new DateHolidayRemainingDto(datePeriod.start().toString(), datePeriod.end().toString());
	}

	/**
	 * @return
	 */
	public PermissionOfEmploymentFormDto getPermissionOfEmploymentForm() {
		String companyId = AppContexts.user().companyId();
		String employeeRoleId = AppContexts.user().roles().forAttendance();

		Optional<PermissionOfEmploymentForm> permission = this.permissionOfEmploymentFormRepository.find(companyId,
				employeeRoleId, 1);
		if (permission.isPresent()) {
			return new PermissionOfEmploymentFormDto(permission.get().getCompanyId(), permission.get().getRoleId(),
					permission.get().getFunctionNo(), permission.get().isAvailable());
		}

		return new PermissionOfEmploymentFormDto(companyId, employeeRoleId, 1, false);
	}

}
