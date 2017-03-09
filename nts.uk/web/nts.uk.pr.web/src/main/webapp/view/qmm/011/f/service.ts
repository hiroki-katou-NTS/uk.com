module nts.uk.pr.view.qmm011.f {
    export module service {
        var paths: any = {
            deleteAccidentInsuranceRate: "pr/insurance/labor/accidentrate/delete",
            deleteUnemployeeInsurance: "pr/insurance/labor/unemployeerate/delete"
        };
        //connection service delete AccidentInsuranceRate
        export function deleteAccidentInsuranceRate(accidentInsuranceRateDeleteDto: model.AccidentInsuranceRateDeleteDto)
            : JQueryPromise<void> {
            //set up data respone
            var dfd = $.Deferred<void>();
            //set up data request
            var data = { accidentInsuranceRateDeleteDto: accidentInsuranceRateDeleteDto };
            //call service server
            nts.uk.request.ajax(paths.deleteAccidentInsuranceRate, data)
                .done(function(res: void) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();

        }
        //connection service delete UnemployeeInsurance
        export function deleteUnemployeeInsurance(unemployeeInsuranceDeleteDto: model.UnemployeeInsuranceDeleteDto)
            : JQueryPromise<void> {
            //set up data respone
            var dfd = $.Deferred<void>();
            //call server server
            nts.uk.request.ajax(paths.deleteUnemployeeInsurance, unemployeeInsuranceDeleteDto)
                .done(function(res: void) {
                    dfd.resolve(res);
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