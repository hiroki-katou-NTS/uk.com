module nts.uk.com.view.cmm011.v2.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    let servicePath = {
        getConfiguration: "bs/employee/wkpdep/get-configuration/{0}",
        getOperationRule: "bs/employee/operationrule/get-operation-rule",
        getAllWkpDepInfor: "bs/employee/wkpdep/get-wkpdepinfo/{0}/{1}",
        getAllWkpDepInforTree: "bs/employee/wkpdep/get-wkpdepinfo-tree/{0}/{1}",
        checkTotalWkpDepInfor: "bs/employee/wkpdep/check-total-wkpdepinfo/{0}/{1}"
    };

    export function getConfiguration(initMode): JQueryPromise<any> {
        let _path = format(servicePath.getConfiguration, initMode);
        return ajax("com", _path);
    }
    
    export function getOperationRule(): JQueryPromise<any> {
        return ajax("com", servicePath.getOperationRule);
    }

    export function getAllWkpDepInfor(initMode, histId): JQueryPromise<any> {
        let _path = format(servicePath.getAllWkpDepInfor, initMode, histId);
        return ajax("com", _path);
    }
    
    export function getAllWkpDepInforTree(initMode, histId): JQueryPromise<any> {
        let _path = format(servicePath.getAllWkpDepInforTree, initMode, histId);
        return ajax("com", _path);
    }
    
    export function checkTotalWkpDepInfor(initMode, histId): JQueryPromise<any> {
        let _path = format(servicePath.checkTotalWkpDepInfor, initMode, histId);
        return ajax("com", _path);
    }

}
