module nts.uk.pr.view.qmm041.a.service {
    import ajax = nts.uk.request.ajax;
    import model = nts.uk.pr.view.qmm041.share.model;
    import format = nts.uk.text.format;

    let paths = {
        getInfoEmpLogin: "workflow/approvermanagement/workroot/getInforPsLogin",
        getWpName: "screen/com/kcp010/getLoginWorkPlace",
        getAllIndEmpSalUnitPriceHistory: "ctx/pr/core/wageprovision/empsalunitprice/getAllIndEmpSalUnitPriceHistory",
        getBaseDate: "ctx/pr/core/wageprovision/processdatecls/getBaseDate",
        getSalaryPerUnitPriceName: "ctx/pr/core/wageprovision/empsalunitprice/getSalaryPerUnitPriceName",
        getEmploymentCode: "ctx/pr/shared/employeeaverwage/getEmploymentCode",
        processYearFromEmp: "ctx/pr/core/ws/wageprovision/individualwagecontract/processYearFromEmp/{0}",
        addHistory: "ctx/pr/core/wageprovision/empsalunitprice/addHistory",
        updateAmount: "ctx/pr/core/wageprovision/empsalunitprice/updateAmount",
    };

    export function getInfoEmpLogin(): JQueryPromise<any> {
        return ajax("com", paths.getInfoEmpLogin);
    }

    export function getWpName(): JQueryPromise<any> {
        return ajax("com", paths.getWpName);
    }

    export function getBaseDate(): JQueryPromise<any> {
        return ajax("pr", paths.getBaseDate);
    }

    export function getSalPerUnitPriceName(): JQueryPromise<model.SalPerUnitPriceName> {
        return ajax("pr", paths.getSalaryPerUnitPriceName);
    }

    export function getAllIndEmpSalUnitPriceHistory(dto): JQueryPromise<any> {
        return ajax("pr", paths.getAllIndEmpSalUnitPriceHistory, dto);
    }

    export function processYearFromEmp(employmentCode: string): JQueryPromise<any> {
        return ajax("pr", format(paths.processYearFromEmp, employmentCode));
    }

    export function getEmploymentCode(employeeId) : JQueryPromise<any> {
        return ajax("pr", paths.getEmploymentCode, employeeId);
    }

    export function addHistory(command) : JQueryPromise<any> {
        return ajax("pr", paths.addHistory, command);
    }

    export function updateAmount(command) : JQueryPromise<any> {
        return ajax("pr", paths.updateAmount, command);
    }
}