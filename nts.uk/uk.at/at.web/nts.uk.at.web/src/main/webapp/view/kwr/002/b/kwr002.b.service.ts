module nts.uk.com.view.kwr002.b.service {
    import attendanceRecordExportSetting = nts.uk.com.view.kwr002.b.AttendanceRecordExportSetting
    const paths = {
        getAllARES: "com/function/attendancerecord/export/setting/getAllAttendanceRecExpSet",
        getARESByCode: "com/function/attendancerecord/export/setting/getAttendanceRecExpSet/",
        addARES: "com/function/attendancerecord/export/setting/addAttendanceRecExpSet",
        delARES: "com/function/attendancerecord/export/setting/deteleAttendanceRecExpSet"
    };

    export function getAllARES(): JQueryPromise<Array<attendanceRecordExportSetting>> {
        return nts.uk.request.ajax("at", paths.getAllARES);
    }

    export function getARESByCode(code: any): JQueryPromise<attendanceRecordExportSetting> {
        return nts.uk.request.ajax("at", paths.getARESByCode + code);
    }

    export function addARES(command: any): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.addARES, command);
    }

    export function delARES(command: any): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.delARES, command);
    }
}