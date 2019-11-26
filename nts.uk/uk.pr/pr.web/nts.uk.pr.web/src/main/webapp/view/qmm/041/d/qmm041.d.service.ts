module nts.uk.pr.view.qmm041.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        processYearFromEmp: "ctx/pr/core/ws/wageprovision/individualwagecontract/processYearFromEmp/{0}",
        individualUnitPriceDisplay: "ctx/pr/core/wageprovision/empsalunitprice/individualUnitPriceDisplay"
    };

    export function processYearFromEmp(employmentCode: string): JQueryPromise<any> {
        return ajax("pr", format(paths.processYearFromEmp, employmentCode));
    }

    export function individualUnitPriceDisplay(dto): JQueryPromise<any> {
        return ajax("pr", paths.individualUnitPriceDisplay, dto);
    }
}