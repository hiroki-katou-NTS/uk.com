package nts.uk.ctx.at.function.app.find.attendancerecord.export.setting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.scrA.WorkScheduleOutputConditionFinder;
import nts.uk.ctx.at.function.dom.adapter.RoleLogin.LoginRoleAdapter;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ItemSelectionType;
//import nts.uk.ctx.at.function.dom.holidaysremaining.PermissionOfEmploymentForm;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.PermissionOfEmploymentFormRepository;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformAuthorRepo;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformanceFunctionNo;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AttendanceRecordExportSettingFinder.
 */
@Stateless
public class AttendanceRecordExportSettingFinder {

	@Inject
	AttendanceRecordExportSettingRepository attendanceRecExpSetRepo;

	/** The permission repo. */
	@Inject
	PermissionOfEmploymentFormRepository permissionRepo;

	/** The login role adapter. */
	@Inject
	LoginRoleAdapter loginRoleAdapter;

	/** Get Closure Month. */
	@Inject
	WorkScheduleOutputConditionFinder workScheduleOutputConditionFinder;

	@Inject
	private DailyPerformAuthorRepo dailyPerAuthRepo;

	@Inject
	private AttendanceRecordFreeSettingRepository freeSetting;

	@Inject
	private AttendanceRecordStandardSettingRepository standardRepo;

	/**
	 * Gets the all attendance record export setting.
	 *
	 * @param companyId the company id
	 * @return the all attendance record export setting
	 */
	public AttendanceRecordExportSettingWrapperDto getAllAttendanceRecordExportSetting() {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		// ログイン社員の就業帳票の権限を取得する (Get the authority of work report of logged in employee)
		boolean isFreeSetting = this.dailyPerAuthRepo.getAuthorityOfEmployee(roleId,
				new DailyPerformanceFunctionNo(BigDecimal.valueOf(51l)), true);

		List<AttendanceRecordExportSettingDto> freeSettingLst = new ArrayList<AttendanceRecordExportSettingDto>();
		List<AttendanceRecordExportSettingDto> standardSettingLst = new ArrayList<AttendanceRecordExportSettingDto>();
		// アルゴリズム「定型設定の出力項目を取得」を実行する (Execute algorithm 「定型設定の出力項目を取得」)
		Optional<AttendanceRecordStandardSetting> standardSetting = this.standardRepo.getStandardByCompanyId(companyId);

		if (standardSetting.isPresent()) {
			// convert domain to Dto
			standardSettingLst = standardSetting.get().getAttendanceRecordExportSettings().stream().map(item -> {
				AttendanceRecordExportSettingDto dto = new AttendanceRecordExportSettingDto();
				dto.setLayoutId(item.getLayoutId());
				dto.setCode(item.getCode().toString());
				dto.setName(item.getName().toString());
				dto.setSealUseAtr(item.getSealUseAtr());
				dto.setNameUseAtr(item.getNameUseAtr().value);
				dto.setMonthlyConfirmedDisplay(item.getMonthlyConfirmedDisplay().value);
				return dto;
			}).collect(Collectors.toList());
		}

		// アルゴリズム「自由設定の出力項目を取得」を実行する (Execute algorithm「自由設定の出力項目を取得」)
		Optional<AttendanceRecordFreeSetting> freeSetting = this.freeSetting
				.getOutputItemsByCompnayAndEmployee(companyId, employeeId);

		if (freeSetting.isPresent()) {
			// convert domain to Dto
			freeSettingLst = freeSetting.get().getAttendanceRecordExportSettings().stream().map(item -> {
				AttendanceRecordExportSettingDto dto = new AttendanceRecordExportSettingDto();
				dto.setLayoutId(item.getLayoutId());
				dto.setCode(item.getCode().toString());
				dto.setName(item.getName().toString());
				dto.setSealUseAtr(item.getSealUseAtr());
				dto.setNameUseAtr(item.getNameUseAtr().value);
				dto.setMonthlyConfirmedDisplay(item.getMonthlyConfirmedDisplay().value);
				return dto;
			}).collect(Collectors.toList());
		}

		// return
		return AttendanceRecordExportSettingWrapperDto.builder().isFreeSetting(isFreeSetting)
				.freeSettingLst(freeSettingLst).standardSettingLst(standardSettingLst).build();
	}

	/**
	 * Gets the attendance record export setting dto.
	 *
	 * @param companyId the company id
	 * @param code      the code
	 * @return the attendance record export setting dto
	 */
	public AttendanceRecordExportSettingDto getAttendanceRecordExportSettingDto(String code, Integer selectionType) {

		String companyId = AppContexts.user().companyId();
		Optional<String> employeeId = selectionType == ItemSelectionType.FREE_SETTING.value
				? Optional.of(AppContexts.user().employeeId())
				: Optional.empty();
		Optional<AttendanceRecordExportSetting> optionalDomain = attendanceRecExpSetRepo
				.findByCode(ItemSelectionType.valueOf(selectionType), companyId, employeeId, code);

		if (optionalDomain.isPresent()) {
			AttendanceRecordExportSetting domain = optionalDomain.get();
			AttendanceRecordExportSettingDto dto = new AttendanceRecordExportSettingDto();

			dto.setLayoutId(domain.getLayoutId());
			dto.setCode(domain.getCode().toString());
			dto.setName(domain.getName().toString());
			dto.setSealUseAtr(domain.getSealUseAtr());
			dto.setNameUseAtr(domain.getNameUseAtr().value);
			dto.setExportFontSize(domain.getExportFontSize().value);
			dto.setMonthlyConfirmedDisplay(domain.getMonthlyConfirmedDisplay().value);
			dto.setItemSelType(selectionType);
			dto.setSealStamp(domain.getSealStamp().stream()
				.map(x -> x.v()).collect(Collectors.toList()));
			
			return dto;
		}
		return new AttendanceRecordExportSettingDto();

	}

	/**
	 * Gets the seal stamp.
	 *
	 * @param code the code
	 * @return the seal stamp
	 */
	public List<String> getSealStamp(String layoutId) {
		String companyId = AppContexts.user().companyId();
		return attendanceRecExpSetRepo.getSealStamp(companyId, layoutId);
	}

	/**
	 * Have permission.
	 *
	 * @return the boolean
	 */
	public Boolean havePermission() {

		Boolean permission = loginRoleAdapter.getCurrentLoginerRole().isEmployeeCharge();

		return permission;
	}

	public AttendaceMonthDto getClosureMonth() {
		Optional<Closure> closureMonth = workScheduleOutputConditionFinder
				.getDomClosure(AppContexts.user().employeeId(), GeneralDate.today());
		Closure optCls = closureMonth.get();
		return new AttendaceMonthDto(optCls.getClosureMonth().getProcessingYm().toString());
	}

	public AttendanceAuthorityOfWorkPerform getAuthorityOfWorkPerformance() {
		String roleId = AppContexts.user().roles().forAttendance();

		AttendanceAuthorityOfWorkPerform attendanceDto = new AttendanceAuthorityOfWorkPerform();

		boolean isFreeSetting = this.dailyPerAuthRepo.getAuthorityOfEmployee(roleId,
				new DailyPerformanceFunctionNo(BigDecimal.valueOf(51l)), true);

		attendanceDto.setFreeSetting(isFreeSetting);
		return attendanceDto;
	}
}
