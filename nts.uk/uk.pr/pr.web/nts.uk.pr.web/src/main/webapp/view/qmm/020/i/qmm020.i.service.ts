module nts.uk.com.view.qmm020.i {
    export module service {
        var paths = {
            acquiProcess: "core/wageprovision/statementbindingsetting/indiTiedStatAcquiProcess"
        };
        export function acquiProcess(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.acquiProcess,data);
        }

    }
}