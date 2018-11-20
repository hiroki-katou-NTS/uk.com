module nts.uk.pr.view.qmm041.a.service {
    import ajax = nts.uk.request.ajax;
    import model = nts.uk.pr.view.qmm041.share.model;

    let paths: any = {
        getInfoEmpLogin: "workflow/approvermanagement/workroot/getInforPsLogin",
        getWpName: "screen/com/kcp010/getLoginWorkPlace",
        getAllIndEmpSalUnitPriceHistory: "ctx/pr/core/wageprovision/empsalunitprice/getAllIndEmpSalUnitPriceHistory",
        getBaseDate: "ctx/pr/core/wageprovision/processdatecls/getBaseDate",
        getSalaryPerUnitPriceName: "ctx/pr/core/wageprovision/empsalunitprice/getSalaryPerUnitPriceName",
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

    export function getIndividualEmpSalUnitPrices(dto): JQueryPromise<any> {
        return ajax("pr", paths.getAllIndEmpSalUnitPriceHistory, dto);
    }

}