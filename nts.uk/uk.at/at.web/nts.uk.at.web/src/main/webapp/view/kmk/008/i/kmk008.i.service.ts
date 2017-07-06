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
                let _path = nts.uk.text.format(this.paths.getData);
                return nts.uk.request.ajax("at", _path);
            };

            insertData(OperationSettingModelUpdate: any): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.insertData, OperationSettingModelUpdate);
                return nts.uk.request.ajax("at", _path);
            };

            updateData(OperationSettingModelUpdate: any): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.updateData, OperationSettingModelUpdate);
                return nts.uk.request.ajax("at", _path);
            };

        }
    }
}
