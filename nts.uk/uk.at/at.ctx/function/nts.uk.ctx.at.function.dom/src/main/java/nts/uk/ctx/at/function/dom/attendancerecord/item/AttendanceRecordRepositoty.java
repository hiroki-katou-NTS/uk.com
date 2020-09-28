package nts.uk.ctx.at.function.dom.attendancerecord.item;



/**
 * The interface AttendanceRecordRepositoty.
 *
 * @author locph
 */
public interface AttendanceRecordRepositoty {

    /**
     * Delete attendance record.
     *
     * @param companyId         the company id
     * @param exportSettingCode the export setting code
     */
    void deleteAttendanceRecord(String layoutId);
    
    /**
     * Duplicate attendance to new layoutId
     * @param layoutId the layout id
     * @param dupliadteId the duplicate id
     */
    void duplicateAttendanceRecord(String layoutId, String duplicateId);
}
