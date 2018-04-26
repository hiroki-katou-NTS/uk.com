module nts.uk.com.view.cmf003.d {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getConditionList: "exio/condset/getAllCondition"
        }

        export function getConditionList(): JQueryPromise<any> {
            return ajax('com', paths.getConditionList);
        };        
       
    }
}
