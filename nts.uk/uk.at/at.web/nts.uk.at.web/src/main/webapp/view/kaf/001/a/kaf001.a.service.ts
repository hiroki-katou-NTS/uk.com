module kaf001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths: any = {
        getAppNameDisp                : "at/request/application/proxy/findName",
        checkEmployee                 : "at/request/application/proxy/checkValid"
    }

    export function getAppDispName(): JQueryPromise<any> {
        return ajax("at", paths.getAppNameDisp);
    }

    export function checkEmployee(param: any) : JQueryPromise<any> {
        return ajax("at", paths.checkEmployee, param);
    }
}