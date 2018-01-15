module nts.uk.pr.view.kmf001.c {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findManageDistinct: 'ctx/at/share/vacation/setting/annualpaidleave/find/managedistinct',
            findApplyPermission: 'ctx/at/share/vacation/setting/annualpaidleave/find/applypermission',
            findPreemptionPermit: 'ctx/at/share/vacation/setting/annualpaidleave/find/preemptionpermit',
            findDisplayDivision: 'ctx/at/share/vacation/setting/annualpaidleave/find/displaydivision',
            findTimeUnit: 'ctx/at/share/vacation/setting/annualpaidleave/find/timeunit',
            findMaxDayReference: 'ctx/at/share/vacation/setting/annualpaidleave/find/maxdayreference',
            
            save: 'ctx/at/share/vacation/setting/annualpaidleave/save',
            findSetting: 'ctx/at/share/vacation/setting/annualpaidleave/find/setting'
        };
        
        export function findManageDistinct(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findManageDistinct);
        }
        
        export function findApplyPermission(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findApplyPermission);
        }
        
        export function findPreemptionPermit(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findPreemptionPermit);
        }
        
        export function findDisplayDivision(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findDisplayDivision);
        }
        
        export function findTimeUnit(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findTimeUnit);
        }
        
        export function findMaxDayReference(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findMaxDayReference);
        }

        export function save(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.save, command);
        }
        
        export function findSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findSetting);
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
