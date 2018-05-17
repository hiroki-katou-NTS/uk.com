module nts.uk.com.view.kwr002.c{
    export module service{
        /**
         * define path to service
         */
        var path: any = {
            findAllAttendanceRecExportDaily: "com/function/attendancerecord/export/getAllAttendanceRecordExportDaily",
            findAllAttendanceRecExportMonthly: "com/function/attendancerecord/export/getAllAttendanceRecordExportMonthly",
            getAttendanceSingleList: "com/function/attendancerecord/export/getAttendanceListSingle",
            getAttendanceCalculateList: "com/function/attendancerecord/export/getAttendanceListCalculate",
        };    
        
        export function findAllAttendanceRecExportDaily(exportCode : number): JQueryPromise<Array<viewmodel.model.AttendanceRecExp>>{
            return nts.uk.request.ajax("at", path.findAllAttendanceRecExportDaily, exportCode);    
        }
        
        export function getAttendanceSingleList():JQueryPromise<any>{
            return nts.uk.request.ajax("at",path.getAttendanceSingleList);    
        }
        
        export function getAttendanceCalculateList():JQueryPromise<any>{
            return nts.uk.request.ajax("at",path.getAttendanceCalculateList);    
        }
     
    }
}