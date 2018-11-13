module nts.uk.pr.view.qmm031.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getLifeInsuranceData: "ctx/pr/yearend/lifeInsurance/getLifeInsuranceData",
        addLifeInsurance: "ctx/pr/yearend/lifeInsurance/addLifeInsurance",
        updateLifeInsurance: "ctx/pr/yearend/lifeInsurance/updateLifeInsurance",
        removeLifeInsurance: "ctx/pr/yearend/lifeInsurance/removeLifeInsurance",
        getInsuranceType: "ctx/pr/yearend/insuranceType/getInsuranceType/{0}",
        getEarthquake: "ctx/pr/yearend/earthquake/getEarthquake",
        addEarthquake: "ctx/pr/yearend/earthquake/addEarthquake",
        updateEarthquake: "ctx/pr/yearend/earthquake/updateEarthquake",
        removeEarthquake: "ctx/pr/yearend/earthquake/removeEarthquake",
    };

    export function getLifeInsuranceData(): JQueryPromise<any> {
        return ajax("pr", format(paths.getLifeInsuranceData));
    }

    export function addLifeInsurance(command: any): JQueryPromise<any> {
        return ajax('pr', paths.addLifeInsurance, command);
    }

    export function updateLifeInsurance(command: any): JQueryPromise<any> {
        return ajax('pr', paths.updateLifeInsurance, command);
    }

    export function removeLifeInsurance(command: any): JQueryPromise<any> {
        return ajax('pr', paths.removeLifeInsurance, command);
    }

    export function getInsuranceType(lifeInsuranceCode: string): JQueryPromise<any> {
        return ajax("pr", format(paths.getInsuranceType, lifeInsuranceCode));
    }

    export function getEarthquake(): JQueryPromise<any> {
        return ajax("pr", format(paths.getEarthquake));
    }

    export function addEarthquake(command: any): JQueryPromise<any> {
        return ajax('pr', paths.addEarthquake, command);
    }

    export function updateEarthquake(command: any): JQueryPromise<any> {
        return ajax('pr', paths.updateEarthquake, command);
    }

    export function removeEarthquake(command: any): JQueryPromise<any> {
        return ajax('pr', paths.removeEarthquake, command);
    }
}
