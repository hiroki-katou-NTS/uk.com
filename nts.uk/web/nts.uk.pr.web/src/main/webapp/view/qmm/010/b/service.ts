module nts.uk.pr.view.qmm010.b {
    export module service {
        export module model {
            //Import SocialInsuranceOffice =>  LaborInsuranceOffice
            export class SocialInsuranceOfficeInDTO {
                /** The company code.*/
                companyCode: string;
                /** The code. officeCode*/
                code: string;
                /** The name. officeName*/
                name: string;

                //New LaborInsuranceOfficeInDTO
                constructor(companyCode: string, code: string, name: string) {
                    this.companyCode = companyCode;
                    this.code = code;
                    this.name = name;
                }
            }
        }
    }
}