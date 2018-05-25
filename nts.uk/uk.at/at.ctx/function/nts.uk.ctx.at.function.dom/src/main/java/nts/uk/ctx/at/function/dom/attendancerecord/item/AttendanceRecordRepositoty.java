package nts.uk.ctx.at.function.dom.attendancerecord.item;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;

public interface AttendanceRecordRepositoty {

    /**
     *
     * @param companyId
     * @param exportSettingCode
     */
    void deleteAttendanceRecord(String companyId, ExportSettingCode exportSettingCode);
}
