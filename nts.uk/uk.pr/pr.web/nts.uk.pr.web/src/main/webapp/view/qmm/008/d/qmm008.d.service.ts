module nts.uk.pr.view.qmm008.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        defaultData: "ctx/pr/core/socialinsurance/socialinsuranceoffice/start",
        findByCode: "ctx/pr/core/socialinsurance/socialinsuranceoffice/findByCode/{0}"
    }
    export function defaultData(): JQueryPromise<any> {
        return ajax("pr",paths.defaultData);
    }
    export function findByCode(code : string) : JQueryPromise<any>{
        let _path = format(paths.findByCode, code);
        return ajax('pr', _path);
    }
}