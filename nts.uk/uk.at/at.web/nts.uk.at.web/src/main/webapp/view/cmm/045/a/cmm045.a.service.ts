module cmm045.a.service {
     var paths = {
         getApplicationList: "at/request/application/applist/getapplist",
         getApplicationDisplayAtr: "at/request/application/applist/get/appdisplayatr",
         approvalListApp: "at/request/application/applist/approval",
         getHdSetInfo: "at/request/vacation/setting/hdapp",
         reflectListApp: "at/request/application/applist/reflect-list"
    }

    /**
    * get List Application
    */
    export function getApplicationList(param: any): JQueryPromise<Array<any>>{
        return nts.uk.request.ajax("at", paths.getApplicationList,param);
    }  
    /**
     * get list Application display atr
     */
    export function getApplicationDisplayAtr(): JQueryPromise<Array<any>>{
        return nts.uk.request.ajax("at", paths.getApplicationDisplayAtr);
    }
    /**
     * approval list Application
     */
    export function approvalListApp(data: any): JQueryPromise<any>{
        return nts.uk.request.ajax("at", paths.approvalListApp, data);
    }
    export function reflectListApp(lstID: any): JQueryPromise<any>{
        return nts.uk.request.ajax("at", paths.reflectListApp, lstID);
    }
}