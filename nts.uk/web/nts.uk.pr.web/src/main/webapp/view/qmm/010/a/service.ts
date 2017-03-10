module nts.uk.pr.view.qmm010.a {

    import SocialInsuranceOfficeImportDto = nts.uk.pr.view.qmm010.b.service.model.SocialInsuranceOfficeImportDto;

    export module service {

        var paths: any = {
            findAllLaborInsuranceOffice: "ctx/pr/core/insurance/labor/findall",
            findLaborInsuranceOffice: "ctx/pr/core/insurance/labor/findLaborInsuranceOffice",
            addLaborInsuranceOffice: "ctx/pr/core/insurance/labor/add",
            updateLaborInsuranceOffice: "ctx/pr/core/insurance/labor/update",
            deleteLaborInsuranceOffice: "ctx/pr/core/insurance/labor/delete",
            findAllSocialInsuranceOffice: "pr/insurance/social/findall/detail"
        };

        //Function connection service FindAll Labor Insurance Office
        export function findAllLaborInsuranceOffice(): JQueryPromise<model.LaborInsuranceOfficeFindOutDto[]> {
            //set respone
            var dfd = $.Deferred<model.LaborInsuranceOfficeFindOutDto[]>();
            //call service server 
            nts.uk.request.ajax(paths.findAllLaborInsuranceOffice)
                .done(function(res: model.LaborInsuranceOfficeFindOutDto[]) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        //Function connection service FindAll Social Insurance Office Service
        export function findAllSocialInsuranceOffice(): JQueryPromise<SocialInsuranceOfficeImportDto[]> {
            //set respone
            var dfd = $.Deferred<SocialInsuranceOfficeImportDto[]>();
            //call service server 
            nts.uk.request.ajax(paths.findAllSocialInsuranceOffice)
                .done(function(res: SocialInsuranceOfficeImportDto[]) {
                    dfd.resolve(res);
                }).fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        //Function connection service findLaborInsuranceOffice By Code
        export function findLaborInsuranceOffice(officeCode: string)
            : JQueryPromise<model.LaborInsuranceOfficeDto> {
            //set respone
            var dfd = $.Deferred<model.LaborInsuranceOfficeDto>();
            //call service server
            nts.uk.request.ajax(paths.findLaborInsuranceOffice + "/" + officeCode)
                .done(function(res: model.LaborInsuranceOfficeDto) {
                    dfd.resolve(res);
                }).fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        //Function connection service add LaborInsuranceOffice
        export function addLaborInsuranceOffice(
            laborInsuranceOfficeDto: model.LaborInsuranceOfficeDto): JQueryPromise<any> {
            //set respone
            var dfd = $.Deferred<any>();
            //set up data request
            var data = { laborInsuranceOfficeDto: laborInsuranceOfficeDto };
            //call service server
            nts.uk.request.ajax(paths.addLaborInsuranceOffice, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                }).fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        //Function connection service update LaborInsuranceOffice
        export function updateLaborInsuranceOffice(
            laborInsuranceOfficeDto: model.LaborInsuranceOfficeDto): JQueryPromise<any> {
            //set up respone
            var dfd = $.Deferred<any>();
            //set up data request
            var data = { laborInsuranceOfficeDto: laborInsuranceOfficeDto };
            //call service server
            nts.uk.request.ajax(paths.updateLaborInsuranceOffice, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                }).fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        //Function connection service delete LaborInsuranceOffice  
        export function deleteLaborInsuranceOffice(
            laborInsuranceOfficeDeleteDto: model.LaborInsuranceOfficeDeleteDto): JQueryPromise<any> {
            //set up data respone
            var dfd = $.Deferred<any>();
            //set up data request
            var data = { laborInsuranceOfficeDeleteDto: laborInsuranceOfficeDeleteDto };
            //call service server
            nts.uk.request.ajax(paths.deleteLaborInsuranceOffice, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                }).fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        /*Model namespace. LaborInsuranceOfficeDto*/
        export module model {

            export class LaborInsuranceOfficeDto {
                /** The code. officeCode*/
                code: string;
                /** The name. officeName*/
                name: string;
                /** The short name. */
                shortName: string;
                /** The pic name. */
                picName: string;
                /** The pic position. */
                picPosition: string;
                /** The potal code. */
                potalCode: string;
                /** The prefecture. */
                prefecture: string;
                /** The address 1 st. */
                address1st: string;
                /** The address 2 nd. */
                address2nd: string;
                /** The kana address 1 st. */
                kanaAddress1st: string;
                /** The kana address 2 nd. */
                kanaAddress2nd: string;
                /** The phone number. */
                // TODO: TelephoneNo
                phoneNumber: string;
                /** The city sign. */
                citySign: string;
                /** The office mark. */
                officeMark: string;
                /** The office no A. */
                officeNoA: string;
                /** The office no B. */
                officeNoB: string;
                /** The office no C. */
                officeNoC: string;
                /** The memo. */
                memo: string;
            }

            export class LaborInsuranceOfficeFindOutDto {
                /** The code. officeCode*/
                code: string;
                /** The name. officeName*/
                name: string;
            }

            export enum TypeActionLaborInsuranceOffice {
                add = 1,
                update = 2
            }

            export class LaborInsuranceOfficeDeleteDto {
                code: string;
                version: number;
            }
        }
    }
}