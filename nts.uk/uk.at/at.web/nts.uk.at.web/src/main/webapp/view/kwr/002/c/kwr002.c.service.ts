module nts.uk.com.view.kwr002.c{
    export module service{
        /**
         * define path to service
         */
        var path: any = {
            findAllAttendanceRecExportDaily: "com/function/attendancerecord/export/getAllAttendanceRecordDailyExport/",
            findAllAttendanceRecExportMonthly: "com/function/attendancerecord/export/getAllAttendanceRecordExportMonthly/",
            getAttendanceSingleList: "com/function/attendancerecord/export/getAttendanceListSingle",
            getAttendanceCalculateList: "com/function/attendancerecord/export/getAttendanceListCalculate/",
            getSealStamp:"com/function/attendancerecord/export/setting/getSealStamp/"
        };    
        
        export function findAllAttendanceRecExportDaily(exportCode : number): JQueryPromise<Array<viewmodel.model.AttendanceRecExp>>{
            return nts.uk.request.ajax("at", path.findAllAttendanceRecExportDaily + exportCode);   
        }
        
        export function findAllAttendanceRecExportMonthly(exportCode : number): JQueryPromise<Array<viewmodel.model.AttendanceRecExp>>{
            return nts.uk.request.ajax("at", path.findAllAttendanceRecExportMonthly + exportCode);   
        }
        
        export function getAttendanceSingleList():JQueryPromise<any>{
            return nts.uk.request.ajax("at",path.getAttendanceSingleList);    
        }
        
        export function getAttendanceCalculateList( attendanceType:number):JQueryPromise<any>{
            return nts.uk.request.ajax("at",path.getAttendanceCalculateList + attendanceType);
        }
        
        export function getSealStamp(exportCode : number): JQueryPromise<Array<String>>{
            return nts.uk.request.ajax("at",path.getSealStamp + exportCode); 
        }
     
    }
}