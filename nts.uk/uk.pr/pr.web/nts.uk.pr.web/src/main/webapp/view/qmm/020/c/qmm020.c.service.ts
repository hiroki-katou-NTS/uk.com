module nts.uk.pr.view.qmm020.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getStateLinkSettingCompanyById: "core/wageprovision/statementbindingsetting/getStateLinkSettingCompanyById",
        };

        export function getStateLinkSettingCompanyById(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateLinkSettingCompanyById);
        }

    }
}