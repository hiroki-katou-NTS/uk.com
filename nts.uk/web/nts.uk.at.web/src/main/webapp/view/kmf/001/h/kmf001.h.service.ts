module nts.uk.pr.view.kmf001.h {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            getComSetting: "at/proto/substvacation/com/find",
            getEmpSetting: "at/proto/substvacation/emp/find",
            saveComSetting: "at/proto/substvacation/com/save",
            saveEmpSetting: "at/proto/substvacation/emp/save",
            vacationExpirationEnum: "at/proto/substvacation/enum/vacationexpiration",
            applyPermissionEnum: "at/proto/substvacation/enum/applypermission",
            manageDistinctEnum: "at/proto/substvacation/enum/managedistinct",
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

        }

        /**
         * Service intance.
         */
        export var instance = new Service();

        /**
        * Model namespace.
        */
        export module model {

            export class EmpSubstVacationDto {
                contractTypeCode: string;
                setting: SubstVacationSettingDto;

                constructor(contractTypeCode: string, setting: SubstVacationSettingDto) {
                    this.contractTypeCode = contractTypeCode;
                    this.setting = setting;
                }
            }

            export class SubstVacationSettingDto {
                isManage: number;
                expirationDate: number;
                allowPrepaidLeave: number;

                constructor(isManage: number, expirationDate: number, allowPrepaidLeave: number) {
                    this.isManage = isManage;
                    this.expirationDate = expirationDate;
                    this.allowPrepaidLeave = allowPrepaidLeave;
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
