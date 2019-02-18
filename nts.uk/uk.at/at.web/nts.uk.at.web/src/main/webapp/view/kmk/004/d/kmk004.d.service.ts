module nts.uk.at.view.kmk004.d {

    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            // ervicePath of find AlreadySetting for component KCP004 
            findAllWorkplaceSetting: 'screen/at/kmk004/workplace/findAll',

            findWorkplaceSetting: 'screen/at/kmk004/workplace/getDetails',
            saveWorkplaceSetting: 'screen/at/kmk004/workplace/save',
            removeWorkplaceSetting: 'screen/at/kmk004/workplace/delete',
            findprogramName : 'sys/portal/standardmenu/findProgramName/{0}/{1}'
        }
        
        export function findprogramName(programId: string, screenId: string): JQueryPromise<any> {
            return nts.uk.request.ajax('com', nts.uk.text.format(servicePath.findprogramName, programId, screenId));
        }
        
        export function saveAsExcel(domainType: string, startDate : any, endDate: any): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "SetWorkingHoursAndDays", domainType: domainType, languageId: 'ja', reportType: 0, mode: 4, startDate: startDate, endDate: endDate});
        }


        // Find AlreadySetting
        export function findAllWorkplaceSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAllWorkplaceSetting);
        }

        // WORKPLACE
        export function saveWorkplaceSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveWorkplaceSetting, command);
        }

        export function findWorkplaceSetting(year: number, wkpId: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findWorkplaceSetting + '/' + year + '/' + wkpId);
        }

        export function removeWorkplaceSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeWorkplaceSetting, command);
        }
    }
}