module nts.uk.pr.view.qmm042.a.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        updateUnitPrice: "ctx/pr/core/wageprovision/empsalunitprice/updateUnitPrice",
        salaryPerUnitPriceName: "ctx/pr/core/wageprovision/empsalunitprice/getSalaryPerUnitPriceNotAbolition",
        employeeReferenceDate: "ctx/pr/core/ws/wageprovision/individualwagecontract/employeeReferenceDate",
        employeeSalaryUnitPriceHistory: "ctx/pr/core/wageprovision/empsalunitprice/getEmployeeSalaryUnitPriceHistory",
    };

    export function salaryPerUnitPriceName(): JQueryPromise<any> {
        return ajax('pr', paths.salaryPerUnitPriceName);
    }

    export function employeeSalaryUnitPriceHistory(command): JQueryPromise<any> {
        return ajax('pr', paths.employeeSalaryUnitPriceHistory, command);
    }

    export function employeeReferenceDate(): JQueryPromise<any> {
        return ajax('pr', paths.employeeReferenceDate);
    }

    export function empSalUnitUpdateAll(command): JQueryPromise<any> {
        return ajax('pr', paths.updateUnitPrice, command);
    }
}