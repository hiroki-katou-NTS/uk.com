module nts.uk.hr.view.jmm017.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getGuideCategory: "guidance/getGuideCategory",
            getGuideMessageList: "guidance/getGuideMessageList",
        }

        export function getGuideCategory(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getGuideCategory);
        }
        
        export function getGuideMessageList(param: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getGuideMessageList, param);
        }
    }
}