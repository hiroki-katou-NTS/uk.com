module nts.uk.com.view.kwr002.d {
    export module service {
        /**
        * define path to service
        */
        var path: any = {
            getSingleAttendanceRecordInfo: "at/function/attendancerecord/item/getSingleAttndRecInfo",
            updateSingleAttendanceRecord: "at/function/attendancerecord/item/updateSingleAttndRec",
            deleteSingleAttendanceRecord: "at/function/attendancerecord/item/deleteSingleAttndRec"
        };
        
        /**
         * get singleAttendanceRecord by key
         */
        export function findSingleAttendanceRecord(attendanceRecordKey: viewmodel.model.AttendanceRecordKey) {
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
        export function deleteSingleAttendance(singleAttendanceRecord: viewmodel.model.SingleAttendanceRecord) {
            return nts.uk.request.ajax("at", path.deleteSingleAttendanceRecord, singleAttendanceRecord);
        }
    }
}