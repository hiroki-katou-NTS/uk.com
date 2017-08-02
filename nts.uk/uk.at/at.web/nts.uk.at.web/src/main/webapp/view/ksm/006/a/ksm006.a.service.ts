module nts.uk.at.view.ksm006.a {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            // Cpmpany
            findCompanyBasicWork: 'ctx/at/schedule/shift/basicworkregister/companybasicwork/find',
            saveCompanyBasicWork: 'ctx/at/schedule/shift/basicworkregister/companybasicwork/save',
            
            //Workplace
            findWorkplaceBasicWork: 'ctx/at/schedule/shift/basicworkregister/workplacebasicwork/find',
            saveWorkplaceBasicWork: 'ctx/at/schedule/shift/basicworkregister/workplacebasicwork/save',
            removeWorkplaceBasicWork: 'ctx/at/schedule/shift/basicworkregister/workplacebasicwork/remove',
            findWorkplaceSetting: 'ctx/at/schedule/shift/basicworkregister/workplacebasicwork/findSetting',
            
            // Classification
            findClassifyBasicWork: 'ctx/at/schedule/shift/basicworkregister/classificationbasicwork/find',
            saveClassifyBasicWork: 'ctx/at/schedule/shift/basicworkregister/classificationbasicwork/save',
            removeClassifyBasicWork: 'ctx/at/schedule/shift/basicworkregister/classificationbasicwork/remove',
            findClassifySetting: 'ctx/at/schedule/shift/basicworkregister/classificationbasicwork/findSetting'
            
        };

// Cpmpany        
        export function findCompanyBasicWork(): JQueryPromise<model.CompanyBasicWorkFindDto> {
            return nts.uk.request.ajax(paths.findCompanyBasicWork);
        }
        
        export function saveCompanyBasicWork(command: model.CompanyBasicWorkDto): JQueryPromise<any> {
            var data = {companyBasicWork: command};
            return nts.uk.request.ajax(paths.saveCompanyBasicWork, data);
        }
        
//Workplace        
        export function findWorkplaceBasicWork(workplaceId: string): JQueryPromise<model.WorkplaceBasicWorkFindDto> {
            return nts.uk.request.ajax(paths.findWorkplaceBasicWork + "/" + workplaceId);
        }
        
        export function saveWorkplaceBasicWork(command: model.WorkplaceBasicWorkDto): JQueryPromise<any> {
            var data = {workplaceBasicWork: command};
            return nts.uk.request.ajax(paths.saveWorkplaceBasicWork, data);
        }
        
        export function removeWorkplaceBasicWork(command: model.WorkplaceBasicWorkDto): JQueryPromise<any> {
            var data = {workplaceBasicWork: command};
            return nts.uk.request.ajax(paths.removeWorkplaceBasicWork, data);
        }
        
        export function findWorkplaceSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findWorkplaceSetting);
        }
        
// Classification
        export function findClassifyBasicWork(classifyCode: string): JQueryPromise<model.ClassifiBasicWorkFindDto> {
            return nts.uk.request.ajax(paths.findClassifyBasicWork + "/" + classifyCode);
        }
        
         export function saveClassifyBasicWork(command: model.ClassificationBasicWorkDto): JQueryPromise<any> {
            var data = {classifiBasicWork: command};
            return nts.uk.request.ajax(paths.saveClassifyBasicWork, data);
        }
        
        export function removeClassifyBasicWork(command: model.ClassificationBasicWorkDto): JQueryPromise<any> {
            var data = {classifiBasicWork: command};
            return nts.uk.request.ajax(paths.removeClassifyBasicWork, data);
        }
        
        export function findClassifySetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findClassifySetting);
        }
        
        

        /**
        * Model namespace.
        */
        export module model {
            export interface CompanyBasicWorkFindDto {
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
            
            export class CompanyBasicWorkDto {
                companyId: string;
                basicWorkSetting: Array<BasicWorkSettingDto>;
            }
            
            export class BasicWorkSettingDto {
                workTypeCode: string;
                siftCode: string;
                workDayDivision: number;
                
                constructor(workDayDivision: number, workTypeCode: string, siftCode: string) {
                    this.workDayDivision = workDayDivision;
                    this.workTypeCode = workTypeCode;
                    this.siftCode = siftCode;
                }
            }
            
            export class WorkplaceBasicWorkFindDto {
                workplaceId: string;
                basicWorkSetting: Array<BasicWorkSettingFindDto>;
            }
            
            export class WorkplaceBasicWorkDto {
                workplaceId: string;
                basicWorkSetting: Array<BasicWorkSettingDto>;
            }
            
            export class ClassifiBasicWorkFindDto {
                companyId: string;
                classificationCode: string;
                basicWorkSetting: Array<BasicWorkSettingFindDto>;
            }
            
            export class ClassificationBasicWorkDto {
                companyId: string;
                classificationCode: string;
                basicWorkSetting: Array<BasicWorkSettingDto>;
            }
           
        }
    }
}
