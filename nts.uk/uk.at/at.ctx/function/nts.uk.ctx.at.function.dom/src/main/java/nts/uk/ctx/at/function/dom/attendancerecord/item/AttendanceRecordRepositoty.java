package nts.uk.ctx.at.function.dom.attendancerecord.item;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;


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
    void deleteAttendanceRecord(String companyId, ExportSettingCode exportSettingCode);
}
