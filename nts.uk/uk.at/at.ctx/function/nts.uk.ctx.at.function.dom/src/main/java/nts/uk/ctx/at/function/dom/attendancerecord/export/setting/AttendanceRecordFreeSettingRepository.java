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
    
    
    /**
     * Find by company employee and code.
     * 
     * @param companyId the company id
     * @param employeeId the employee id
     * @param code the code
     * @return the optional
     */
    public Optional<AttendanceRecordFreeSetting> findByCompanyEmployeeAndCode(String companyId, String employeeId,String code);
    
    
    /**
     * Find by company employee code and layout id.
     *
     * @param companyId the company id
     * @param employeeId the employee id
     * @param code the code
     * @param layoutId the layout id
     * @return the optional
     */
    public Optional<AttendanceRecordFreeSetting> findByCompanyEmployeeCodeAndLayoutId(String companyId, String employeeId, String code, String layoutId);
    
    
    /**
     * Find by layout id.
     *
     * @param layoutId the layout id
     * @return the optional
     */
    public Optional<AttendanceRecordExportSetting> findByLayoutId(String layoutId);
    
    
    
}
