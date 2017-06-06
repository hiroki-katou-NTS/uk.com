module nts.uk.pr.view.kmf001.j {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            getComSetting: "at/proto/sixtyhourvacation/com/find",
            getEmpSetting: "at/proto/sixtyhourvacation/emp/find",
            saveComSetting: "at/proto/sixtyhourvacation/com/save",
            saveEmpSetting: "at/proto/sixtyhourvacation/emp/save",
            manageDistinctEnum: "at/proto/sixtyhourvacation/enum/managedistinct",
            sixtyHourExtraEnum: "at/proto/sixtyhourvacation/enum/sixtyhourextra",
            timeDigestiveUnitEnum: "at/proto/sixtyhourvacation/enum/timedigestiveunit",
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
             * Find contract type's setting.
             */
            public findEmpSetting(contractTypeCode: string): JQueryPromise<model.Emp60HourVacationDto> {
                return nts.uk.request.ajax(paths.getEmpSetting + "/" + contractTypeCode);
            }

            /**
             * Update company's setting
             */
            public saveComSetting(setting: model.SixtyHourVacationSettingDto): JQueryPromise<void> {
                return nts.uk.request.ajax(paths.saveComSetting, setting);
            }

            /**
             * Update contract type's setting
             */
            public saveEmpSetting(setting: model.SixtyHourVacationSettingDto): JQueryPromise<void> {
                return nts.uk.request.ajax(paths.saveEmpSetting, setting);
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
