module nts.uk.com.view.kwr002.e {
    export module service {

        var path: any = {
            getAttndRecList: "at/function/attendancerecord/item/getAttndRecItem/",
            getCalculateAttndRecInfo: "at/function/attendancerecord/item/getCalculateAttndRecInfo",
            getAllAttndByAtrAndType: "at/function/attendancerecord/item/getAttndRecByAttndTypeKey"
        }

        export function findAttndRecByScreen(screenUseAtr: number): JQueryPromise<Array<model.AttendanceRecordItemDto>> {
            return nts.uk.request.ajax("at", path.getAttndRecList + screenUseAtr);
        }


    }
}