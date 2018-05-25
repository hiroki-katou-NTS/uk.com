package nts.uk.ctx.at.function.dom.attendancerecord.export;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;

import java.util.List;

/**
 * The Interface AttendanceRecordOutputRepository.
 */
public interface AttendanceRecordExportRepository {

	/**
	 * Gets the all attendance record output.
	 *
	 * @param companyId the company id
	 * @param outputSettingCode the output setting code
	 * @return the all attendance record output
	 */
	List<AttendanceRecordExport> getAllAttendanceRecordExportDaily(String companyId, long exportSettingCode);

	/**
	 * Gets the attendance record export by index.
	 *
	 * @param companyId the company id
	 * @param exportSettingCode the export setting code
	 * @param columnIndex the column index
	 * @return the attendance record export by index
	 */
	List<AttendanceRecordExport> getAllAttendanceRecordExportMonthly(String companyId, long exportSettingCode);

	/**
	 * Update attendance record output.
	 *
	 * @param attendanceRecordOutput the attendance record output
	 */
	void updateAttendanceRecordExport(AttendanceRecordExport attendanceRecordExport);

	/**
	 * Adds the attendance record output.
	 *
	 * @param attendanceRecordOutput the attendance record output
	 */
	void addAttendanceRecordExport(AttendanceRecordExport attendanceRecordExport);

	void deleteAttendanceRecord(String companyId, ExportSettingCode exportSettingCode);
}
