module nts.uk.pr.view.qmm031.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getInsuranceType: "ctx/pr/yearend/insuranceType/getInsuranceType/{0}",
        addInsuranceType: "ctx/pr/yearend/insuranceType/addInsuranceType",
        updateInsuranceType: "ctx/pr/yearend/insuranceType/updateInsuranceType",
        removeInsuranceType: "ctx/pr/yearend/insuranceType/removeInsuranceType"

    }

    export function getInsuranceType(lifeInsuranceCode: string): JQueryPromise<any> {
        var _path = format(paths.getInsuranceType, lifeInsuranceCode);
        return ajax('pr', _path);
    };

    export function addInsuranceType(command: any): JQueryPromise<any> {
        return ajax('pr', paths.addInsuranceType, command);
    };

    export function updateInsuranceType(command: any): JQueryPromise<any> {
        return ajax('pr', paths.updateInsuranceType, command);
    };

    export function removeInsuranceType(command: any): JQueryPromise<any> {
        return ajax('pr', paths.removeInsuranceType, command);
    };
}
