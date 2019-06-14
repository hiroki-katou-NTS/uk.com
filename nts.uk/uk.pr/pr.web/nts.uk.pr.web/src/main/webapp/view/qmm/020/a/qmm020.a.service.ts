module nts.uk.pr.view.qmm020.a {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getStateUseUnitSettingById: "core/wageprovision/statementbindingsetting/getStateUseUnitSettingById",
        };

        export function getStateUseUnitSettingById(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateUseUnitSettingById);
        }

    }
}