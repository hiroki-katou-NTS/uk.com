module nts.uk.pr.view.kmf001.h {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            getComSetting: "pr/proto/substvacation/com/find",
            getEmpSetting: "pr/proto/substvacation/emp/find",
            updateComSetting: "pr/proto/substvacation/com/update",
            updateEmpSetting: "pr/proto/substvacation/emp/update"
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
            public updateComSetting(setting: model.SubstVacationSettingDto): JQueryPromise<void> {
                return nts.uk.request.ajax(paths.updateUnitPriceHistory, setting);
            }

            /**
             * Update contract type's setting
             */
            public updateEmpSetting(setting: model.EmpSubstVacationDto): JQueryPromise<void> {
                return nts.uk.request.ajax(paths.updateUnitPriceHistory, setting);
            }
        }


        /**
        * Model namespace.
        */
        export module model {

            export interface EmpSubstVacationDto {
                empContractTypeCode: string;
                setting: SubstVacationSettingDto;
            }

            export interface SubstVacationSettingDto {
                isManage: number;
                expirationDate: number;
                allowPrepaidLeave: number;
            }
        }
    }
}
