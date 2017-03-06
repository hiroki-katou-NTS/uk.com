module nts.uk.pr.view.qmm010.b {
    export module service {
        var paths: any = {
            checkDuplicateCodeByImportData: "ctx/pr/core/insurance/labor/importser/checkDuplicateCode",
            importData: "ctx/pr/core/insurance/labor/importser/importData"
        };

        //Function connnection service check Duplicate Code By ImportData
        export function checkDuplicateCodeByImportData(socialInsuranceOfficeImportDto: model.SocialInsuranceOfficeImportDto): JQueryPromise<model.LaborInsuranceOfficeCheckImportDto> {
            var dfd = $.Deferred<model.LaborInsuranceOfficeCheckImportDto>();
            nts.uk.request.ajax(paths.checkDuplicateCodeByImportData, socialInsuranceOfficeImportDto)
                .done(function(res: model.LaborInsuranceOfficeCheckImportDto) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Function 

        export function importData(laborInsuranceOfficeImportDto: model.LaborInsuranceOfficeImportDto): JQueryPromise<model.LaborInsuranceOfficeImportOutDto> {
            var dfd = $.Deferred<model.LaborInsuranceOfficeImportOutDto>();
            nts.uk.request.ajax(paths.importData, laborInsuranceOfficeImportDto)
                .done(function(res: model.LaborInsuranceOfficeImportOutDto) {
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
            export class SocialInsuranceOfficeImportDto {
                /** The code. */
                code: string;

                /** The name. */
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
                phoneNumber: string;

                /** The health insu office ref code 1 st. */
                healthInsuOfficeRefCode1st: string;

                /** The health insu office ref code 2 nd. */
                healthInsuOfficeRefCode2nd: string;

                /** The pension office ref code 1 st. */
                pensionOfficeRefCode1st: string;

                /** The pension office ref code 2 nd. */
                pensionOfficeRefCode2nd: string;

                /** The welfare pension fund code. */
                welfarePensionFundCode: string;

                /** The office pension fund code. */
                officePensionFundCode: string;

                /** The health insu city code. */
                healthInsuCityCode: string;

                /** The health insu office sign. */
                healthInsuOfficeSign: string;

                /** The pension city code. */
                pensionCityCode: string;

                /** The pension office sign. */
                pensionOfficeSign: string;

                /** The health insu office code. */
                healthInsuOfficeCode: string;

                /** The health insu asso code. */
                healthInsuAssoCode: string;

                /** The memo. */
                memo: string;
            }

            export class LaborInsuranceOfficeImportOutDto {
                code: string;
                message: string;
                totalImport: number;
            }

            export class LaborInsuranceOfficeImportDto {
                socialInsuranceOfficeImport: SocialInsuranceOfficeImportDto;
                checkUpdateDuplicateCode: number; //0 update //1 none update
                constructor() {
                    this.socialInsuranceOfficeImport = new SocialInsuranceOfficeImportDto();
                    this.checkUpdateDuplicateCode = 0;
                }
            }

            export class LaborInsuranceOfficeCheckImportDto {
                code: string; // "0" succ 1 "rows duplication"
                /** The message. */
                message: string;
            }
        }
    }
}