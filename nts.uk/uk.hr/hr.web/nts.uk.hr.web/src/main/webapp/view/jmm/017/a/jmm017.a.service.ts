module nts.uk.hr.view.jmm017.a {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getGuidance: "guidance/getGuidance",
            saveGuideDispSetting: "guidance/saveGuidance"
        }

        export function getGuidance(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getGuidance);
        }
        export function saveGuideDispSetting(param: any): JQueryPromise<any> {
            return ajax(paths.saveGuideDispSetting, param);
        }

    }
}