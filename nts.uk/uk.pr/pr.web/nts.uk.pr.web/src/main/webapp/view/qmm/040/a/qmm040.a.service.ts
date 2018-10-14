module nts.uk.pr.view.qmm040.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths: any = {
        salIndAmountNameByCateIndicator :"ctx.pr.core.ws.wageprovision.individualwagecontract/allSalIndAmountNameflowCateIndicator/{0}",
        salIndAmountHisByPeValCode :"ctx.pr.core.ws.wageprovision.individualwagecontract/salIndAmountHisByPeValCode/{0}/{1}/{2}",
        salIndAmountUpdateAll :"ctx.pr.core.ws.wageprovision.individualwagecontract/salIndAmountUpdateAll",
        employeeReferenceDate :"ctx.pr.core.ws.wageprovision.individualwagecontract/employeeReferenceDate",
    }

    export function salIndAmountNameByCateIndicator(cateIndicator: number): JQueryPromise<any> {
        let _path = format(paths.salIndAmountNameByCateIndicator, cateIndicator);
        return ajax('pr', _path);
    }

    export function salIndAmountHisByPeValCode(perValCode:string,cateIndicator:number,salBonusCate:number): JQueryPromise<any> {
        let _path = format(paths.salIndAmountHisByPeValCode, perValCode,cateIndicator,salBonusCate);
        return ajax('pr', _path);
    }

    export function employeeReferenceDate(): JQueryPromise<any> {
        let _path = format(paths.employeeReferenceDate);
        return ajax('pr', _path);
    }
    export function salIndAmountUpdateAll(command): JQueryPromise<any> {
        let _path = format(paths.salIndAmountUpdateAll,command);
        return ajax('pr', _path);
    }

}