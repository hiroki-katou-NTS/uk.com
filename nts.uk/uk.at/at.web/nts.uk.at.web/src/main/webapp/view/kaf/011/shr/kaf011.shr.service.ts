module nts.uk.at.view.kaf011.shr.service {

    import ajax = nts.uk.request.ajax;
    var paths: any = {
        start: "at/request/application/holidayshipment/start",
        changeWkType: "at/request/application/holidayshipment/change_work_type",
        changeDay: "at/request/application/holidayshipment/change_day",
        save: "at/request/application/holidayshipment/save",
        findById: "at/request/application/holidayshipment/find_by_id",
        update: "at/request/application/holidayshipment/update",
        start_c: "at/request/application/holidayshipment/start_c",
        holidayShipmentRemove: "at/request/application/holidayshipment/remove",
        holidayShipmentCancel: "at/request/application/holidayshipment/cancel",


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
    export function update(updateCmd: common.ISaveHolidayShipmentCommand) {
        return ajax(paths.update, updateCmd);
    }
    export function start_c(startParam: any) {
        return ajax(paths.start_c, startParam);
    }
    export function removeAbs(startParam: any) {
        return ajax(paths.holidayShipmentRemove, startParam);
    }
    export function cancelAbs(startParam: any) {
        return ajax(paths.holidayShipmentCancel, startParam);
    }


}