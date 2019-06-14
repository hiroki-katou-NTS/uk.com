module nts.uk.pr.view.qmm020.k {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            editHistoryProcess: "core/wageprovision/statementbindingsetting/editHistoryProcess"

        };
        export function editHistoryProcess(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.editHistoryProcess,data);
        }

    }
}