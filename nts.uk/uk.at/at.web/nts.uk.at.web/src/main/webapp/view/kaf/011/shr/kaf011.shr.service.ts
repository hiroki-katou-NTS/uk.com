module nts.uk.at.view.kaf011.shr.service {

    import ajax = nts.uk.request.ajax;
    var paths: any = {
        start: "at/request/application/holidayshipment/start",
    }

    export function start(startParam: any) {
        return ajax(paths.start, startParam);
    }


}