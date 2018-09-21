module nts.uk.pr.view.qmm008.f.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        init: "ctx/pr/core/socialinsurance/salaryhealth/startwelfare",
        update: "ctx/pr/core/socialinsurance/salaryhealth/updatewelfare",
        count: "ctx/pr/core/socialinsurance/salaryhealth/countwelfare"
    }
    export function init(command): JQueryPromise<any> {
        return ajax(paths.init, command);
    }
    export function update(command): JQueryPromise<any> {
        return ajax("pr", paths.update, command);
    }
    export function count(command): JQueryPromise<any> {
        return ajax(paths.count, command);
    }
}