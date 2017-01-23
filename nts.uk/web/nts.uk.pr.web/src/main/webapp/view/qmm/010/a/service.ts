module nts.uk.pr.view.qmm010.a {
    export module service {
        var paths: any = {
            findAllLaborInsuranceOffice: "ctx/pr/core/insurance/labor/findall",
            findLaborInsuranceOffice: "ctx/pr/core/insurance/labor/findLaborInsuranceOffice",
            addLaborInsuranceOffice: "ctx/pr/core/insurance/labor/add",
            updateLaborInsuranceOffice: "ctx/pr/core/insurance/labor/update",
            deleteLaborInsuranceOffice: "ctx/pr/core/insurance/labor/delete",
        };

        //Function connection service FindAll Labor Insurance Office
        export function findAllLaborInsuranceOffice(): JQueryPromise<Array<any>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.findAllLaborInsuranceOffice)
                .done(function(res: Array<any>) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Function connection service findLaborInsuranceOffice By Code
        export function findLaborInsuranceOffice(code: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.findLaborInsuranceOffice + "/" + code)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export function addLaborInsuranceOffice(laborInsuranceOffice: model.LaborInsuranceOfficeDto, companyCode: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { laborInsuranceOffice: laborInsuranceOffice, companyCode: companyCode };
            nts.uk.request.ajax(paths.addLaborInsuranceOffice, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        export function deleteLaborInsuranceOffice(code: string, companyCode: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { companyCode: companyCode, code: code };
            nts.uk.request.ajax(paths.deleteLaborInsuranceOffice, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        /**
       * Model namespace.
       */
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

                //New LaborInsuranceOffice
                constructor(code: string, name: string, shortName: string, picName: string, picPosition: string,
                    potalCode: string, prefecture: string, address1st: string, address2nd: string, kanaAddress1st: string,
                    kanaAddress2nd: string, phoneNumber: string, citySign: string, officeMark: string,
                    officeNoA: string, officeNoB: string, officeNoC: string, memo: string) {
                    this.code = code;
                    this.name = name;
                    this.shortName = shortName;
                    this.picName = picName;
                    this.picPosition = picPosition;
                    this.potalCode = potalCode;
                    this.prefecture = prefecture;
                    this.address1st = address1st;
                    this.address2nd = address2nd;
                    this.kanaAddress1st = kanaAddress1st;
                    this.kanaAddress2nd = kanaAddress2nd;
                    this.phoneNumber = phoneNumber;
                    this.citySign = citySign;
                    this.officeMark = officeMark;
                    this.officeNoA = officeNoA;
                    this.officeNoB = officeNoB;
                    this.officeNoC = officeNoC;
                    this.memo = memo;
                }
            }
            export class LaborInsuranceOfficeInDto {
                /** The code. officeCode*/
                code: string;
                /** The name. officeName*/
                name: string;

                //New LaborInsuranceOfficeInDto
                constructor(laborInsuranceOffice: LaborInsuranceOfficeDto) {
                    this.code = laborInsuranceOffice.code;
                    this.name = laborInsuranceOffice.name;
                }
            }

        }
    }
}