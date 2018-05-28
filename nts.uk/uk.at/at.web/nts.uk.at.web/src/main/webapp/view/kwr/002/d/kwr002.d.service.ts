module nts.uk.com.view.kwr002.d {
    export module service {
        /**
        * define path to service
        */
        var path: any = {
            getSingleAttendanceRecordInfo: "at/function/attendancerecord/item/getSingleAttndRecInfo",
            updateSingleAttendanceRecord: "at/function/attendancerecord/item/updateSingleAttndRec",
            deleteSingleAttendanceRecord: "at/function/attendancerecord/item/deleteSingleAttndRec",
            getAttendanceRecordItemsByScreenUseAtr: "at/function/attendancerecord/item/getAttndRecItem/",
            getAllAttendanceDaily: "at/function/attendancerecord/item/getAllAttndDaily",    
            getAllAttndByAtrAndType: "at/function/attendancerecord/item/getAttndRecByAttndTypeKey"
        };

        /**
         * get singleAttendanceRecord by key
         */
        export function findSingleAttendanceRecord(attendanceRecordKey: viewmodel.model.AttendanceRecordKey): JQueryPromise<viewmodel.model.SingleAttendanceRecord> {
            return nts.uk.request.ajax("at", path.getSingleAttendanceRecordInfo, attendanceRecordKey);
        }

        /**
         * update singleAttendanceRecord
         */
        export function updateSingleAttendance(singleAttendanceRecord: viewmodel.model.SingleAttendanceRecord) {
            return nts.uk.request.ajax("at", path.updateSingleAttendanceRecord, singleAttendanceRecord);
        }

        /**
         * delete singleAttendanceRecord
         */
        export function deleteSingleAttendance(singleAttendanceRecord: viewmodel.model.SingleAttendanceCommand) {
            return nts.uk.request.ajax("at", path.deleteSingleAttendanceRecord, singleAttendanceRecord);
        }
        /**
         * get list attendanceReocrdItems by screenUseAtr
         */
        export function getAttendanceRecordItemsByScreenUseAtr(screenUseAtr: number): JQueryPromise<Array<viewmodel.model.AttendanceRecordItem>> {
            return nts.uk.request.ajax("at", path.getAttendanceRecordItemsByScreenUseAtr + screenUseAtr);
        }
        /**
         * get all attendance item
         */
        export function getAllAttendanceDaily(): JQueryPromise<Array<viewmodel.model.AttendanceRecordItem>> {
            return nts.uk.request.ajax("at", path.getAllAttendanceDaily);
        }
        /**
         * get all attendance by srceenUseAtr and attendanceType
         */
        export function getAllAttndByAtrAndType(attendanceTypeKey:viewmodel.model.AttendanceTypeKey):JQueryPromise<Array<viewmodel.model.AttendanceRecordItem>> {
            return nts.uk.request.ajax("at", path.getAllAttndByAtrAndType,attendanceTypeKey);
        }

    }
}