module nts.uk.pr.view.qmm039.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        processYearFromEmp: "ctx/pr/core/ws/wageprovision/individualwagecontract/processYearFromEmp/{0}",
        getPersonalMoneyName: "ctx/pr/core/ws/wageprovision/individualwagecontract/getPersonalMoneyName/{0}",
        salIndAmountHisDisplay: "ctx/pr/core/ws/wageprovision/individualwagecontract/salIndAmountHisDisplay",
        getEmploymentCode: "ctx/pr/shared/employeeaverwage/getEmploymentCode"
    };

    export function getEmploymentCode(employeeId) : JQueryPromise<any> {
        return ajax("pr", paths.getEmploymentCode, employeeId);
    }

    export function processYearFromEmp(employmentCode: string): JQueryPromise<any> {
        return ajax("pr", format(paths.processYearFromEmp, employmentCode));
    }

    export function getPersonalMoneyName(cateIndicator: number): JQueryPromise<any> {
        return ajax("pr", format(paths.getPersonalMoneyName, cateIndicator));
    }

    export function salIndAmountHisDisplay(dto): JQueryPromise<any> {
        return ajax("pr", paths.salIndAmountHisDisplay, dto);
    }
}