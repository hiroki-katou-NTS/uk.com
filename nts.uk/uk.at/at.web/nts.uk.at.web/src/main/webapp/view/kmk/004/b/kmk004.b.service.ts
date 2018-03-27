module nts.uk.at.view.kmk004.b {

    /**
         *  Service paths
         */
    var servicePath: any = {
        // TODO: path of Employee AlreadySetting (get all Domain "社員別通常勤務労働時間")

        findEmployeeSetting: 'screen/at/kmk004/employee/getDetails',
        saveEmployeeSetting: 'screen/at/kmk004/employee/save',
        removeEmployeeSetting: 'screen/at/kmk004/employee/delete',

    }
        export function removeCompanySetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeEmployeeSetting, command);
        }
        

        export function findCompanySetting(year: number, sid: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findEmployeeSetting + '/' + year + '/' + sid);
        }
        
        export function saveCompanySetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveEmployeeSetting, command);
        }
}