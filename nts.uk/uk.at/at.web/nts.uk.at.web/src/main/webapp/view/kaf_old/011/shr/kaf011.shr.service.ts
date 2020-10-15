module nts.uk.at.view.kaf011.shr.service {

    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths: any = {
        start: "at/request/application/holidayshipment/start",
        changeWkType: "at/request/application/holidayshipment/change_work_type",
        changeDay: "at/request/application/holidayshipment/change_day",
        save: "at/request/application/holidayshipment/save",
        findById: "at/request/application/holidayshipment/find_by_id",
        startPageBRefactor: "at/request/application/holidayshipment/startPageBRefactor",
        update: "at/request/application/holidayshipment/update",
        start_c: "at/request/application/holidayshipment/start_c",
        holidayShipmentRemove: "at/request/application/holidayshipment/remove",
        holidayShipmentCancel: "at/request/application/holidayshipment/cancel",
        changeAbsDate: "at/request/application/holidayshipment/change_abs_date",
        changeAbsDateToHoliday: "at/request/application/holidayshipment/change_abs_date_to_holiday",
        getSelectedWorkingHours: "at/request/application/holidayshipment/get_selected_working_hours",
        startPageARefactor: "at/request/application/holidayshipment/startPageARefactor",
        checkBeforeRegister: "at/request/application/holidayshipment/processBeforeRegister_New",
        changeWorkingDateRefactor: "at/request/application/holidayshipment/changeWorkingDateRefactor",
        changeHolidayDateRefactor: "at/request/application/holidayshipment/changeHolidayDateRefactor"
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
    export function startPageBRefactor(appParam) {
        return ajax(paths.startPageBRefactor, appParam);
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

    export function changeAbsDate(saveCmd: common.ISaveHolidayShipmentCommand) {
        return ajax(paths.changeAbsDate, saveCmd);
    }

    export function changeAbsDateToHoliday(saveCmd: common.ISaveHolidayShipmentCommand) {
        return ajax(paths.changeAbsDateToHoliday, saveCmd);
    }
    export function getSelectedWorkingHours(changeWkTypeParam) {
        return ajax(paths.getSelectedWorkingHours, changeWkTypeParam);
    }
    
    export function startPageARefactor(param) {
        return ajax(paths.startPageARefactor, param);
    }

    export function checkBeforeRegister(param) {
        return ajax(paths.checkBeforeRegister, param);
    }

    export function changeWorkingDateRefactor(param) {
        return ajax(paths.changeWorkingDateRefactor, param);
    }

    export function changeHolidayDateRefactor(param) {
        return ajax(paths.changeHolidayDateRefactor, param);
    }
}