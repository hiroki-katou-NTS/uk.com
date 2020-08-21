package nts.uk.ctx.at.function.dom.attendancerecord.ouput.items;


/**
 * @author nws-ducnt
 * 
 * Repository
 */
public interface AttendanceRecordOuputItemsRepository {
	/**
	 * Add new OutputPeriodSetting
	 * @param domain
	 */
	void add(AttendanceRecordOuputItems domain);

	/**
	 * Update OutputPeriodSetting
	 * @param domain
	 */
    void update(AttendanceRecordOuputItems domain);
}
