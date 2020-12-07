module knr002.h {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getAllAttendanceRecExpSet: "com/function/attendancerecord/export/setting/getAllAttendanceRecExpSet",
        };

        export function getAllAttendanceRecExpSet(): JQueryPromise<Array<any>> {
            return nts.uk.request.ajax("at", path.getAllAttendanceRecExpSet);
        }
    }
}