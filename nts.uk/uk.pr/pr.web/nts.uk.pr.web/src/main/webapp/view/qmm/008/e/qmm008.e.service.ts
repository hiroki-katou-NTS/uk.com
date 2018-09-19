module nts.uk.pr.view.qmm008.e.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        startScreen: "ctx/pr/core/socialinsurance/salaryhealth/start",
        update: "ctx/pr/core/socialinsurance/salaryhealth/update/{0}"
    }
    export function startScreen(command): JQueryPromise<any> {
        return ajax(paths.startScreen, command);
    }
    export function update(historyId): JQueryPromise<any> {
        let _path = format(paths.update, historyId);
        return ajax("com",_path);
    }
}