module nts.uk.pr.view.kmf001.l {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findManageDistinct: 'ctx/at/share/vacation/setting/nursingleave/find/managedistinct',
            
            save: 'ctx/at/share/vacation/setting/nursingleave/save',
            findSetting: 'ctx/at/share/vacation/setting/nursingleave/find/setting',
            
            findSpecialHoliday: "ctx/at/share/vacation/setting/nursingleave/find/allspecialholiday",
            findAbsenceFrame: "ctx/at/share/vacation/setting/nursingleave/find/allabsenceframce"
        };
        
        export function findManageDistinct(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findManageDistinct);
        }

        export function save(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.save, command);
        }
        
        export function findSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findSetting);
        }
        
        export function findAllSpecialHoliday(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findSpecialHoliday);
        }
        
        export function findAllAbsenceFrame(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAbsenceFrame);
        }
        
        /**
        * Model namespace.
        */
        export module model {

            export class EnumerationModel {

                value: number;
                fieldName: string;
                localizedName: string;

                constructor(value: number, fieldName: string, localizedName: string) {
                    let self = this;
                    self.value = value;
                    self.fieldName = fieldName;
                    self.localizedName = localizedName;
                }
            }
        }
    }
}
