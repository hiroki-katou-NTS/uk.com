module nts.uk.pr.view.qmm037.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getPersonalMoneyName: "ctx/pr/core/ws/wageprovision/individualwagecontract/getPersonalMoneyName/{0}",
        addSalIndAmountName: "ctx/pr/core/ws/wageprovision/individualwagecontract/addSalIndAmountName",
        updateSalIndAmountName: "ctx/pr/core/ws/wageprovision/individualwagecontract/updateSalIndAmountName",
        removeSalIndAmountName: "ctx/pr/core/ws/wageprovision/individualwagecontract/removeSalIndAmountName"
    };

    export function getPersonalMoneyName(cateIndicator: number): JQueryPromise<any> {
        return ajax("pr", format(paths.getPersonalMoneyName, cateIndicator));
    }
    export function addSalIndAmountName(command: any): JQueryPromise<any> {
        return ajax('pr', paths.addSalIndAmountName, command);
    }
    export function updateSalIndAmountName(command: any): JQueryPromise<any> {
        return ajax('pr', paths.updateSalIndAmountName, command);
    }
    export function removeSalIndAmountName(command: any): JQueryPromise<any> {
        return ajax('pr', paths.removeSalIndAmountName, command);
    }
}
