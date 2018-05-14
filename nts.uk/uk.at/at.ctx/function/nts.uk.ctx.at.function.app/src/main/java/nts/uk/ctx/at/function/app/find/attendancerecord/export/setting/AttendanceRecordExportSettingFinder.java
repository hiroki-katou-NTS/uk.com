package nts.uk.ctx.at.function.app.find.attendancerecord.export.setting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;

/**
 * The Class AttendanceRecordExportSettingFinder.
 */
@Stateless
public class AttendanceRecordExportSettingFinder {

	/** The attendance rec exp set repo. */
	@Inject
	AttendanceRecordExportSettingRepository attendanceRecExpSetRepo;

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
			return dto;
		}).collect(Collectors.toList());

		// return
		return dtoList;
	}

	/**
	 * Gets the attendance record export setting dto.
	 *
	 * @param companyId the company id
	 * @param code the code
	 * @return the attendance record export setting dto
	 */
	public AttendanceRecordExportSettingDto getAttendanceRecordExportSettingDto(String companyId, String code) {

		AttendanceRecordExportSetting domain = attendanceRecExpSetRepo.getAttendanceRecExpSet(companyId, code);

		AttendanceRecordExportSettingDto dto = new AttendanceRecordExportSettingDto();

		dto.setCode(domain.getCode().toString());
		dto.setName(domain.getName().toString());
		dto.setSealUseAtr(domain.getSealUseAtr());

		return dto;
	}
}
