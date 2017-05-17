module qmm019.f.salaryItem.service {
    var paths = {
        getItemSalary: "pr/core/itemsalary/find/{0}",
    };
    export function getSalaryItem(itemCode): JQueryPromise<model.ItemSalaryModel> {
        let dfd = $.Deferred<model.ItemSalaryModel>();
        var _path = nts.uk.text.format(paths.getItemSalary, itemCode);
        nts.uk.request.ajax(_path).done(function(res: model.ItemSalaryModel) {
            dfd.resolve(res);
        })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();

    }
    export module model {
        export class ItemSalaryModel {
            taxAtr: number;
            socialInsAtr: number;
            laborInsAtr: number;
            fixPayAtr: number;
            applyForAllEmpFlg: number;
            applyForMonthlyPayEmp: number;
            applyForDaymonthlyPayEmp: number;
            applyForDaylyPayEmp: number;
            applyForHourlyPayEmp: number;
            avePayAtr: number;
            errRangeLowAtr: number;
            errRangeLow: number;
            errRangeHighAtr: number;
            errRangeHigh: number;
            alRangeLowAtr: number;
            alRangeLow: number;
            alRangeHighAtr: number;
            alRangeHigh: number;
            memo: string;
            limitMnyAtr: number;
            limitMnyRefItemCode: string;
            limitMny: number;
        }
    }
}