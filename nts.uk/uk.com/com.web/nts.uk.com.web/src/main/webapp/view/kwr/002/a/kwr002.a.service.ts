module nts.uk.com.view.kwr002.a {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getAllAttendanceRecExpSet: "com/function/attendancerecord/export/setting/getAllAttendanceRecExpSet",
            getPermission: "com/function/attendancerecord/export/setting/getPermission"
        };

        export function getAllAttendanceRecExpSet(): JQueryPromise<Array<a.viewModel.AttendanceRecordExportSettingDto>> {
            return nts.uk.request.ajax("at", path.getAllAttendanceRecExpSet);
        }   

        export function getPermission(): JQueryPromise<boolean> {
            return nts.uk.request.ajax("at", path.getPermission);
        }
    }
}