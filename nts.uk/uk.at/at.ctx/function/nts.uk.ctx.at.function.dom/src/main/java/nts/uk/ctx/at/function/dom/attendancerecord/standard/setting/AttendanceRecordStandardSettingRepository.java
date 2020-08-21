package nts.uk.ctx.at.function.dom.attendancerecord.standard.setting;


/**
 * @author nws-ducnt
 * Repository
 */
public interface AttendanceRecordStandardSettingRepository {
	/**
	 * Add new OutputPeriodSetting
	 * @param domain
	 */
	void add(AttendanceRecordStandardSetting domain);

	/**
	 * Update OutputPeriodSetting
	 * @param domain
	 */
    void update(AttendanceRecordStandardSetting domain);
}
