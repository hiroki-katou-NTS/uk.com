package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import java.util.Optional;

/**
 * @author nws-ducnt
 * 
 * Repository
 */
public interface AttendanceRecordFreeSettingRepository {
	/**
	 * Add new OutputPeriodSetting
	 * @param domain
	 */
	void add(AttendanceRecordFreeSetting domain);

	/**
	 * Update OutputPeriodSetting
	 * @param domain
	 */
    void update(AttendanceRecordFreeSetting domain);
    
    /**
     * Get domain model 「出勤簿の出力項目自由設定」－「出勤簿の出力項目設定」 with company ID and employee ID
     * @param companyId
     * @param employeeId
     * @return
     */
    public Optional<AttendanceRecordFreeSetting> getOutputItemsByCompnayAndEmployee(String companyId, String employeeId);

}
