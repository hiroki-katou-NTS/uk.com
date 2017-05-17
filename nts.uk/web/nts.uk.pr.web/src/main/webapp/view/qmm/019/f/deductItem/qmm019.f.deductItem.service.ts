module qmm019.f.deductItem.service {
    var paths = {
        getItemSalary: "pr/core/itemdeduct/find/{0}",
    };
    export function getSalaryItem(itemCode): JQueryPromise<model.DeductItemModel> {
        let dfd = $.Deferred<model.DeductItemModel>();
        var _path = nts.uk.text.format(paths.getItemSalary, itemCode);
        nts.uk.request.ajax(_path).done(function(res: model.DeductItemModel) {
            dfd.resolve(res);
        })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();

    }
    export module model {
        export class DeductItemModel {
            deductAtr: number;
            errRangeLowAtr: number;
            errRangeLow: number;
            errRangeHighAtr: number;
            errRangeHigh: number;
            alRangeLowAtr: number;
            alRangeLow: number;
            alRangeHighAtr: number;
            alRangeHigh: number;
            memo: string;
        }
    }
}