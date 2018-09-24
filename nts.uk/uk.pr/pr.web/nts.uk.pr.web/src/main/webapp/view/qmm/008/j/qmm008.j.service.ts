module nts.uk.pr.view.qmm008.j.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        start: "ctx/pr/core/socialinsurance/salaryhealth/startwelfarestandard",
        update: "ctx/pr/core/socialinsurance/salaryhealth/updatewelfarestandard",
        count: "ctx/pr/core/socialinsurance/salaryhealth/countwelfarestandard"
    }
    export function start(command): JQueryPromise<any> {
        return ajax(paths.start, command);
    }
    export function update(command): JQueryPromise<any> {
        return ajax("pr", paths.update, command);
    }
    export function count(command): JQueryPromise<any> {
        return ajax(paths.start, command);
    }
}