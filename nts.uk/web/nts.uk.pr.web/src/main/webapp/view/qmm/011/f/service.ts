module nts.uk.pr.view.qmm011.f {
    import HistoryInsuranceRateDto = nts.uk.pr.view.qmm011.a.service.model.HistoryInsuranceRateDto;
    import MonthRange = nts.uk.pr.view.qmm011.a.service.model.MonthRange;
    import HistoryInfoDto = nts.uk.pr.view.qmm011.d.service.model.HistoryInfoDto;
    export module service {
        var paths: any = {
            updateUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/update",
            updateAccidentInsuranceRate: "pr/insurance/labor/accidentrate/update",
        };
        export function updateHistoryInfoUnemployeeInsurance(historyInfo: HistoryInfoDto): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { historyInfoDto: historyInfo, comanyCode: "CC0001" };
            nts.uk.request.ajax(paths.updateUnemployeeInsuranceRate, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();

        }
        export function updateHistoryInfoAccidentInsuranceRate(historyInfo: HistoryInfoDto): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { historyInfoDto: historyInfo, comanyCode: "CC0001" };
            nts.uk.request.ajax(paths.updateAccidentInsuranceRate, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();

        }
    }
}