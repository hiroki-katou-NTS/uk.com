module nts.uk.pr.view.qmm042.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths: any = {
        employeeSalaryUnitPriceHistory :"ctx/pr/core/wageprovision/empsalunitprice/getEmployeeSalaryUnitPriceHistory",
        salaryPerUnitPriceName :"ctx/pr/core/wageprovision/empsalunitprice/getSalaryPerUnitPriceName",
        employeeReferenceDate :"ctx.pr.core.ws.wageprovision.individualwagecontract/employeeReferenceDate",
    }

    export function salaryPerUnitPriceName(): JQueryPromise<any> {
        return ajax('pr', paths.salaryPerUnitPriceName);
    }

    export function employeeSalaryUnitPriceHistory(command): JQueryPromise<any> {
        return ajax('pr', paths.employeeSalaryUnitPriceHistory,command);
    }

    export function employeeReferenceDate(): JQueryPromise<any> {
        return ajax('pr', paths.employeeReferenceDate);
    }

    export function empSalUnitUpdateAll(command):JQueryPromise<any>{
        return ajax('pr', paths.employeeSalaryUnitPriceHistory,command);
    }


}