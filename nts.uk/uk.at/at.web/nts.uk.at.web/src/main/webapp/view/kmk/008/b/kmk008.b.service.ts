module nts.uk.at.view.kmk008.b {
    export module service {

        var paths: any = {
            getData: "at/record/agreementUnitSetting/getAgreementUnitSetting",
        };

        export function getData(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getData);
        }

    }
}
