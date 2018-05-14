package nts.uk.ctx.at.function.dom.attendancerecord.item;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;

/**
 * The Interface SingleAttendanceRecordRepository.
 */
public interface SingleAttendanceRecordRepository {

	/**
	 * Gets the single attendance record.
	 *
	 * @param CompanyId the company id
	 * @param code the code
	 * @param columnIndex the column index
	 * @param position the position
	 * @return the single attendance record
	 */
	Optional<SingleAttendanceRecord> getSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex, long position,long exportArt );

	/**
	 * Adds the singgle attendance record.
	 *
	 * @param CompanyId the company id
	 * @param code the code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param singleAttendanceRecord the single attendance record
	 */
	void addSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex, long position, SingleAttendanceRecord singleAttendanceRecord);

	/**
	 * Update singgle attendance record.
	 *
	 * @param CompanyId the company id
	 * @param code the code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param singleAttendanceRecord the single attendance record
	 */
	void updateSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex, long position, SingleAttendanceRecord singleAttendanceRecord);

	/**
	 * Delete singgle attendance record.
	 *
	 * @param CompanyId the company id
	 * @param code the code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param singleAttendanceRecord the single attendance record
	 */
	void deleteSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex, long position, SingleAttendanceRecord singleAttendanceRecord);
}
