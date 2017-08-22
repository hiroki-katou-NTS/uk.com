module nts.uk.at.view.ksm001.e {
    export module service {
        var paths = {
            find: "ctx/at/schedule/shift/pattern/estimate/usagesetting/find",
            save: "ctx/at/schedule/shift/pattern/estimate/usagesetting/save",
            useclassification: "ctx/at/schedule/shift/pattern/estimate/usagesetting/find/useclassification"
        }

        /**
         * call service find setting
         */
        export function getUsageSetting(): JQueryPromise<model.UsageSettingDto> {
            return nts.uk.request.ajax('at', paths.find);
        }

        export function saveUsageSetting(command: model.UsageSettingDto): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.save, command);
        }
        
        export function getUseClsEnum(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(paths.useclassification);
        }

        export module model {
            export class UsageSettingDto {
                employmentSetting: number;
                personalSetting: number;
            
                constructor(employmentSetting: number, personalSetting: number) {
                    this.employmentSetting = employmentSetting;
                    this.personalSetting = personalSetting;
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