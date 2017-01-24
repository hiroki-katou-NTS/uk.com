module nts.uk.pr.view.qmm010.b {
    export module service {
        var paths: any = {
            findAllSocialInsuranceOffice: "pr/insurance/social/findall",
        };

        //Function connection service FindAll Social Insurance Office Service
        export function findAllSocialInsuranceOffice(): JQueryPromise<Array<SocialInsuranceOfficeInDto>> {
            var dfd = $.Deferred<Array<SocialInsuranceOfficeInDto>>();
            nts.uk.request.ajax(paths.findAllSocialInsuranceOffice)
                .done(function(res: Array<SocialInsuranceOfficeInDto>) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        export module model {
            //Import SocialInsuranceOffice =>  SocialInsuranceOfficeInDto
            export class SocialInsuranceOfficeInDto {
                /** The company code.*/
                companyCode: string;
                /** The code. officeCode*/
                code: string;
                /** The name. officeName*/
                name: string;

                //New SocialInsuranceOfficeInDto
                constructor(companyCode: string, code: string, name: string) {
                    this.companyCode = companyCode;
                    this.code = code;
                    this.name = name;
                }
            }
        }
    }
}