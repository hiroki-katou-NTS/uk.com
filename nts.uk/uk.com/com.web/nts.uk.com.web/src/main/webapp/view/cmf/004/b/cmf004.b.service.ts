module nts.uk.com.view.cmf004.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        findPerformDataRecover: "ctx/sys/assist/datarestoration/findPerformDataRecover/{0}",
        findTableList: "ctx/sys/assist/datarestoration/findTableList/{0}",
        findDataRecoverySelection: "ctx/sys/assist/datarestoration/findDataRecoverySelection",
        obtainRecovery: "ctx/sys/assist/datarestoration/obtainRecovery",       
    }
    /**
     * get SSPMT_PERFORM_DAT_RECOVER
    */
    export function findPerformDataRecover(dataRecoveryProcessId: string): JQueryPromise<any> {
        let _path = format(paths.findPerformDataRecover, dataRecoveryProcessId);
        return ajax('com', _path);
    }

    /**
     * get SSPMT_TABLE_LIST
    */
    export function findTableList(dataRecoveryProcessId: string): JQueryPromise<any> {
        let _path = format(paths.findTableList, dataRecoveryProcessId);
        return ajax('com', _path);
    }
 
    /**
     * get for screen B
    */
    export function findDataRecoverySelection(paramSearch): JQueryPromise<any> {
        return ajax('com', paths.findDataRecoverySelection, paramSearch);
    }
     /**
      * send for screen E
     */
    export function obtainRecovery(paramObtainRecovery): JQueryPromise<any> {
        return nts.uk.request.ajax('com', paths.obtainRecovery, paramObtainRecovery);
    };
    
}