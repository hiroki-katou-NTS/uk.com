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
            testAnotherPath: "at/function/attendancerecord/item/updateSingleAttndRec"
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
        export function deleteSingleAttendance(singleAttendanceRecord: viewmodel.model.SingleAttendanceRecord) {
            return nts.uk.request.ajax("at", path.deleteSingleAttendanceRecord, singleAttendanceRecord);
        }
        /**
         * get list attendanceReocrdItems by screenUseAtr
         */
        export function getAttendanceRecordItemsByScreenUseAtr(screenUseAtr: number): JQueryPromise<Array<viewmodel.model.AttendanceRecordItem>> {
            return nts.uk.request.ajax("at", path.getAttendanceRecordItemsByScreenUseAtr + screenUseAtr);
        }
        export function testAnotherPath(attendanceRecordKey: viewmodel.model.SingleAttendanceRecordSaveCommand) {
            return nts.uk.request.ajax("at", path.testAnotherPath, attendanceRecordKey);
        }
    }
}