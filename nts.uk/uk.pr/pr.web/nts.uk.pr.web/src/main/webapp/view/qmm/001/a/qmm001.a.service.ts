module nts.uk.pr.view.qmm001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths : any = {
        getAllSalGenParaIdentification: "shared/payrollgeneralpurposeparameters/getAllSalGenParaIdentification",
        getSalGenParamOptions: "shared/payrollgeneralpurposeparameters/getSalGenParamOptions/{0}",
        getSalGenParaDateHistory: "shared/payrollgeneralpurposeparameters/getSalGenParaDateHistory/{0}",
        getSalGenParaYearMonthHistory: "shared/payrollgeneralpurposeparameters/getSalGenParaYearMonthHistory/{0}",
        getSalGenParaValue: "shared/payrollgeneralpurposeparameters/getSalGenParaValue/{0}",
        addSelectionProcess: "shared/payrollgeneralpurposeparameters/addSelectionProcess",
        getListHistory: "shared/payrollgeneralpurposeparameters/getListHistory"
    };

    export function getAllSalGenParaIdentification(): JQueryPromise<any> {
        return ajax(paths.getAllSalGenParaIdentification);
    }
    export function getSalGenParamOptions(param :any): JQueryPromise<any> {
        let _path = format(paths.getSalGenParamOptions, param);
        return ajax("pr", _path);
    }
    export function getSalGenParaDateHistory(param :any): JQueryPromise<any> {
        let _path = format(paths.getSalGenParaDateHistory, param);
        return ajax("pr", _path);
    }
    export function getSalGenParaYearMonthHistory(param :any): JQueryPromise<any> {
        let _path = format(paths.getSalGenParaYearMonthHistory, param);
        return ajax("pr", _path);
    }
    export function getSalGenParaValue(param :any): JQueryPromise<any> {
        let _path = format(paths.getSalGenParaValue, param);
        return ajax("pr", _path);
    }
    export function addSelectionProcess(data :any): JQueryPromise<any> {
        return ajax(paths.addSelectionProcess, data);
    }
    export function getListHistory(data :any): JQueryPromise<any> {
        return ajax(paths.getListHistory, data);
    }




}
