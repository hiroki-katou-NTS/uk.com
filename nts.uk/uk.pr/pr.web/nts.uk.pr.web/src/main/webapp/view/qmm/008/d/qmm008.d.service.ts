module nts.uk.pr.view.qmm008.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        defaultData: "ctx/pr/core/socialinsurance/socialinsuranceoffice/start",
        checkProcess: "ctx/sys/assist/datarestoration/getServerPrepare"
    }
    export function defaultData(): JQueryPromise<any> {
        return ajax("pr",paths.defaultData);
    }
    export function checkProcess(processId): JQueryPromise<any> {
        return ajax("com", paths.checkProcess, processId);
    }
}