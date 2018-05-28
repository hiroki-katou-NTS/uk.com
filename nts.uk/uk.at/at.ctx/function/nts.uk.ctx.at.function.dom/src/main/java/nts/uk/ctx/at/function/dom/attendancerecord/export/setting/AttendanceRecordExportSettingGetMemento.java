package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import java.util.List;

import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;

/**
 * The Interface AttendanceRecordOutputSettingGetMemento.
 */
public interface AttendanceRecordExportSettingGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the daily ouput item.
	 *
	 * @return the daily ouput item
	 */
	List<AttendanceRecordExport> getDailyExportItem();

	/**
	 * Gets the monthly output item.
	 *
	 * @return the monthly output item
	 */
	List<AttendanceRecordExport> getMonthlyExportItem();

	/**
	 * Gets the seal use atr.
	 *
	 * @return the seal use atr
	 */
	Boolean getSealUseAtr();

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	ExportSettingCode getCode();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	ExportSettingName getName();

	/**
	 * Gets the seal stamp.
	 *
	 * @return the seal stamp
	 */
	List<SealColumnName> getSealStamp();

	/**
	 *
	 * @return
	 */
	Integer getNameUseAtr();

}
