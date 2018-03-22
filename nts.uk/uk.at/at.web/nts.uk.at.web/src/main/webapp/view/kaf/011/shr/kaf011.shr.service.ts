module nts.uk.at.view.kaf011.shr.service {

    import ajax = nts.uk.request.ajax;
    var paths: any = {
        start: "at/request/application/holidayshipment/start",
        changeWkType: "at/request/application/holidayshipment/change_work_type",
        changeDay: "at/request/application/holidayshipment/change_day"
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


}