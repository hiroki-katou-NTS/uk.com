module nts.uk.pr.view.qmm017.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        // all
        getFormulaElements: "ctx/pr/core/wageprovision/formula/getFormulaElement/{0}",
        // tab 1
        getStatementItemData: "ctx/pr/core/wageprovision/statementitem/getStatementItemData/{0}/{1}",
        getAllStatementItemData: "ctx/pr/core/wageprovision/statementitem/getAllStatementItemData/{0}/{1}",
        // tab 2
        getAllPayrollUnitPriceByYearMonth: "core/wageprovision/companyuniformamount/getAllPayrollUnitPriceByYearMonth/{0}",
        getPayrollUnitPriceSettingByYearMonth: "core/wageprovision/companyuniformamount/getPayrollUnitPriceHistoryByYearMonth/{0}/{1}",
        getUnitPriceDataByCode: "ctx/pr/core/wageprovision/unitpricename/getUnitPriceDataByCode/{0}",
        getAllUnitPriceName: "ctx/pr/core/wageprovision/unitpricename/getAllUnitPriceName/{0}",
        // tab 7
        getWageTableInfo: "ctx/pr/core/wageprovision/wagetable/get-wagetable-by-code/{0}",
    }

    export function getStatementItemData(categoryAtr: number, itemNameCd: string): JQueryPromise<any> {
        let _path = format(paths.getStatementItemData, categoryAtr, itemNameCd);
        return ajax('pr', _path);
    }

    export function getAllStatementItemData(categoryAtr: number, isdisplayAbolition: boolean): JQueryPromise<any> {
        let _path = format(paths.getAllStatementItemData, categoryAtr, isdisplayAbolition);
        return ajax('pr', _path);
    }

    export function getAllPayrollUnitPriceByYearMonth(yearMonth: number) : JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getAllPayrollUnitPriceByYearMonth, yearMonth);
        return nts.uk.request.ajax("pr", _path);
    }

    export function getAllUnitPriceName(isdisplayAbolition: boolean) : JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getAllUnitPriceName, isdisplayAbolition);
        return nts.uk.request.ajax("pr", _path);
    }
    export function getAllUnitPriceItem(itemCategory: number, isdisplayAbolition: boolean, yearMonth: number): JQueryPromise<any> {
        if (itemCategory == nts.uk.pr.view.qmm017.share.model.UNIT_PRICE_ITEM_CATEGORY.COMPANY_UNIT_PRICE_ITEM) return this.getAllPayrollUnitPriceByYearMonth(yearMonth);
        return this.getAllUnitPriceName(isdisplayAbolition);
    }

    export function getPayrollUnitPriceById(code: string, yearMonth) : JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getPayrollUnitPriceSettingByYearMonth, code, yearMonth);
        return nts.uk.request.ajax("pr", _path);
    }
    export function getUnitPriceDataByCode(code: string) : JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getUnitPriceDataByCode, code);
        return nts.uk.request.ajax("pr", _path);
    }

    export function getUnitPriceItemByCode(itemCategory: number, code: string, yearMonth: number): JQueryPromise<any> {
        if (itemCategory == nts.uk.pr.view.qmm017.share.model.UNIT_PRICE_ITEM_CATEGORY.COMPANY_UNIT_PRICE_ITEM) return this.getPayrollUnitPriceById(code, yearMonth);
        return this.getUnitPriceDataByCode(code);
    }

    export function getFormulaElements (yearMonth: number) : JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getFormulaElements, yearMonth);
        return nts.uk.request.ajax("pr", _path);
    }

    export function getWageTableInfo (wageTableCode) : JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getWageTableInfo, wageTableCode);
        return nts.uk.request.ajax("pr", _path);
    }
}
