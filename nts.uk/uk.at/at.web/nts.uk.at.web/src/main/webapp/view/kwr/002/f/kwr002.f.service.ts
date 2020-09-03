module nts.uk.com.view.kwr002.e {
    export module service {

        var path: any = {
            getAttndRecList: "at/function/attendancerecord/item/getAttndRecItem/",

        }

        export function findAttndRecByScreen(screenUseAtr: number): JQueryPromise<Array<model.AttendanceRecordItemDto>> {
            return nts.uk.request.ajax("at", path.getAttndRecList + screenUseAtr);
        }


    }
}