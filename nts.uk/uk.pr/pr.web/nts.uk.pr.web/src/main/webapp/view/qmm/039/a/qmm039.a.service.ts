module nts.uk.pr.view.qmm039.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getInfoEmLogin: "workflow/approvermanagement/workroot/getInforPsLogin",
        getWpName: "screen/com/kcp010/getLoginWorkPlace",
        getPersonalMoneyName: "ctx/pr/core/ws/wageprovision/individualwagecontract/getPersonalMoneyName/{0}",
        getSalIndAmountHis: "ctx/pr/core/ws/wageprovision/individualwagecontract/getSalIndAmountHis",
        processYearFromEmp: "ctx/pr/core/ws/wageprovision/individualwagecontract/processYearFromEmp/{0}",
        updateHistory: "ctx/pr/core/ws/wageprovision/individualwagecontract/updateHistory",
        addHistory: "ctx/pr/core/ws/wageprovision/individualwagecontract/addHistory",
        getBaseDate: "ctx/pr/core/wageprovision/processdatecls/getBaseDate",
        getEmploymentCode: "ctx/pr/shared/employeeaverwage/getEmploymentCode",
    };

    export function getInfoEmLogin(): JQueryPromise<any> {
        return ajax("com", paths.getInfoEmLogin);
    }

    export function getWpName(): JQueryPromise<any> {
        return ajax("com", paths.getWpName);
    }

    export function getPersonalMoneyName(cateIndicator: number): JQueryPromise<any> {
        return ajax("pr", format(paths.getPersonalMoneyName, cateIndicator));
    }

    export function processYearFromEmp(employmentCode: string): JQueryPromise<any> {
        return ajax("pr", format(paths.processYearFromEmp, employmentCode));
    }

    export function getSalIndAmountHis(dto): JQueryPromise<any> {
        return ajax("pr", paths.getSalIndAmountHis, dto);
    }

    export function updateHistory(command): JQueryPromise<any> {
        return ajax("pr", paths.updateHistory, command);
    }

    export function addHistory(command): JQueryPromise<any> {
        return ajax("pr", paths.addHistory, command);
    }

    export function getBaseDate(): JQueryPromise<any> {
        return ajax("pr", paths.getBaseDate);
    }

    export function getEmploymentCode(employeeId) : JQueryPromise<any> {
        return ajax("pr", paths.getEmploymentCode, employeeId);
    }
}