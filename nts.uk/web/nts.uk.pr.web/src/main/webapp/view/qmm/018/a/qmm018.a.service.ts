module qmm018.a.service {
    var paths: any = {
        averagePayItemSelect: "pr/core/averagepay/findByCompanyCode",
        averagePayItemSelectBySalary: "pr/core/averagepay/findByItemSalary",
        averagePayItemSelectByAttend: "pr/core/averagepay/findByItemAttend",
        averagePayItemInsert: "pr/core/averagepay/register",
        averagePayItemUpdate: "pr/core/averagepay/update"
    }
    
    /**
     * select average pay item
     */
    export function averagePayItemSelect(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.averagePayItemSelect);
    }
    
    /**
     * select items master by salary item code
     */
    export function averagePayItemSelectBySalary(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.averagePayItemSelectBySalary);
    }
    
    /**
     * select items master by attend item code
     */
    export function averagePayItemSelectByAttend(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.averagePayItemSelectByAttend);
    }
    
    /**
     * insert average pay item, salary items, attend items
     */
    export function averagePayItemInsert(command): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.averagePayItemInsert, command);
    }
    
    /**
     * update average pay item, salary items, attend items
     */
    export function averagePayItemUpdate(command): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.averagePayItemUpdate, command);
    }
}