module nts.uk.pr.view.qmm025.a {
    import ajax = nts.uk.request.ajax;
    export module service {
        var paths: any = {
            getEmpInfoDept: "transfer/rsdttaxpayee/getEmpInfoDept",
            getRsdtTaxPayAmount: "transfer/rsdttaxpayee/getRsdtTaxPayAmount",
            registerTaxPayAmount: "core/emprsdttaxinfo/amountinfo/registerTaxPayAmount",
            deleteTaxPayAmount: "core/emprsdttaxinfo/amountinfo/deleteTaxPayAmount"
        }

        export function getEmpInfoDept(param): JQueryPromise<any> {
            return ajax('pr', paths.getEmpInfoDept, param);
        }

        export function getRsdtTaxPayAmount(param): JQueryPromise<any> {
            return ajax('pr', paths.getRsdtTaxPayAmount, param);
        }

        export function registerTaxPayAmount(param): JQueryPromise<any> {
            return ajax('pr', paths.registerTaxPayAmount, param);
        }

        export function deleteTaxPayAmount(param): JQueryPromise<any> {
            return ajax('pr', paths.deleteTaxPayAmount, param);
        }
    }
}