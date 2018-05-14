package nts.uk.ctx.at.function.dom.attendancerecord.item;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;

/**
 * The Interface CalculateAttendanceRecordRepositoty.
 */
public interface CalculateAttendanceRecordRepositoty {

	/**
	 * Gets the calculate attendance record.
	 *
	 * @param CompanyId the company id
	 * @param code the code
	 * @param columnIndex the column index
	 * @param position the position
	 * @return the calculate attendance record
	 */
	CalculateAttendanceRecord getCalculateAttendanceRecord(String CompanyId, ExportSettingCode code, int columnIndex, int position, int exportArt );

	/**
	 * Adds the calculate attendance record.
	 *
	 * @param CompanyId the company id
	 * @param code the code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param calculateAttendanceRecord the calculate attendance record
	 */
	void addCalculateAttendanceRecord(String CompanyId, ExportSettingCode code, int columnIndex, int position, CalculateAttendanceRecord calculateAttendanceRecord);

	/**
	 * Update calculate attendance record.
	 *
	 * @param CompanyId the company id
	 * @param code the code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param calculateAttendanceRecord the calculate attendance record
	 */
	void updateCalculateAttendanceRecord(String CompanyId, ExportSettingCode code, int columnIndex, int position, CalculateAttendanceRecord calculateAttendanceRecord);

	/**
	 * Delete calculate attendance record.
	 *
	 * @param CompanyId the company id
	 * @param code the code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param calculateAttendanceRecord the calculate attendance record
	 */
	void deleteCalculateAttendanceRecord(String CompanyId, ExportSettingCode code, int columnIndex, int position, CalculateAttendanceRecord calculateAttendanceRecord);
}
