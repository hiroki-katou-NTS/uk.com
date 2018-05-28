package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import java.util.List;

import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;

/**
 * The Interface AttendanceRecordOutputSettingSetMemento.
 */
public interface AttendanceRecordExportSettingSetMemento {

	/**
	 * Gets the company id.
	 *
	 * @param companyId the company id
	 * @return the company id
	 */
	void setCompanyId(String companyId);

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
	void setSealStamp(String companyId, ExportSettingCode code, List<SealColumnName> seal);

	void setNameUseAtr(Integer nameUseAtr);
}
