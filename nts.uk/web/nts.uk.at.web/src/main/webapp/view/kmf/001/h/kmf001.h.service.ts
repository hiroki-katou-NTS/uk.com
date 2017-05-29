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
            manageDistinctEnum: "at/proto/substvacation/enum/managedistinct"
        };

        /**
         * Normal service.
         */
        export class Service {
            /**
             * Find company's setting.
             */
            public findComSetting(): JQueryPromise<model.SubstVacationSettingDto> {
                return nts.uk.request.ajax(paths.getUnitPriceHistoryDetail);
            }

            /**
             * Find contract type's setting.
             */
            public findEmpSetting(contractTypeCode: string): JQueryPromise<model.SubstVacationSettingDto> {
                return nts.uk.request.ajax(paths.getUnitPriceHistoryDetail + "/" + contractTypeCode);
            }

            /**
             * Update company's setting
             */
            public saveComSetting(setting: model.SubstVacationSettingDto): JQueryPromise<void> {
                return nts.uk.request.ajax(paths.updateUnitPriceHistory, setting);
            }

            /**
             * Update contract type's setting
             */
            public saveEmpSetting(setting: model.EmpSubstVacationDto): JQueryPromise<void> {
                return nts.uk.request.ajax(paths.updateUnitPriceHistory, setting);
            }

            /**
             * Get VacationExpiration Enum.
             */
            public getVacationExpirationEnum(): JQueryPromise<model.Enum> {
                return nts.uk.request.ajax(paths.getVacationExpirationEnum);
            }

            /**
             * Get ApplyPermission Enum.
             */
            public getApplyPermissionEnum(): JQueryPromise<model.Enum> {
                return nts.uk.request.ajax(paths.getApplyPermissionEnum);
            }

            /**
             * Get ManageDistinct Enum.
             */
            public getManageDistinctEnum(): JQueryPromise<model.Enum> {
                return nts.uk.request.ajax(paths.getManageDistinctEnum);
            }

        }


        /**
        * Model namespace.
        */
        export module model {

            export interface EmpSubstVacationDto {
                contractTypeCode: string;
                setting: SubstVacationSettingDto;
            }

            export interface SubstVacationSettingDto {
                isManage: number;
                expirationDate: number;
                allowPrepaidLeave: number;
            }

            export interface Enum {
                value: number;
                ecName: string;
                name: string;
            }
        }
    }
}
