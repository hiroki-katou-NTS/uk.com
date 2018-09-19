module nts.uk.pr.view.qmm008.e.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        startScreen: "ctx/pr/core/socialinsurance/salaryhealth/start",
        checkProcess: "ctx/sys/assist/datarestoration/getServerPrepare"
    }
    export function startScreen(command): JQueryPromise<any> {
        return ajax(paths.startScreen, command);
    }
    export function checkProcess(processId): JQueryPromise<any> {
        return ajax("com", paths.checkProcess, processId);
    }
}