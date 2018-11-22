module nts.uk.com.view.qmm020.i {
    export module service {
        var paths = {
            indiTiedStatAcquiProcess: "core/wageprovision/statementbindingsetting/indiTiedStatAcquiProcess"
        };
        export function indiTiedStatAcquiProcess(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.indiTiedStatAcquiProcess,data);
        }

    }
}