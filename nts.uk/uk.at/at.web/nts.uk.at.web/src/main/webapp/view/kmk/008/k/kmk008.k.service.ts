module nts.uk.at.view.kmk008.k {
    export module service {
        var paths: any = {
            getYear: "at/record/agreementYearSetting/getAgreementYearSetting",
            addYear: "at/record/agreementYearSetting/addAgreementYearSetting",
            updateYear: "at/record/agreementYearSetting/updateAgreementYearSetting",
            removeYear: "at/record/agreementYearSetting/removeAgreementYearSetting",
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
        
        export function addYear(command): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.addYear, command).done(function() {
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);
            });
            return dfd.promise();
        }

        export module model {
            export class YearDto {
                yearValue: string;
                errorOneYear: string;
                alarmOneYear: string;
            }
        }
    }
}
