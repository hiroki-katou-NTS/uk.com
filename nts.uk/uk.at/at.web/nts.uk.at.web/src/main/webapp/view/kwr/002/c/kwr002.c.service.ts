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
            getSealStamp:"com/function/attendancerecord/export/setting/getSealStamp/",
            getAttendanceRecordExportSetting: "com/function/attendancerecord/export/setting/getAttendanceRecExpSet/",
            getApprovalProcessingUseSetting: "com/function/attendancerecord/export/getApprovalProcessingUseSetting",
            getDailyAttendanceItems: "com/function/attendancerecord/export/getDailyAttendanceItems",
            getMonthlyAttendanceItems: "com/function/attendancerecord/export/getMonthlyAttendanceItems",
            getSingleAttendanceRecord: "com/function/attendancerecord/export/getSingleAttendanceRecord",
            getCalculateAttendanceRecordDto: "com/function/attendancerecord/export/getCalculateAttendanceRecordDto"
        };

        export function findAllAttendanceRecExportDaily(layoutId : string): JQueryPromise<Array<viewmodel.model.AttendanceRecExp>>{
            return nts.uk.request.ajax("at", path.findAllAttendanceRecExportDaily + layoutId);   
        }
        
        export function findAllAttendanceRecExportMonthly(layoutId : string): JQueryPromise<Array<viewmodel.model.AttendanceRecExp>>{
            return nts.uk.request.ajax("at", path.findAllAttendanceRecExportMonthly + layoutId);   
        }

        export function getAttendanceSingleList():JQueryPromise<any>{
            return nts.uk.request.ajax("at", path.getAttendanceSingleList);    
        }
        
        export function getAttendanceCalculateList( attendanceType:number):JQueryPromise<any>{
            return nts.uk.request.ajax("at", path.getAttendanceCalculateList + attendanceType);
        }
        
        export function getSealStamp(layoutId: string): JQueryPromise<Array<string>>{
            return nts.uk.request.ajax("at", path.getSealStamp + layoutId); 
        }

        // Ver25
        export function getApprovalProcessingUseSetting(): JQueryPromise<viewmodel.model.ApprovalProcessingUseSetting> {
            return nts.uk.request.ajax("at", path.getApprovalProcessingUseSetting);
        }
        
        // Ver25 get daily attendance items
        export function getDailyAttendanceItems(): JQueryPromise<Array<viewmodel.model.AttributeOfAttendanceItem>> {
            return nts.uk.request.ajax("at", path.getDailyAttendanceItems);
        }

        // Ver25 get monthly attendance items
        export function getMonthlyAttendanceItems(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.getMonthlyAttendanceItems);
        }

        // Ver25 出勤簿の単一の項目
        export function getSingleAttendanceRecord(attendanceRecordKey: viewmodel.model.AttendanceRecordKey): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.getSingleAttendanceRecord, attendanceRecordKey);
        }

        // Ver25 加減算する項目
        export function getCalculateAttendanceRecordDto(attendanceRecordKey: viewmodel.model.AttendanceRecordKey): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.getCalculateAttendanceRecordDto, attendanceRecordKey);
        }

        // Ver25 出勤簿の出力項目設定
        export function getAttendanceRecordExportSetting(code : number, selectionType: number): JQueryPromise<any>{
            return nts.uk.request.ajax("at",path.getAttendanceRecordExportSetting + code + '/' + selectionType);
        }

    }

}
