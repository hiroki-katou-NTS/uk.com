module nts.uk.pr.view.qmm016.l {
    export module service {
        var paths: any = {
            findAllCertification: "pr/wagetable/certification/findall",
            findAllCertifyGroup: "pr/wagetable/certifygroup/findall",
            findLaborInsuranceOffice: "ctx/pr/core/insurance/labor/findLaborInsuranceOffice",
            addLaborInsuranceOffice: "ctx/pr/core/insurance/labor/add",
            updateLaborInsuranceOffice: "ctx/pr/core/insurance/labor/update",
            deleteLaborInsuranceOffice: "ctx/pr/core/insurance/labor/delete",
        };

        //Function connection service FindAll Certification
        export function findAllCertification(companyCode: string): JQueryPromise<Array<model.CertificationFindInDto>> {
            var dfd = $.Deferred<Array<model.CertificationFindInDto>>();
            nts.uk.request.ajax(paths.findAllCertification + "/" + companyCode)
                .done(function(res: Array<model.CertificationFindInDto>) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Function connection service FindAll CertifyGroup
        export function findAllCertifyGroup(companyCode: string): JQueryPromise<Array<model.CertifyGroupFindInDto>> {
            var dfd = $.Deferred<Array<model.CertifyGroupFindInDto>>();
            nts.uk.request.ajax(paths.findAllCertifyGroup + "/" + companyCode)
                .done(function(res: Array<model.CertifyGroupFindInDto>) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        export module model {
            export class CertificationDto {
                code: string;
                name: string;
            }

            export class CertificationFindInDto {
                code: string;
                name: string;
            }
            export class CertifyGroupFindInDto {
                code: string;
                name: string;
            }

            export class CertifyGroupDto {
                /** The wage table code. */
                wageTableCode: string;

                /** The history id. */
                historyId: string;

                /** The code. */
                code: string;

                /** The name. */
                name: string;

                /** The multi apply set. */
                multiApplySet: number;

                /** The certifies. */
                certifies: CertificationDto[];
            }
            export enum MultipleTargetSetting {
                BigestMethod = 0,//BigestMethod
                TotalMethod = 1//TotalMethod
            }
            export class MultipleTargetSettingDto {
                code: number;
                name: string;
                constructor(code: number, name: string) {
                    this.code = code;
                    this.name = name;
                }
            }
        }
    }
}