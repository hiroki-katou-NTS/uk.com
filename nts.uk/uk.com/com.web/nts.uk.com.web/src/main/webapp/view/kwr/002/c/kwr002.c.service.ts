module nts.uk.com.view.kwr002.c{
    export module service{
        /**
         * define path to service
         */
        var path: any = {
            findAllAttendanceRecExportDaily: "at/function/attendancerecord/export/getAllAttendanceRecordExportDaily",
            findAllAttendanceRecExportMonthly: "at/function/attendancerecord/export/getAllAttendanceRecordExportMonthly",
            getAttendanceSingleList: "at/function/attendancerecord/export/getAttendanceListSingle",
            getAttendanceCalculateList: "at/function/attendancerecord/export/getAttendanceListCalculate",
        };    
        
        export function findAllAttendanceRecExportDaily(exportCode : number): JQueryPromise<Array<viewmodel.viewmodel.model.AttendanceItem>>{
            return nts.uk.request.ajax("com", path.findAllAttendanceRecExportDaily, exportCode);    
        }
    }
}