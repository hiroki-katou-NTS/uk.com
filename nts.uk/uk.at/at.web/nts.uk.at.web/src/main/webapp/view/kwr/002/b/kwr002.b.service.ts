module nts.uk.com.view.kwr002.b.service {
    import AttendanceRecordExportSetting = nts.uk.com.view.kwr002.b.AttendanceRecordExportSetting;
    import AttendanceRecordExportSettingWrapper = nts.uk.com.view.kwr002.b.AttendanceRecordExportSettingWrapper;

    const paths = {
        getAllARES: "com/function/attendancerecord/export/setting/getAllAttendanceRecExpSet",
        getARESByCode: "com/function/attendancerecord/export/setting/getAttendanceRecExpSet/",
        addARES: "com/function/attendancerecord/export/setting/addAttendanceRecExpSet",
        delARES: "com/function/attendancerecord/export/setting/deteleAttendanceRecExpSet",
    };

    export function getAllARES(): JQueryPromise<AttendanceRecordExportSettingWrapper> {
        return nts.uk.request.ajax("at", paths.getAllARES);
    }

    export function getARESByCode(code: any, selectionType: number): JQueryPromise<AttendanceRecordExportSetting> {
        return nts.uk.request.ajax("at", paths.getARESByCode + code + '/' + selectionType);
    }

    export function addARES(command: any): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.addARES, command);
    }

    export function delARES(command: any): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.delARES, command);
    }
}