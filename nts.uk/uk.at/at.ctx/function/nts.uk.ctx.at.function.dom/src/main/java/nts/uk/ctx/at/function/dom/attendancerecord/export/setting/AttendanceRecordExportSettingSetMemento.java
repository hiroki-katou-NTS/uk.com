package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import java.util.List;

import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;

/**
 * The Interface AttendanceRecordOutputSettingSetMemento.
 */
public interface AttendanceRecordExportSettingSetMemento {
	
	/**
	 * Sets the layout id.
	 *
	 * @param layoutId the new layout id
	 */
	void setLayoutId(String layoutId);

	/**
	 * Sets the daily ouput item.
	 *
	 * @param attendanceList the new daily ouput item
	 */
	void setDailyExportItem(List<AttendanceRecordExport> attendanceList);

	/**
	 * Sets the monthly output item.
	 *
	 * @param attendanceList the new monthly output item
	 */
	void setMonthlyExportItem(List<AttendanceRecordExport> attendanceList);

	/**
	 * Sets the seal use atr.
	 *
	 * @param atr the new seal use atr
	 */
	void setSealUseAtr(Boolean atr);

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	void setCode(ExportSettingCode code);

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	void setName(ExportSettingName name);

	/**
	 * Sets the seal stamp.
	 *
	 * @param seal the new seal stamp
	 */
	void setSealStamp(List<SealColumnName> seal);

	/**
	 * Sets the name use atr.
	 *
	 * @param nameUseAtr the new name use atr
	 */
	void setNameUseAtr(Integer nameUseAtr);
	
	/**
	 * Font size - Screen B 
	 * Sets the export font size.
	 *
	 * @param exportFontSize the new export font size
	 */
	void setExportFontSize(ExportFontSize exportFontSize);
	
	/**
	 * A18_2 - Screen F
	 * Sets the monthly confirmed display.
	 *
	 * @param monthlyConfirmedDisplay the new monthly confirmed display
	 */
	void setMonthlyConfirmedDisplay(MonthlyConfirmedDisplay monthlyConfirmedDisplay);
	
	void setStartOfWeek(DayOfWeek startOfWeek);
}
