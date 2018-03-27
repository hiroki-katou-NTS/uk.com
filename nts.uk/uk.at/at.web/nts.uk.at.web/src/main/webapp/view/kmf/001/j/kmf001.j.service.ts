module nts.uk.pr.view.kmf001.j {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            getComSetting: "ctx/at/shared/vacation/setting/sixtyhourvacation/com/find",
            saveComSetting: "ctx/at/shared/vacation/setting/sixtyhourvacation/com/save",
            manageDistinctEnum: "ctx/at/shared/vacation/setting/sixtyhourvacation/enum/managedistinct",
            sixtyHourExtraEnum: "ctx/at/shared/vacation/setting/sixtyhourvacation/enum/sixtyhourextra",
            timeDigestiveUnitEnum: "ctx/at/shared/vacation/setting/sixtyhourvacation/enum/timedigestiveunit",
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
            public findComSetting(): JQueryPromise<model.Emp60HourVacationDto> {
                return nts.uk.request.ajax(paths.getComSetting);
            }

            /**
             * Update company's setting
             */
            public saveComSetting(setting: model.SixtyHourVacationSettingDto): JQueryPromise<any> {
                return nts.uk.request.ajax(paths.saveComSetting, setting);
            }

            /**
             * Get VacationExpiration Enum.
             */
            public getTimeDigestiveUnitEnum(): JQueryPromise<Array<model.Enum>> {
                return nts.uk.request.ajax(paths.timeDigestiveUnitEnum);
            }

            /**
             * Get ApplyPermission Enum.
             */
            public getSixtyHourExtraEnum(): JQueryPromise<Array<model.Enum>> {
                return nts.uk.request.ajax(paths.sixtyHourExtraEnum);
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
    
            export class SixtyHourVacationSettingDto {
                isManage: number;
                digestiveUnit: number;
                sixtyHourExtra: number;
    
                constructor(isManage: number, digestiveUnit: number, sixtyHourExtra: number) {
                    this.isManage = isManage;
                    this.digestiveUnit = digestiveUnit;
                    this.sixtyHourExtra = sixtyHourExtra;
                }
    
            }
    
            export class Emp60HourVacationDto extends SixtyHourVacationSettingDto {
                contractTypeCode: string;
    
                constructor(contractTypeCode: string, setting: SixtyHourVacationSettingDto) {
                    super(setting.isManage, setting.digestiveUnit, setting.sixtyHourExtra);
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
