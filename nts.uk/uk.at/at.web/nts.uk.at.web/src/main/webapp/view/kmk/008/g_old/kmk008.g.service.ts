module nts.uk.at.view.kmk008.g {
    export module service {
        var paths: any = {
            getMonth: "at/record/agreementMonthSetting/getAgreementMonthSetting",
            getYear: "at/record/agreementYearSetting/getAgreementYearSetting",

        };

        export function getMonth(employeeId: string): JQueryPromise<Array<model.MonthDto>> {
            var dfd = $.Deferred<Array<model.MonthDto>>();
            nts.uk.request.ajax("at", paths.getMonth + "/" + employeeId)
                .done(function(res: Array<model.MonthDto>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        export function getYear(employeeId: string): JQueryPromise<Array<model.YearDto>> {
            var dfd = $.Deferred<Array<model.YearDto>>();
            nts.uk.request.ajax("at", paths.getYear + "/" + employeeId)
                .done(function(res: Array<model.YearDto>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
    }

    export module model {
        export class MonthDto {
            yearMonthValue: string;
            errorOneMonth: string;
            alarmOneMonth: string;
        }

        export class YearDto {
            yearValue: string;
            errorOneYear: string;
            alarmOneYear: string;
        }
    }
}