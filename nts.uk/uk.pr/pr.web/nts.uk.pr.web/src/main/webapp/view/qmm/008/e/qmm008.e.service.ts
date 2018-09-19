module nts.uk.pr.view.qmm008.e.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        startScreen: "ctx/pr/core/socialinsurance/salaryhealth/start",
        update: "ctx/pr/core/socialinsurance/salaryhealth/update",
        count: "ctx/pr/core/socialinsurance/salaryhealth/count"
    }
    export function startScreen(command): JQueryPromise<any> {
        return ajax(paths.startScreen, command);
    }
    export function update(command): JQueryPromise<any> {
        return ajax("pr",paths.update, command);
    }
    export function count(command): JQueryPromise<any> {
        return ajax("pr",paths.count, command);
    }
}