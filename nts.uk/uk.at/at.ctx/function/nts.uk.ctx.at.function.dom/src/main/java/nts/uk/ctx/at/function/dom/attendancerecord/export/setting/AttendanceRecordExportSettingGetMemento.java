package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import java.util.List;

import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;

/**
 * The Interface AttendanceRecordOutputSettingGetMemento.
 */
public interface AttendanceRecordExportSettingGetMemento {

	/**
	 * Gets the layout id.
	 *
	 * @return the layout id
	 */
	String getLayoutId();

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
	 * Gets the name use atr.
	 *
	 * @return the name use atr
	 */
	Integer getNameUseAtr();

	/**
	 * KWR002 B - font size Gets the export font size.
	 *
	 * @return the export font size
	 */
	ExportFontSize getExportFontSize();

	/**
	 * KWR002 F A18_2 Gets the monthly confirmed display.
	 *
	 * @return the monthly confirmed display
	 */
	MonthlyConfirmedDisplay getMonthlyConfirmedDisplay();
	
	DayOfWeek getStartOfWeek();
}
