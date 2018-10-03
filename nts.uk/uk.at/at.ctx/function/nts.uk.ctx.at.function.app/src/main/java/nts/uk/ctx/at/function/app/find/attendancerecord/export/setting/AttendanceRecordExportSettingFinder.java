package nts.uk.ctx.at.function.app.find.attendancerecord.export.setting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.RoleLogin.LoginRoleAdapter;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.holidaysremaining.PermissionOfEmploymentForm;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.PermissionOfEmploymentFormRepository;
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

	/**
	 * Gets the all attendance record export setting.
	 *
	 * @param companyId
	 *            the company id
	 * @return the all attendance record export setting
	 */
	public List<AttendanceRecordExportSettingDto> getAllAttendanceRecordExportSetting(String companyId) {

		// get domain
		List<AttendanceRecordExportSetting> domainList = attendanceRecExpSetRepo.getAllAttendanceRecExpSet(companyId);

		// convert domain to Dto
		List<AttendanceRecordExportSettingDto> dtoList = domainList.stream().map(item -> {
			AttendanceRecordExportSettingDto dto = new AttendanceRecordExportSettingDto();
			dto.setCode(item.getCode().toString());
			dto.setName(item.getName().toString());
			dto.setSealUseAtr(item.getSealUseAtr());
			dto.setNameUseAtr(item.getNameUseAtr().value);
			return dto;
		}).collect(Collectors.toList());

		// return
		return dtoList;
	}

	/**
	 * Gets the attendance record export setting dto.
	 *
	 * @param companyId
	 *            the company id
	 * @param code
	 *            the code
	 * @return the attendance record export setting dto
	 */
	public AttendanceRecordExportSettingDto getAttendanceRecordExportSettingDto(String companyId, long code) {

		Optional<AttendanceRecordExportSetting> optionalDomain = attendanceRecExpSetRepo
				.getAttendanceRecExpSet(companyId, code);

		if (optionalDomain.isPresent()) {
			AttendanceRecordExportSetting domain = optionalDomain.get();
			AttendanceRecordExportSettingDto dto = new AttendanceRecordExportSettingDto();

			dto.setCode(domain.getCode().toString());
			dto.setName(domain.getName().toString());
			dto.setSealUseAtr(domain.getSealUseAtr());
			dto.setNameUseAtr(domain.getNameUseAtr().value);

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
}
