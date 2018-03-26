module nts.uk.at.view.kmk004.a {
//    import WorktimeSettingDto = kmk004.a.viewmodel.ScreenModel.WorktimeSettingDto;
    import WorktimeSettingDto = kmk004.a.viewmodel.WorktimeSettingDto;
    import WorktimeSettingDtoSaveCommand = kmk004.a.viewmodel.WorktimeSettingDtoSaveCommand;
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            // TAB 1: COMPANY
//            findCompanySetting: 'ctx/at/shared/employment/statutory/worktime/company/find',
//            saveCompanySetting: 'ctx/at/shared/employment/statutory/worktime/company/save',
//            removeCompanySetting: 'ctx/at/shared/employment/statutory/worktime/company/remove',
            
            findCompanySetting: 'screen/at/kmk004/company/getDetails',
            saveCompanySetting: 'screen/at/kmk004/company/save',
            removeCompanySetting: 'screen/at/kmk004/company/delete',
            
            findEmploymentSetting: 'ctx/at/shared/employment/statutory/worktime/employment/find',
            findAllEmploymentSetting: 'ctx/at/shared/employment/statutory/worktime/employment/findall',
            saveEmploymentSetting: 'ctx/at/shared/employment/statutory/worktime/employment/save',
            removeEmploymentSetting: 'ctx/at/shared/employment/statutory/worktime/employment/remove',
            
            findWorkplaceSetting: 'ctx/at/shared/employment/statutory/worktime/workplace/find',
            findAllWorkplaceSetting: 'ctx/at/shared/employment/statutory/worktime/workplace/findall',
            saveWorkplaceSetting: 'ctx/at/shared/employment/statutory/worktime/workplace/save',
            removeWorkplaceSetting: 'ctx/at/shared/employment/statutory/worktime/workplace/remove',
            
            findBeginningMonth: 'basic/company/beginningmonth/find'
        };

        export function getStartMonth(): JQueryPromise<any> {
            return nts.uk.request.ajax('com', servicePath.findBeginningMonth);
        }
        // COMPANY

//        export function saveCompanySetting(command: any): JQueryPromise<any> {
//            return nts.uk.request.ajax(servicePath.saveCompanySetting, command);
//        }
//
//        export function findCompanySetting(year: number): JQueryPromise<any> {
//            return nts.uk.request.ajax(servicePath.findCompanySetting + '/' + year);
//        }
//
        export function removeCompanySetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeCompanySetting, command);
        }
        

        export function findCompanySetting(year: number): JQueryPromise<WorktimeSettingDto> {
            return nts.uk.request.ajax(servicePath.findCompanySetting + '/' + year);
        }
        
        export function saveCompanySetting(command: WorktimeSettingDtoSaveCommand): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveCompanySetting, command);
        }


        // EMPLOYMENT
        export function saveEmploymentSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveEmploymentSetting, command);
        }

        export function findEmploymentSetting(request: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findEmploymentSetting, request);
        }

        export function findAllEmploymentSetting(year: number): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAllEmploymentSetting + '/' + year);
        }

        export function removeEmploymentSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeEmploymentSetting, command);
        }

        // WORKPLACE
        export function saveWorkplaceSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveWorkplaceSetting, command);
        }

        export function findWorkplaceSetting(request: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findWorkplaceSetting, request);
        }

        export function findAllWorkplaceSetting(year: number): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAllWorkplaceSetting + '/' + year);
        }

        export function removeWorkplaceSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeWorkplaceSetting, command);
        }

    }
}
