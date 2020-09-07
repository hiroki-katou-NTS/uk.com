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
            getDailyAttendanceTtems: "com/function/attendancerecord/export/getDailyAttendanceTtems",
            getSingleAttendanceRecord: "com/function/attendancerecord/export/getSingleAttendanceRecord",
            getCalculateAttendanceRecordDto: "com/function/attendancerecord/export/getCalculateAttendanceRecordDto"
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

        // Add in ver 25
        export function getApprovalProcessingUseSetting(): JQueryPromise<viewmodel.model.ApprovalProcessingUseSetting> {
            return nts.uk.request.ajax("at", path.getApprovalProcessingUseSetting);
        }
        
        // Add in Ver 25
        export function getDailyAttendanceTtem(): JQueryPromise<AttributeOfAttendanceItem> {
            return nts.uk.request.ajax("at", path.getDailyAttendanceTtems);
        }

        // Add in Ver 25
        export function getSingleAttendanceRecord(attendanceRecordKey: viewmodel.model.AttendanceRecordKey): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.getSingleAttendanceRecord, attendanceRecordKey);
        }

        // Add in Ver 25
        export function getCalculateAttendanceRecordDto(attendanceRecordKey: viewmodel.model.AttendanceRecordKey): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.getCalculateAttendanceRecordDto, attendanceRecordKey);
        }

        // Add in Ver 25
        export function getAttendanceRecordExportSetting(code : number): JQueryPromise<any>{
            return nts.uk.request.ajax("at",path.getAttendanceRecordExportSetting + code);
        }

    }

    export interface AttributeOfAttendanceItem {
        /** 勤怠項目ID<List> */
        attendanceItemIds: Array<number>;
        /** 勤怠項目名称 <List> */
        attendanceItemNames: Array<AttItemName>;
        /** 勤怠項目の属性<List> */
        attributes: Array<DailyAttendanceAtr>;
        /** List<マスタの種類> */
        masterTypes: Array<TypesMasterRelatedDailyAttendanceItem>;
        /** List<表示番号> */
        displayNumbers: Array<number>;
    }

    export interface AttItemName {
        attendanceItemId: number;
        attendanceItemName: string;
        attendanceItemDisplayNumber: number;
        userCanUpdateAtr: number;
        typeOfAttendanceItem: number | null;
        nameLineFeedPosition: number;
        frameCategory: number | null;
        authority: AttItemAuthority;
    }

    export interface AttItemAuthority {
        /** 利用する */
        toUse: boolean;
        /** 他人が変更できる */
        canBeChangedByOthers: boolean;
        /** 本人が変更できる */
        youCanChangeIt: boolean;
    }

    export interface TypesMasterRelatedDailyAttendanceItem {
        value: number;
	    name: string;
    }

    export interface DailyAttendanceAtr {
        value: number;
    }
}