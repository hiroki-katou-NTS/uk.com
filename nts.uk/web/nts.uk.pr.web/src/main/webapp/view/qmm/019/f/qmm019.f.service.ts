module qmm019.f.service {
    var paths = {
        getItemsByCategory: "pr/core/item/findall/category/{0}",
        getItem: "pr/core/{0}/find/{1}",
        getLayoutMasterDetail: "pr/proto/layout/findlayoutdetail/{0}/{1}/{2}/{3}",
        getPersonalWages: "pr/proto/personalwage/findalls/{0}"
    };
    export function getItemsByCategory(categoryAtr: number): JQueryPromise<Array<model.ItemDetailModel>> {
        var dfd = $.Deferred<Array<any>>();
        var objectItem = { categoryAtr: categoryAtr };
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

    export function getLayoutMasterDetail(stmtCode: string, historyId: string, categoryAtr: number, itemCd: String): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        var _path = nts.uk.text.format(paths.getLayoutMasterDetail, stmtCode, historyId, categoryAtr, itemCd);
        nts.uk.request.ajax(_path)
            .done(function(res) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function getItem(categoryAtr, itemCode): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        var _path = nts.uk.text.format(paths.getItem, genAtrName(categoryAtr), itemCode);
        nts.uk.request.ajax(_path)
            .done(function(res) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    function genAtrName(categoryAtr: number) {
        let AtrName = ''
        switch (categoryAtr) {
            case 0: AtrName = 'itemsalary';
                break;
            case 1: AtrName = 'itemdeduct';
                break;
            case 2: AtrName = 'itemattend';
                break;
        }
        return AtrName;
    }

    export function getListPersonalWages(categoryAtr: number): JQueryPromise<Array<model.PersonalWageNameDto>> {
        var dfd = $.Deferred<Array<any>>();
        var _path = nts.uk.text.format(paths.getPersonalWages, categoryAtr);
        nts.uk.request.ajax(_path)
            .done(function(res: Array<any>) {
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

        export class PersonalWageNameDto {
            companyCode: string;
            categoryAtr: number;
            personalWageCode: string;
            personalWageName: string;
        }
    }
}