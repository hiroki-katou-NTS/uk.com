module nts.uk.pr.view.qmm020.l {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getStateUseUnitSettingById: "core/wageprovision/statementbindingsetting/getStateUseUnitSettingById",
            update: "core/wageprovision/statementbindingsetting/update",
        };

        export function getStateUseUnitSettingById(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.getStateUseUnitSettingById);
        }

        export function update(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(path.update,data);
        }

    }
}