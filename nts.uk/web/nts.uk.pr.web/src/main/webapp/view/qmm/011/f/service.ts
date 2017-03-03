module nts.uk.pr.view.qmm011.f {
    export module service {
        var paths: any = {
            deleteAccidentInsuranceRate: "pr/insurance/labor/accidentrate/delete",
            deleteUnemployeeInsurance: "pr/insurance/labor/unemployeerate/delete"
        };
        //connection service delete AccidentInsuranceRate
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
        //connection service delete UnemployeeInsurance
        export function deleteUnemployeeInsurance(unemployeeInsuranceDeleteDto: model.UnemployeeInsuranceDeleteDto): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.deleteUnemployeeInsurance, unemployeeInsuranceDeleteDto)
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
            export class UnemployeeInsuranceDeleteDto {
                code: string;
                version: number;
            }
        }
    }
}