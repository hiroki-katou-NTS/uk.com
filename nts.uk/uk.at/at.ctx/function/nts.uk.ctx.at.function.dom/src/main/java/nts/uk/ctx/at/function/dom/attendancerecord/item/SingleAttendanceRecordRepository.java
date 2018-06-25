package nts.uk.ctx.at.function.dom.attendancerecord.item;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;

// TODO: Auto-generated Javadoc
/**
 * The Interface SingleAttendanceRecordRepository.
 */
public interface SingleAttendanceRecordRepository {

	/**
	 * Gets the single attendance record.
	 *
	 * @param companyId the company id
	 * @param exportSettingCode the export setting code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param exportArt the export art
	 * @return the single attendance record
	 */
	Optional<SingleAttendanceRecord> getSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex, long position,long exportArt );

	/**
	 * Adds the singgle attendance record.
	 *
	 * @param companyId the company id
	 * @param exportSettingCode the export setting code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param singleAttendanceRecord the single attendance record
	 */
	void addSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex, long position,long exportArt,boolean useAtr, SingleAttendanceRecord singleAttendanceRecord);

	/**
	 * Update singgle attendance record.
	 *
	 * @param exportSettingCode the export setting code
	 * @param attendanceRecordExport the attendance record export
	 * @param singleAttendanceRecord the single attendance record
	 */
	void updateSingleAttendanceRecord(String companyId,ExportSettingCode exportSettingCode,long columnIndex, long position,long exportArt,boolean useAtr, SingleAttendanceRecord singleAttendanceRecord);

	/**
	 * Delete singgle attendance record.
	 *
	 * @param companyId the company id
	 * @param exportSettingCode the export setting code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param singleAttendanceRecord the single attendance record
	 */
	void deleteSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex, long position,long exportArt, SingleAttendanceRecord singleAttendanceRecord);
	
	/**
	 * Gets the id single attendance record by position.
	 *
	 * @param companyId the company id
	 * @param exportCode the export code
	 * @param position the position
	 * @return the id single attendance record by position
	 */
	List<Integer> getIdSingleAttendanceRecordByPosition(String companyId, long exportCode, long position);
	
}
