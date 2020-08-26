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
    
    /**
     * Get domain model 「出勤簿の出力項目自由設定」－「出勤簿の出力項目設定」 with company ID and employee ID
     * @param companyId
     * @param employeeId
     * @return
     */
    AttendanceRecordOuputItems getAttendanceByCompnayAndEmployee(String companyId, String employeeId);
}
