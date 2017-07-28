module nts.uk.at.view.ksm006.a {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            findCompanyBasicWork: 'ctx/at/schedule/shift/basicworkregister/companybasicwork/find',
            saveCompanyBasicWork: 'ctx/at/schedule/shift/basicworkregister/companybasicwork/save'
        };

        
        export function findCompanyBasicWork(): JQueryPromise<model.CompanyBasicWorkDto> {
            return nts.uk.request.ajax(paths.findCompanyBasicWork);
        }
        
        export function saveCompanyBasicWork(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.saveCompanyBasicWork, command);
        }

        /**
        * Model namespace.
        */
        export module model {
            export interface CompanyBasicWorkDto {
                companyId: string;
                basicWorkSetting: Array<BasicWorkSettingFindDto>;
            }
            
            export class BasicWorkSettingFindDto {
                workTypeCode: string;
                workingCode: string;
                workDayDivision: number;
                workTypeDisplayName: string;
                workingDisplayName: string;
            }
        }
    }
}
