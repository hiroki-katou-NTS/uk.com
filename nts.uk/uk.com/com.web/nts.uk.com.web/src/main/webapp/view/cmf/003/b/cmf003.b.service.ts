module nts.uk.com.view.cmf003.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        
        var paths = {
            getConditionList: "exio/condset/getAllCondition",
            addMalSet: "ctx/sys/assist/app/addMalSet"
        }

        export function getConditionList(): JQueryPromise<any> {
            return ajax('com', paths.getConditionList);
        };
        
        export function addMalSet(manualSet: any): JQueryPromise<any> {
            return nts.uk.request.ajax('com', paths.addMalSet, manualSet);
        };
       
    }
}
