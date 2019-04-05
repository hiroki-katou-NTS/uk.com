module nts.uk.com.view.cmm011.v2.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var servicePath: any = {
        getAllConfiguration: "bs/employee/wkpdep/get-all-configuration/{0}"
    };

    export function getAllConfiguration(initMode): JQueryPromise<any> {
        let _path = format(servicePath.getAllConfiguration, initMode);
        return ajax("com", _path);
    }

}
