module nts.uk.at.view.kaf011.shr.service {

    import ajax = nts.uk.request.ajax;
    var paths: any = {
        start: "at/request/application/holidayshipment/start",
        changeWkType: "at/request/application/holidayshipment/change_work_type",
        changeDay: "at/request/application/holidayshipment/change_day",
        save: "at/request/application/holidayshipment/save",
        findById: "at/request/application/holidayshipment/find_by_id",

    }

    export function start(startParam: any) {
        return ajax(paths.start, startParam);
    }

    export function changeWkType(changeWkTypeParam) {

        return ajax(paths.changeWkType, changeWkTypeParam);
    }

    export function changeDay(changeDayParam) {

        return ajax(paths.changeDay, changeDayParam);
    }
    export function save(saveCmd: common.ISaveHolidayShipmentCommand) {
        return ajax(paths.save, saveCmd);
    }
    export function findById(appParam) {
        return ajax(paths.findById, appParam);
    }


}