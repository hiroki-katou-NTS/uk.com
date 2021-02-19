package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import java.util.List;
import java.util.Optional;

public interface AttendanceRecordExportSettingRepository {
    
    /**
     * Find by company employee and code.
     * 
     * @param companyId the company id
     * @param employeeId the employee id
     * @param code the code
     * @return the optional
     */
	public Optional<AttendanceRecordExportSetting> findByCode(ItemSelectionType selectionType
			, String companyId
			, Optional<String> employeeId
			, String code);
    
    /**
     * Find by layout id.
     *
     * @param layoutId the layout id
     * @return the optional
     */
    public Optional<AttendanceRecordExportSetting> findByLayoutId(String layoutId);
    
    /**
	 * Gets the seal stamp.
	 *
	 * @param companyId the company id
	 * @param code the code
	 * @return the seal stamp
	 */
	List<String> getSealStamp(String companyId, String layoutId);
	
	/**
	 * Delete attendance rec exp set.
	 *
	 * @param domain the domain
	 */
	void deleteAttendanceRecExpSet(AttendanceRecordExportSetting domain);
}
