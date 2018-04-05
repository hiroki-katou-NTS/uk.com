module nts.uk.at.view.kmk013.m {
    export module service {
        let paths: any = {
            findSetting: "record/workrule/specific/find",
            register: "record/workrule/specific/register",
        }
        export function findSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findSetting);
        }
        export function register(setting: any) {
            return nts.uk.request.ajax("at", paths.register, setting);
        }
    }
}