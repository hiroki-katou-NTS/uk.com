module nts.uk.pr.view.qmm011.f {
    export module service {
        var paths: any = {
            deleteAccidentInsuranceRate: "pr/insurance/labor/accidentrate/delete"
        };
        export function deleteAccidentInsuranceRate(accidentInsuranceRateDeleteDto: model.AccidentInsuranceRateDeleteDto): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { accidentInsuranceRateDeleteDto: accidentInsuranceRateDeleteDto };
            nts.uk.request.ajax(paths.deleteAccidentInsuranceRate, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();

        }
        export module model {
            export class AccidentInsuranceRateDeleteDto {
                code: string;
                version: number;
            }
        }
    }
}