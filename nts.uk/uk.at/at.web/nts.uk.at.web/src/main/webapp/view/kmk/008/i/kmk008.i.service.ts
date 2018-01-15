module nts.uk.at.view.kmk008.i {
    export module service {
        export class Service {
            paths = {
                getData: "at/record/agreementOperationSetting/getAgreementOperationSetting",
                insertData: "at/record/agreementOperationSetting/addAgreementOperationSetting",
                updateData: "at/record/agreementOperationSetting/updateAgreementOperationSetting",
            };
            constructor() { }

            getData(): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.getData);
            };

            insertData(OperationSettingModelUpdate: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.insertData, OperationSettingModelUpdate);
            };

            updateData(OperationSettingModelUpdate: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateData, OperationSettingModelUpdate);
            };

        }
    }
}
