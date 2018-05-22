module nts.uk.com.view.kwr002.b.service {
    import attendanceRecordExportSetting = nts.uk.com.view.kwr002.b.AttendanceRecordExportSetting
    const paths = {
        getAllARES: "com/function/attendancerecord/export/setting/getAllAttendanceRecExpSet",
        getARESByCode: "com/function/attendancerecord/export/setting/getAttendanceRecExpSet/"
    }

    export function getAllARES(): JQueryPromise<Array<attendanceRecordExportSetting>> {
        return nts.uk.request.ajax("at", paths.getAllARES);
    }

    export function getARESByCode(code: string): JQueryPromise<attendanceRecordExportSetting> {
        return nts.uk.request.ajax("at", paths.getARESByCode + code);
    }
}