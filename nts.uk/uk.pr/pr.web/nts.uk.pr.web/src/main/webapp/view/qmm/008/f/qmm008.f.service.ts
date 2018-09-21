module nts.uk.pr.view.qmm008.f.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        init: "ctx/pr/core/socialinsurance/salaryhealth/startwelfare",
        checkProcess: "ctx/sys/assist/datarestoration/getServerPrepare"
    }
    export function init(command): JQueryPromise<any> {
        return ajax(paths.init, command);
    }
    export function checkProcess(processId): JQueryPromise<any> {
        return ajax("com", paths.checkProcess, processId);
    }
}