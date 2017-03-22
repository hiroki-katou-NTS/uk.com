module qmm012.k {
    export module service {
        var paths: any = {
            getListByCompanyCode: "core/commutelimit/find/bycompanycode"
        }
        export function getCommutelimitsByCompanyCode(): JQueryPromise<Array<model.CommuteNoTaxLimitDto>> {
            var dfd = $.Deferred<Array<any>>();
            var _path = paths.getListByCompanyCode;
            nts.uk.request.ajax(_path)
                .done(function(res: Array<any>) {
                    dfd.resolve(res);
                }).fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export module model {
            // layout
            export class CommuteNoTaxLimitDto {
                companyCode: string;
                commuNoTaxLimitCode: string;
                commuNoTaxLimitName: string;
                commuNoTaxLimitValue: number;

                constructor(companyCode: string, commuNoTaxLimitCode: string, commuNoTaxLimitName: string,
                    commuNoTaxLimitValue: number) {
                    this.companyCode = companyCode;
                    this.commuNoTaxLimitCode = commuNoTaxLimitCode;
                    this.commuNoTaxLimitName = commuNoTaxLimitName;
                    this.commuNoTaxLimitValue = commuNoTaxLimitValue;
                }
            }

        }
    }

}