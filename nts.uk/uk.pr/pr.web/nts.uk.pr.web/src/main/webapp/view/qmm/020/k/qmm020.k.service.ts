module nts.uk.pr.view.qmm020.k {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getStateLinkSettingCompanyById: "core/wageprovision/statementbindingsetting/deleteStateCorrelationHis",

        };
        export function deleteStateCorrelationHis(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.deleteStateCorrelationHis,data);
        }

    }
}