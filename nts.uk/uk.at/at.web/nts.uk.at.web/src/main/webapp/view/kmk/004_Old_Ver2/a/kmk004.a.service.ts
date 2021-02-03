module nts.uk.at.view.kmk004.a {

    import WorktimeSettingDto = nts.uk.at.view.kmk004.shared.model.WorktimeSettingDto;
    import WorktimeSettingDtoSaveCommand = nts.uk.at.view.kmk004.shared.model.WorktimeSettingDtoSaveCommand;
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findCompanySetting: 'screen/at/kmk004/company/getDetails',
            saveCompanySetting: 'screen/at/kmk004/company/save',
            removeCompanySetting: 'screen/at/kmk004/company/delete',
            
            findBeginningMonth: 'basic/company/beginningmonth/find',
            findprogramName : 'sys/portal/standardmenu/findProgramName/{0}/{1}'
        }
        
        export function findprogramName(programId: string, screenId: string): JQueryPromise<any> {
            return nts.uk.request.ajax('com', nts.uk.text.format(servicePath.findprogramName, programId, screenId));
        }
        
        export function saveAsExcel(domainType: string, startDate : any, endDate: any): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "SetWorkingHoursAndDays", domainType: domainType, languageId: 'ja', reportType: 0, mode: 4, startDate: startDate, endDate: endDate});
        }
        
        export function getStartMonth(): JQueryPromise<any> {
            return nts.uk.request.ajax('com', servicePath.findBeginningMonth);
        }
        
        export function removeCompanySetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeCompanySetting, command);
        }
        

        export function findCompanySetting(year: number): JQueryPromise<WorktimeSettingDto> {
            return nts.uk.request.ajax(servicePath.findCompanySetting + '/' + year);
        }
        
        export function saveCompanySetting(command: WorktimeSettingDtoSaveCommand): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveCompanySetting, command);
        }
    }
}