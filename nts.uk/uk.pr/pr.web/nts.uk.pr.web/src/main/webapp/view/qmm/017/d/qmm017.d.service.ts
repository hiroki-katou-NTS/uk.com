module nts.uk.pr.view.qmm017.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        // tab 1
        getStatementItemData: "ctx/pr/core/wageprovision/statementitem/getStatementItemData/{0}/{1}",
        getAllStatementItemData: "ctx/pr/core/wageprovision/statementitem/getAllStatementItemData/{0}/{1}",
        //
        getAllPayrollUnitPriceByCID: "core/wageprovision/companyuniformamount/getAllPayrollUnitPriceByCID",
        getPayrollUnitPriceById: "core/wageprovision/companyuniformamount/getPayrollUnitPriceById/{0}",
        getUnitPriceDataByCode: "ctx/pr/core/wageprovision/unitpricename/getUnitPriceDataByCode/{0}",
        getAllUnitPriceName: "ctx/pr/core/wageprovision/unitpricename/getAllUnitPriceName/{0}"
    }

    export function getStatementItemData(categoryAtr: number, itemNameCd: string): JQueryPromise<any> {
        let _path = format(paths.getStatementItemData, categoryAtr, itemNameCd);
        return ajax('pr', _path);
    }

    export function getAllStatementItemData(categoryAtr: number, isdisplayAbolition: boolean): JQueryPromise<any> {
        let _path = format(paths.getAllStatementItemData, categoryAtr, isdisplayAbolition);
        return ajax('pr', _path);
    }

    export function getAllPayrollUnitPriceByCID() : JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getAllPayrollUnitPriceByCID);
    }

    export function getAllUnitPriceName(isdisplayAbolition: boolean) : JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getAllUnitPriceName, isdisplayAbolition);
        return nts.uk.request.ajax("pr", _path);
    }
    export function getAllUnitPriceItem(itemCategory: number, isdisplayAbolition: boolean): JQueryPromise<any> {
        if (itemCategory == nts.uk.pr.view.qmm017.share.model.UNIT_PRICE_ITEM_CATEGORY.COMPANY_UNIT_PRICE_ITEM) return this.getAllPayrollUnitPriceByCID();
        return this.getAllUnitPriceName(isdisplayAbolition);
    }

    export function getPayrollUnitPriceById(code: string) : JQueryPromise<any> {
        // TODO
        // unknown algorithm, service is temporary fixed
        let _path = nts.uk.text.format(paths.getPayrollUnitPriceById, code);
        return nts.uk.request.ajax("pr", _path);
    }
    export function getUnitPriceDataByCode(code: string) : JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getUnitPriceDataByCode, code);
        return nts.uk.request.ajax("pr", _path);
    }

    export function getUnitPriceItemByCode(itemCategory: number, code: string): JQueryPromise<any> {
        if (itemCategory == nts.uk.pr.view.qmm017.share.model.UNIT_PRICE_ITEM_CATEGORY.COMPANY_UNIT_PRICE_ITEM) return this.getPayrollUnitPriceById(code);
        return this.getUnitPriceDataByCode(code);
    }
}
