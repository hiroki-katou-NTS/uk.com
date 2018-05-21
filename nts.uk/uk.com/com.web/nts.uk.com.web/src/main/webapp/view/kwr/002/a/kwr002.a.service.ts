module nts.uk.com.view.kwr002.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            findAllAttendanceRecExportDaily: "com/function/attendancerecord/export/getAllAttendanceRecordExportDaily",
            getAttendanceSingleList: "com/function/attendancerecord/export/getAttendanceListSingle"           
        };

//        export function findAllAttendanceRecExportDaily(exportCode: number): JQueryPromise<Array<viewmodel.model.AttendanceRecExp>> {
//            return nts.uk.request.ajax("at", path.findAllAttendanceRecExportDaily, exportCode);
//        }
        

        export function getAttendanceSingleList(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.getAttendanceSingleList);
        }

    }
}