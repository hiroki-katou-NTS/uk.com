package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import java.util.Optional;

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
    public Optional<AttendanceRecordOuputItems> getOutputItemsByCompnayAndEmployee(String companyId, String employeeId);
    
    
    /**
     * Find by company employee and code.
     * 
     * @param companyId the company id
     * @param employeeId the employee id
     * @param code the code
     * @return the optional
     */
    public Optional<AttendanceRecordOuputItems> findByCompanyEmployeeAndCode(String companyId, String employeeId, String code);
    
    
    /**
     * Find by company employee code and layout id.
     *
     * @param companyId the company id
     * @param employeeId the employee id
     * @param code the code
     * @param layoutId the layout id
     * @return the optional
     */
    public Optional<AttendanceRecordOuputItems> findByCompanyEmployeeCodeAndLayoutId(String companyId, String employeeId, String code, String layoutId);
    
    
    /**
     * Find by layout id.
     *
     * @param layoutId the layout id
     * @return the optional
     */
    public Optional<AttendanceRecordExportSetting> findByLayoutId(String layoutId);
    
    /**
     * Find by company employee and code.
     *
     * @param companyId the company id
     * @param employeeId the employee id
     * @param code the code
     * @param selectionType the selection type
     * @return the optional
     */
    public Optional<AttendanceRecordOuputItems> findByCompanyEmployeeAndCodeAndSelection(String companyId, String employeeId, long code , int selectionType);
    
    
    
    
    
}
