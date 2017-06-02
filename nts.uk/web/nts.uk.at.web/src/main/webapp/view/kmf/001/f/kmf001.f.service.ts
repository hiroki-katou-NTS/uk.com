module nts.uk.pr.view.kmf001.f {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            update: 'ctx/at/shared/vacation/setting/compensatoryleave/save',
            find: 'ctx/at/shared/vacation/setting/compensatoryleave/find',
            enumManageDistinct: 'ctx/at/shared/vacation/setting/compensatoryleave/enum/managedistinct',
            enumApplyPermission: 'ctx/at/shared/vacation/setting/compensatoryleave/enum/applypermission',
            enumExpirationTime: 'ctx/at/shared/vacation/setting/compensatoryleave/enum/expirationtime',
            enumTimeVacationDigestiveUnit: 'ctx/at/shared/vacation/setting/compensatoryleave/enum/timevacationdigestiveunit',
            enumCompensatoryOccurrenceDivision: 'ctx/at/shared/vacation/setting/compensatoryleave/enum/compensatoryoccurrencedivision',
            enumTransferSettingDivision: 'ctx/at/shared/vacation/setting/compensatoryleave/enum/transfersettingdivision',
            
            //employment
            getListEmployment: 'ctx/at/shared/vacation/setting/compensatoryleave/employment/findall',
            updateEmploySetting: 'ctx/at/shared/vacation/setting/compensatoryleave/employment/save',
            findEmploymentSetting: 'ctx/at/shared/vacation/setting/compensatoryleave/employment/findsetting'
        };

        export function update(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.update, command);
        }

        export function find(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.find);
        }

        /**
             * Get VacationExpiration Enum.
             */
        export function getEnumManageDistinct(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(servicePath.enumManageDistinct);
        }

        export function getEnumApplyPermission(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(servicePath.enumApplyPermission);
        }
        /**
             * Get VacationExpiration Enum.
             */
        export function getEnumExpirationTime(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(servicePath.enumExpirationTime);
        }
        /**
             * Get VacationExpiration Enum.
             */
        export function getEnumTimeVacationDigestiveUnit(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(servicePath.enumTimeVacationDigestiveUnit);
        }
        /**
             * Get VacationExpiration Enum.
             */
        export function getEnumCompensatoryOccurrenceDivision(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(servicePath.enumCompensatoryOccurrenceDivision);
        }
        /**
             * Get VacationExpiration Enum.
             */
        export function getEnumTransferSettingDivision(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(servicePath.enumTransferSettingDivision);
        }
        
        //EMPLOYMENT
        export function getListEmployment(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getListEmployment);
        }
 
        export function updateEmploymentSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.updateEmploySetting,command);
        }

        export function findEmploymentSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findEmploymentSetting);
        }
 
        /**
        * Model namespace.
        */
        export module model {
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

            export class RadioEnum {
                value: number;
                fieldName: string;
                localizedName: string;
                enable: boolean;
                constructor(value: number, fieldName: string, localizedName: string) {
                    this.value = value;
                    this.fieldName = fieldName;
                    this.localizedName = localizedName;
                    this.enable = true;
                }
            }

        }
    }
}
