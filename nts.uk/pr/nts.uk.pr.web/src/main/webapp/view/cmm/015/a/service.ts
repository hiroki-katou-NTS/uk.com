module cmm015.a.service {
    var paths = {
        getItemsByCategory: "pr/proto/item/findall/bycategory/{0}",
        getItem: "pr/proto/item/find/{categoryAtr}/{itemCode}",
        getLayoutMasterDetail: "pr/proto/layout/findlayoutdetail/{0}/{1}/{2}/{3}"
    }

    export function getItemsByCategory(categoryAtr: number): JQueryPromise<Array<model.ItemDetailModel>> {
        var dfd = $.Deferred<Array<any>>();
        var objectItem = {categoryAtr: categoryAtr};
        var _path = nts.uk.text.format(paths.getItemsByCategory, categoryAtr);
        nts.uk.request.ajax(_path)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function getLayoutMasterDetail(stmtCode: String, startYm: number, categoryAtr: number, itemCd: String): JQueryPromise<model.ItemDetailModel> {
        var dfd = $.Deferred<model.ItemDetailModel>();
        var objectItem = {stmtCode: stmtCode, startYm: startYm, categoryAtr: categoryAtr, itemCd: itemCd};
        var _path = nts.uk.text.format(paths.getLayoutMasterDetail, stmtCode, startYm, categoryAtr, itemCd);
        nts.uk.request.ajax(_path)
            .done(function(res: model.ItemDetailModel) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function getItem(categoryAtr: number, itemCode: string): JQueryPromise<model.ItemDetailModel> {
        var dfd = $.Deferred<model.ItemDetailModel>();
        var objectItem = {categoryAtr: categoryAtr, itemCode: itemCode};
        var _path = nts.uk.text.format(paths.getItem, categoryAtr, itemCode);
        nts.uk.request.ajax(_path)
            .done(function(res: model.ItemDetailModel) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export module model {
        export class ItemDetailModel {
            itemCode: string;
            itemAbName: string;
            categoryAtr: number;
            sumScopeAtr: number;
            commuteAtr: number;
            calculationMethod: number;
            distributeSet: number;
            distributeWay: number;
            personalWageCode: string;
            isUseHighError: number;
            errRangeHigh: number;
            isUseLowError: number;
            errRangeLow: number;
            isUseHighAlam: number;
            alamRangeHigh: number;
            isUseLowAlam: number;
            alamRangeLow: number;
            taxAtr: number;
        }
    }
}