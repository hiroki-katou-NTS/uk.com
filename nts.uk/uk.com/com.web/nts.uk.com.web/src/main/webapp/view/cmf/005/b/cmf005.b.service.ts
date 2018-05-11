module nts.uk.com.view.cmf005.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        
        var paths = {
            addManualSetDel: "ctx/sys/assist/app/addManualSetDel"
        }

        export function getConditionList(): JQueryPromise<any> {
            return ajax('com', paths.getConditionList);
        };
        
        export function addManualSetDel(manualSet: any): JQueryPromise<any> {
            return nts.uk.request.ajax('com', paths.addManualSetDel, manualSet);
        };
        
    }
}
