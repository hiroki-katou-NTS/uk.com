module qmm019.f.attendItem.service {
    var paths = {
        getItemSalary: "pr/core/itemattend/find/{0}",
    };
    export function getAttendItem(itemCode): JQueryPromise<model.AttendItemModel> {
        let dfd = $.Deferred<model.AttendItemModel>();
        var _path = nts.uk.text.format(paths.getItemSalary, itemCode);
        nts.uk.request.ajax(_path).done(function(res: model.AttendItemModel) {
            dfd.resolve(res);
        })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();

    }
    export module model {
        export class AttendItemModel {
            avePayAtr: number;
            itemAtr: number;
            errRangeLowAtr: number;
            errRangeLow: number;
            errRangeHighAtr: number;
            errRangeHigh: number;
            alRangeLowAtr: number;
            alRangeLow: number;
            alRangeHighAtr: number;
            alRangeHigh: number;
            workDaysScopeAtr: number;
            memo: string;
        }
    }
}