module nts.uk.com.view.cmm011.v2.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var servicePath: any = {
        getConfiguration: "bs/employee/wkpdep/get-configuration/{0}",
        getAllWkpDepInfor: "bs/employee/wkpdep/get-wkpdepinfo/{0}/{1}"
    };

    export function getConfiguration(initMode): JQueryPromise<any> {
        let _path = format(servicePath.getConfiguration, initMode);
        return ajax("com", _path);
    }

    export function getAllWkpDepInfor(initMode, histId): JQueryPromise<any> {
        let _path = format(servicePath.getAllWkpDepInfor, initMode, histId);
        return ajax("com", _path);
    }

}
