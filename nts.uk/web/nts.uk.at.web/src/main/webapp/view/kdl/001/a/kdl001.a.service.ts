module nts.uk.at.view.kdl001.a {
    export module service {
        var paths: any = {
            retirePayItemSelect: "pr/core/retirement/payitem/findBycompanyCode",
            retirePayItemUpdate: "pr/core/retirement/payitem/update" 
        }
        
        export function retirePayItemSelect(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.retirePayItemSelect);
        }
        
        export function retirePayItemUpdate(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.retirePayItemUpdate, command);
        }
    }
}