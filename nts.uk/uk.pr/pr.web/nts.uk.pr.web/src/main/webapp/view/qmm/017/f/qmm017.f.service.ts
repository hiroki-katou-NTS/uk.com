module nts.uk.pr.view.qmm017.f.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import standardAmountClsEnum = nts.uk.pr.view.qmm017.share.model.STANDARD_AMOUNT_CLS

    let paths = {
        getAllStatementItemData: "ctx/pr/core/wageprovision/statementitem/getAllStatementItemData/{0}/{1}",
        getAllPayrollUnitPriceByYearMonth: "core/wageprovision/companyuniformamount/getAllPayrollUnitPriceByYearMonth/{0}",
        getAllUnitPriceName: "ctx/pr/core/wageprovision/unitpricename/getAllUnitPriceName/{0}"
    }

    export function getTargetItemCodeList (standardAmountCls: number, yearMonth) : JQueryPromise<any> {
        if (standardAmountCls == standardAmountClsEnum.DEDUCTION_ITEM || standardAmountCls == standardAmountClsEnum.PAYMENT_ITEM){
            return getAllStatementItemData(standardAmountCls - 1, false);
        }
        if (standardAmountCls == standardAmountClsEnum.INDIVIDUAL_UNIT_PRICE_ITEM) return getAllUnitPriceName(false);
        return getAllPayrollUnitPriceByYearMonth(yearMonth);
    }

    export function getAllStatementItemData(categoryAtr: number, isdisplayAbolition: boolean): JQueryPromise<any> {
        let _path = format(paths.getAllStatementItemData, categoryAtr, isdisplayAbolition);
        return ajax('pr', _path);
    }

    export function getAllPayrollUnitPriceByYearMonth(yearMonth) : JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getAllPayrollUnitPriceByYearMonth, yearMonth);
        return nts.uk.request.ajax("pr", _path);
    }

    export function getAllUnitPriceName(isdisplayAbolition: boolean) : JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getAllUnitPriceName, isdisplayAbolition);
        return nts.uk.request.ajax("pr", _path);
    }
}
