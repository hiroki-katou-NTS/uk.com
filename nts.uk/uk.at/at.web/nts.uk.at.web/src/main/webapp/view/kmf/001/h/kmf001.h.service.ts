module nts.uk.pr.view.kmf001.h {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            getComSetting: "ctx/at/shared/vacation/setting/substvacation/com/find",
            getEmpSetting: "ctx/at/shared/vacation/setting/substvacation/emp/find",
            findAllByEmployment: 'ctx/at/shared/vacation/setting/substvacation/emp/findall',
            saveComSetting: "ctx/at/shared/vacation/setting/substvacation/com/save",
            saveEmpSetting: "ctx/at/shared/vacation/setting/substvacation/emp/save",
            deleteEmpSetting: "ctx/at/shared/vacation/setting/substvacation/emp/delete",
            vacationExpirationEnum: "ctx/at/shared/vacation/setting/substvacation/enum/vacationexpiration",
            applyPermissionEnum: "ctx/at/shared/vacation/setting/substvacation/enum/applypermission",
            manageDistinctEnum: "ctx/at/shared/vacation/setting/substvacation/enum/managedistinct",
            getH32: "ctx/at/shared/vacation/setting/substvacation/enum/managedeadline",
            getH34: "ctx/at/shared/vacation/setting/substvacation/enum/linkingManagementATR",

        };

        /**
         * Normal service.
         */
        export class Service {

            constructor() {

            }

            /**
             * Find company's setting.
             */
            public findComSetting(): JQueryPromise<model.SubstVacationSettingDto> {
                return nts.uk.request.ajax(paths.getComSetting);
            }

            /**
             * Find contract type's setting.
             */
            public findEmpSetting(contractTypeCode: string): JQueryPromise<model.EmpSubstVacationDto> {
                return nts.uk.request.ajax(paths.getEmpSetting + "/" + contractTypeCode);
            }
            /**
            *Find all employment.
             */
            public findAllByEmployment(): JQueryPromise<any> {
                return nts.uk.request.ajax(paths.findAllByEmployment);
            }
            /**
             * Update company's setting
             */
            public saveComSetting(setting: model.SubstVacationSettingDto): JQueryPromise<void> {
                return nts.uk.request.ajax(paths.saveComSetting, setting);
            }

            /**
             * Update contract type's setting
             */
            public saveEmpSetting(setting: model.EmpSubstVacationDto): JQueryPromise<void> {
                return nts.uk.request.ajax(paths.saveEmpSetting, setting);
            }

            /**
             * Delete contract type's setting
             */
            public deleteEmpSetting(contractTypeCode: string): JQueryPromise<void> {
                return nts.uk.request.ajax(paths.deleteEmpSetting + "/" + contractTypeCode);
            }

            /**
             * Get VacationExpiration Enum.
             */
            public getVacationExpirationEnum(): JQueryPromise<Array<model.Enum>> {
                return nts.uk.request.ajax(paths.vacationExpirationEnum);
            }

            /**
             * Get ApplyPermission Enum.
             */
            public getApplyPermissionEnum(): JQueryPromise<Array<model.Enum>> {
                return nts.uk.request.ajax(paths.applyPermissionEnum);
            }

            /**
             * Get ManageDistinct Enum.
             */
            public getManageDistinctEnum(): JQueryPromise<Array<model.Enum>> {
                return nts.uk.request.ajax(paths.manageDistinctEnum);
            }

            public getH32(): JQueryPromise<Array<model.Enum>> {
                return nts.uk.request.ajax(paths.getH32);
            }

            public getH34(): JQueryPromise<Array<model.Enum>> {
                return nts.uk.request.ajax(paths.getH34);
            }

        }

        /**
         * Service intance.
         */
        export var instance = new Service();

        /**
        * Model namespace.
        */
        export module model {

            export class SubstVacationSettingDto {
                isManage: number;
                expirationDate: number;
                allowPrepaidLeave: number;
                manageDeadline: number;
                linkingManagementATR: number;

                constructor(isManage: number, expirationDate: number, allowPrepaidLeave: number, manageDeadline: number, linkingManagementATR: number) {
                    this.isManage = isManage;
                    this.expirationDate = expirationDate;
                    this.allowPrepaidLeave = allowPrepaidLeave;
                    this.manageDeadline = manageDeadline;
                    this.linkingManagementATR = linkingManagementATR;
                }
            }

            export class EmpSubstVacationDto extends SubstVacationSettingDto {
                contractTypeCode: string;

                constructor(contractTypeCode: string, setting: SubstVacationSettingDto) {
                    super(setting.isManage, setting.expirationDate, setting.allowPrepaidLeave);
                    this.contractTypeCode = contractTypeCode;
                }
            }

            export class Enum {
                value: number;
                fieldName: string;
                localizedName: string;

                constructor(value: number, fieldName: string, localizedName: string) {
                    this.value = value;
                    this.fieldName = fieldName;
                    this.localizedName = localizedName;
                }
            }
        }
    }
}
