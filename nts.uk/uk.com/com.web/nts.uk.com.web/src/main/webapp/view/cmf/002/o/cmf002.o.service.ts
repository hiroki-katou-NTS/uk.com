module nts.uk.com.view.cmf002.o {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getConditionList: "exio/condset/getAllCondition",
            getExOutSummarySetting: "exio/condset/getExOutSummarySetting/{0}"
        }

        export function getConditionList(): JQueryPromise<any> {
            return ajax('com', paths.getConditionList);
        };        
       
        export function getExOutSummarySetting(conditionSetCd: string): JQueryPromise<any> {
            let _path = format(paths.getExOutSummarySetting, conditionSetCd);
            return ajax('com', _path);
        }
    }
}
