module nts.uk.at.view.ksm004.c.service {
    var paths: any = {
        getAllHoliday :"at/schedule/holiday/getAllHoliday"
    }
    export function getHolidayByDate(): JQueryPromise<Array<viewmodel.HolidayInfo>> {
        return nts.uk.request.ajax("at", paths.getAllHoliday, {});
    }
}