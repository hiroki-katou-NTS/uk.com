package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import java.util.List;
import java.util.Optional;

/**
 * The Interface AttendanceRecordOutputSettingRepository.
 */
public interface AttendanceRecordExportSettingRepository {

	/**
	 * Gets the all attendance rec out set.
	 *
	 * @param companyId the company id
	 * @return the all attendance rec out set
	 */
	List<AttendanceRecordExportSetting> getAllAttendanceRecExpSet(String companyId);

	/**
	 * Gets the attendance rec out set.
	 *
	 * @param companyId the company id
	 * @param code the code
	 * @return the attendance rec out set
	 */
	Optional<AttendanceRecordExportSetting> getAttendanceRecExpSet(String companyId, long code);

	/**
	 * Update attendance rec out set.
	 *
	 * @param attendanceRecordExpSet the attendance record out set
	 */
	void updateAttendanceRecExpSet(AttendanceRecordExportSetting attendanceRecordExpSet);

	/**
	 * Adds the attendance rec out set.
	 *
	 * @param attendanceRecordExpSet the attendance record out set
	 */
	void addAttendanceRecExpSet(AttendanceRecordExportSetting attendanceRecordExpSet);

	/**
	 * Delete attendance rec out set.
	 *
	 * @param attendanceRecordExpSet the attendance record out set
	 */
	void deleteAttendanceRecExpSet(AttendanceRecordExportSetting attendanceRecordExpSet);
	
	/**
	 * Gets the seal stamp.
	 *
	 * @param companyId the company id
	 * @param code the code
	 * @return the seal stamp
	 */
	List<String> getSealStamp (String companyId, long code);
}
