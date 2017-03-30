module qmm013.a.service {
    var paths: any = {
        getPersonalUnitPriceList: "pr/core/rule/employment/unitprice/personal/find/all",
        getPersonalUnitPriceListNoneDisplay: "pr/core/rule/employment/unitprice/personal/find/all/nonedisplay",
        getPersonalUnitPrice: "pr/core/rule/employment/unitprice/personal/find/",
        addPersonalUnitPrice: "pr/core/rule/employment/unitprice/personal/add",
        updatePersonalUnitPrice: "pr/core/rule/employment/unitprice/personal/update",
        removePersonalUnitPrice: "pr/core/rule/employment/unitprice/personal/remove"
    }
    
    /**
     * get data from database base-on property 'display' true/false
     */
    export function getPersonalUnitPriceList(display: boolean): JQueryPromise<any> {
        var path = display ? paths.getPersonalUnitPriceList : paths.getPersonalUnitPriceListNoneDisplay;
        return nts.uk.request.ajax(path);
    }
    
    /**
     * get data from database P_UNITPRICE
     */
    export function getPersonalUnitPrice(code): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getPersonalUnitPrice + "" + code);
    }
    
    /**
     * add data in database P_UNITPRICE
     */
    export function addPersonalUnitPrice(isCreated, data): JQueryPromise<any> {
        var path = isCreated ? paths.addPersonalUnitPrice : paths.updatePersonalUnitPrice;
        return nts.uk.request.ajax(path, data);
    }
    
    /**
     * remove data in database P_UNITPRICE
     */
    export function removePersonalUnitPrice(data): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.removePersonalUnitPrice, data);
    }
}