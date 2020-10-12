module nts.uk.at.view.kmk008.h {
    export module service {

        var paths: any = {
            getData: 'screen/at/kmk008/h/getInitDisplay', //"at/record/agreementUnitSetting/getAgreementUnitSetting",
            insertData: 'monthly/estimatedtime/unit/Register', //'"at/record/agreementUnitSetting/addAgreementUnitSetting",
            updateData: "at/record/agreementUnitSetting/updateAgreementUnitSetting",
        };

        export function getData(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getData);
        }

        export function updateData(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateData, command);
        }

        export function insertData(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.insertData, command);
        }

    }

}
