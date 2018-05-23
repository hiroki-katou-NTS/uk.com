module nts.uk.com.view.kaf001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths: any = {
        getAllProxyApplicationSetting : "at/request/application/setting/proxy/findAll",
        selectApplicationByType       : "at/request/application/proxy/find"
    }

    export function getAllProxyApplicationSetting(): JQueryPromise<any> {
        return ajax("at", paths.getAllProxyApplicationSetting);
    }

    export function selectApplicationByType(proxyParamFind): JQueryPromise<any> {
        return ajax("at", paths.selectApplicationByType, proxyParamFind);
    }

}