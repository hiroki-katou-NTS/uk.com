module nts.uk.pr.view.qmm016.l {
    export module service {
        var paths: any = {
            findAllCertification: "pr/wagetable/certification/findall",
            findAllCertifyGroup: "pr/wagetable/certifygroup/findall",
            findCertifyGroup: "pr/wagetable/certifygroup/find",
            findLaborInsuranceOffice: "ctx/pr/core/insurance/labor/findLaborInsuranceOffice",
            addCertifyGroup: "pr/wagetable/certifygroup/add",
            updateCertifyGroup: "pr/wagetable/certifygroup/update",
            deleteLaborInsuranceOffice: "ctx/pr/core/insurance/labor/delete",
        };

        //Function connection service FindAll Certification
        export function findAllCertification(): JQueryPromise<Array<model.CertificationFindInDto>> {
            var dfd = $.Deferred<Array<model.CertificationFindInDto>>();
            nts.uk.request.ajax(paths.findAllCertification)
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
        export function findAllCertifyGroup(): JQueryPromise<Array<model.CertifyGroupFindOutDto>> {
            var dfd = $.Deferred<Array<model.CertifyGroupFindOutDto>>();
            nts.uk.request.ajax(paths.findAllCertifyGroup)
                .done(function(res: Array<model.CertifyGroupFindOutDto>) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Function connection service FindA CertifyGroup
        export function findCertifyGroup(code: string): JQueryPromise<model.CertifyGroupDto> {
            var dfd = $.Deferred<model.CertifyGroupDto>();
            nts.uk.request.ajax(paths.findCertifyGroup + "/" + code)
                .done(function(res: model.CertifyGroupDto) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        //Function connection service Add CertifyGroup
        export function addCertifyGroup(certifyGroupDto: model.CertifyGroupDto): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { certifyGroupDto: certifyGroupDto };
            nts.uk.request.ajax(paths.addCertifyGroup, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Function connection service Update CertifyGroup
        export function updateCertifyGroup(certifyGroupDto: model.CertifyGroupDto): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { certifyGroupDto: certifyGroupDto };
            nts.uk.request.ajax(paths.updateCertifyGroup, data)
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
            export class CertificationDto {
                code: string;
                name: string;
            }

            export class CertificationFindInDto {
                code: string;
                name: string;
            }
            export class CertifyGroupFindOutDto {
                code: string;
                name: string;
            }

            export class CertifyGroupDto {
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
            export enum TypeActionCertifyGroup {
                add = 1,
                update = 2
            }
        }
    }
}