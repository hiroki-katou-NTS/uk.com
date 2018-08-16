module nts.uk.pr.view.kmf001.f {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            //enums             
            enumManageDistinct: 'ctx/at/shared/vacation/setting/compensatoryleave/enum/managedistinct',
            enumApplyPermission: 'ctx/at/shared/vacation/setting/compensatoryleave/enum/applypermission',
            enumExpirationTime: 'ctx/at/shared/vacation/setting/compensatoryleave/enum/expirationtime',
            enumTimeVacationDigestiveUnit: 'ctx/at/shared/vacation/setting/compensatoryleave/enum/timevacationdigestiveunit',
            enumCompensatoryOccurrenceDivision: 'ctx/at/shared/vacation/setting/compensatoryleave/enum/compensatoryoccurrencedivision',
            enumTransferSettingDivision: 'ctx/at/shared/vacation/setting/compensatoryleave/enum/transfersettingdivision',
            enumDeadlCheckMonth: 'ctx/at/shared/vacation/setting/compensatoryleave/enum/deadlcheckmonth',
            //com
            update: 'ctx/at/shared/vacation/setting/compensatoryleave/save',
            find: 'ctx/at/shared/vacation/setting/compensatoryleave/find',
            //employment
            findAllEmploymentSetting: 'ctx/at/shared/vacation/setting/compensatoryleave/employment/findall',
            updateEmploySetting: 'ctx/at/shared/vacation/setting/compensatoryleave/employment/save',
            deleteEmploySetting: 'ctx/at/shared/vacation/setting/compensatoryleave/employment/delete',
            findEmploymentSetting: 'ctx/at/shared/vacation/setting/compensatoryleave/employment/findsetting',
            findEmploymentDto: 'ctx/at/shared/vacation/setting/compensatoryleave/employment/finddto'
        };

        //update com
        export function update(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.update, command);
        }

        // find setting
        export function find(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.find);
        }

        /**
         * Get ManageDistinct Enum.
         */
        export function getEnumManageDistinct(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(servicePath.enumManageDistinct);
        }

        /**
         * Get ApplyPermission Enum.
         */
        export function getEnumApplyPermission(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(servicePath.enumApplyPermission);
        }

        /**
         * Get ExpirationTime Enum.
         */
        export function getEnumExpirationTime(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(servicePath.enumExpirationTime);
        }
        
        /**
         * Get DeadlCheckMonth Enum.
         */
        export function getEnumDeadlCheckMonth(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(servicePath.enumDeadlCheckMonth);
        }

        /**
         * Get TimeVacationDigestiveUnit Enum.
         */
        export function getEnumTimeVacationDigestiveUnit(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(servicePath.enumTimeVacationDigestiveUnit);
        }

        /**
         * Get CompensatoryOccurrenceDivision Enum.
         */
        export function getEnumCompensatoryOccurrenceDivision(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(servicePath.enumCompensatoryOccurrenceDivision);
        }

        /**
         * Get TransferSettingDivision Enum.
         */
        export function getEnumTransferSettingDivision(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(servicePath.enumTransferSettingDivision);
        }

        //EMPLOYMENT

        //update em
        export function updateEmploymentSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.updateEmploySetting, command);
        }
        
        //delete em
        export function deleteEmploymentSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.deleteEmploySetting, command);
        }

        //find em setting by em code
        export function findEmploymentSetting(employmentCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findEmploymentSetting + "/" + employmentCode);
        }
        
        //find em setting by em code
        export function findEmploymentDto(employmentCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findEmploymentDto + "/" + employmentCode);
        }
        
        //find all em setting 
        export function findAllEmploymentSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAllEmploymentSetting);
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
