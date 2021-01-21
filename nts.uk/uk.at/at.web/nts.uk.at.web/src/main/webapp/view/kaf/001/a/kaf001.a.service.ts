module kaf001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths: any = {
        getAllProxyApplicationSetting : "at/request/application/setting/proxy/findAll",
        selectApplicationByType       : "at/request/application/proxy/find",
        getListNameDis                : "at/request/application/displayname/disp",
        getAppNameDisp                : "at/request/application/proxy/findName",
        checkEmployee                 : "at/request/application/proxy/checkValid"
    }

    export function getAllProxyApplicationSetting(): JQueryPromise<any> {
        return ajax("at", paths.getAllProxyApplicationSetting);
    }

    export function selectApplicationByType(proxyParamFind: any): JQueryPromise<any> {
        return ajax("at", paths.selectApplicationByType, proxyParamFind);
    }
    //get name display from domain 
    export function getListNameDis(): JQueryPromise<any> {
        return ajax("at", paths.getListNameDis);
    }

    export function getAppDispName(): JQueryPromise<any> {
        return ajax("at", paths.getAppNameDisp);
    }

    export function checkEmployee(param: any) : JQueryPromise<any> {
        return ajax("at", paths.checkEmployee, param);
    }
}