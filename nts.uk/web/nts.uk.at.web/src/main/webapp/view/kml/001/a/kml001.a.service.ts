module kml001.a.service {
    var paths: any = {
        retirePayItemSelect: "pr/core/retirement/payitem/findBycompanyCode", //qremt_Retire_Pay_Item_SEL_1
        retirePayItemUpdate: "pr/core/retirement/payitem/update" //qremt_Retire_Pay_Item_UPD_1
    }
    
    // qremt_Retire_Pay_Item_SEL_1 function
    export function retirePayItemSelect(): JQueryPromise<any> {
       return nts.uk.request.ajax(paths.retirePayItemSelect);
    }
    
    // qremt_Retire_Pay_Item_UPD_1 function
    export function retirePayItemUpdate(command): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.retirePayItemUpdate, command);
    }
    
}