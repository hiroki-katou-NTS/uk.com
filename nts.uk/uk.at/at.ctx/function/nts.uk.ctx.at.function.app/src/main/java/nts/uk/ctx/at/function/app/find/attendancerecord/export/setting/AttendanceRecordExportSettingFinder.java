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

	/** The attendance rec exp set repo. */
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
				dto.setCode(item.getCode().toString());
				dto.setName(item.getName().toString());
				dto.setSealUseAtr(item.getSealUseAtr());
				dto.setNameUseAtr(item.getNameUseAtr().value);
				return dto;
			}).collect(Collectors.toList());
		}
		
		// アルゴリズム「自由設定の出力項目を取得」を実行する (Execute algorithm「自由設定の出力項目を取得」)
		Optional<AttendanceRecordFreeSetting> freeSetting = this.freeSetting.getOutputItemsByCompnayAndEmployee(companyId, employeeId);

		if (freeSetting.isPresent()) {
			// convert domain to Dto
			freeSettingLst = freeSetting.get().getAttendanceRecordExportSettings().stream().map(item -> {
				AttendanceRecordExportSettingDto dto = new AttendanceRecordExportSettingDto();
				dto.setCode(item.getCode().toString());
				dto.setName(item.getName().toString());
				dto.setSealUseAtr(item.getSealUseAtr());
				dto.setNameUseAtr(item.getNameUseAtr().value);
				return dto;
			}).collect(Collectors.toList());
		}


		// return
		return AttendanceRecordExportSettingWrapperDto.builder()
				.isFreeSetting(isFreeSetting)
				.freeSettingLst(freeSettingLst)
				.standardSettingLst(standardSettingLst)
				.build();
	}
	/**
	 * Gets the attendance record export setting dto.
	 *
	 * @param companyId the company id
	 * @param code the code
	 * @return the attendance record export setting dto
	 */
	public AttendanceRecordExportSettingDto getAttendanceRecordExportSettingDto(String code) {

		String companyId = AppContexts.user().companyId();
		Optional<AttendanceRecordExportSetting> optionalDomain = attendanceRecExpSetRepo
				.getAttendanceRecExpSet(companyId, code);

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
			return dto;
		}
		return new AttendanceRecordExportSettingDto();

	}

	/**
	 * Gets the seal stamp.
	 *
	 * @param code
	 *            the code
	 * @return the seal stamp
	 */
	public List<String> getSealStamp(long code) {
		String companyId = AppContexts.user().companyId();
		return attendanceRecExpSetRepo.getSealStamp(companyId, code);
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
	
	public AttendaceMonthDto getClosureMonth(){
		Optional<Closure> closureMonth = workScheduleOutputConditionFinder.getDomClosure(AppContexts.user().employeeId(), GeneralDate.today());
		Closure optCls = closureMonth.get();
		return new AttendaceMonthDto(optCls.getClosureMonth().getProcessingYm().toString());
	}
	
	public AttendanceAuthorityOfWorkPerform getAuthorityOfWorkPerformance() {
		String roleId = AppContexts.user().roles().forAttendance();
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();

		AttendanceAuthorityOfWorkPerform attendanceDto = new AttendanceAuthorityOfWorkPerform();
		
		boolean isFreeSetting = this.dailyPerAuthRepo.getAuthorityOfEmployee(roleId,
				new DailyPerformanceFunctionNo(BigDecimal.valueOf(51l)), true);
		
		if (isFreeSetting) {
			Optional<AttendanceRecordFreeSetting> freeSetting = this.freeSetting
					.getOutputItemsByCompnayAndEmployee(companyId, employeeId);
		} else {
			Optional<AttendanceRecordStandardSetting> standardSetting = this.standardRepo
					.getStandardByCompanyId(companyId);
		}

		attendanceDto.setFreeSetting(isFreeSetting);
		attendanceDto.setCompanyId(companyId);
		attendanceDto.setRoleId(roleId);
		attendanceDto.setEmployeeId(employeeId);
		attendanceDto.setFunctionNo(51);
		return attendanceDto;
	}
	
	/**
	 * Gets the free setting.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @return the free setting
	 */
	private AttendanceRecordFreeSettingDto getFreeSetting(String companyId, String employeeId) {
		Optional<AttendanceRecordFreeSetting> oDomain = this.freeSetting.getOutputItemsByCompnayAndEmployee(companyId, employeeId);
		
		return oDomain.map(d -> toFreeSettingDto(d)).orElse(null);
	}
	
	/**
	 * Gets the standard setting.
	 *
	 * @param companyId the company id
	 * @return the standard setting
	 */
	private AttendanceRecordStandardSettingDto getStandardSetting(String companyId) {
		Optional<AttendanceRecordStandardSetting> oDomain = this.standardRepo.getStandardByCompanyId(companyId);
		
		return oDomain.map(d -> toStandardSettingDto(d)).orElse(null);
	}
	
	/**
	 * To free setting dto.
	 *
	 * @param domain the domain
	 * @return the attendance record ouput items dto
	 */
	private AttendanceRecordFreeSettingDto toFreeSettingDto(AttendanceRecordFreeSetting domain) {
		AttendanceRecordFreeSettingDto dto = new AttendanceRecordFreeSettingDto();
		domain.setMemento(dto);
		return dto;
	}
	
	/**
	 * To standard setting dto.
	 *
	 * @param domain the domain
	 * @return the attendance record standard setting dto
	 */
	private AttendanceRecordStandardSettingDto toStandardSettingDto(AttendanceRecordStandardSetting domain) {
		AttendanceRecordStandardSettingDto dto = new AttendanceRecordStandardSettingDto();
		domain.setMemento(dto);
		return dto;
	}

}

